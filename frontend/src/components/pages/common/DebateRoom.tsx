import React, { useState, useEffect, useRef } from 'react';
import { OpenVidu, Session, Publisher } from 'openvidu-browser';
import { useParams } from 'react-router-dom';
import { api } from '../../../services/api';

const DebateRoom: React.FC = () => {
  // URL 파라미터에서 sessionName 추출 (없으면 'DebateRoom' 사용)
  const { sessionName } = useParams<{ sessionName: string }>();
  const roomName = sessionName || 'DebateRoom';

  // 상태 변수 선언
  const [session, setSession] = useState<Session | null>(null);
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [userCount, setUserCount] = useState<number>(0);
  const [nickname, setNickname] = useState<string>('');
  const [joined, setJoined] = useState<boolean>(false);
  const [isAudioActive, setIsAudioActive] = useState<boolean>(true);
  const [isVideoActive, setIsVideoActive] = useState<boolean>(true);

  // DOM Ref
  const publisherRef = useRef<HTMLDivElement>(null);
  const subscribersRef = useRef<HTMLDivElement>(null);
  const OVRef = useRef<OpenVidu | null>(null);

  // 스타일 객체 (인라인 스타일)
  const containerStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    minHeight: '100vh',
    background: 'linear-gradient(135deg, #f5f7fa, #c3cfe2)',
    fontFamily: 'Arial, sans-serif',
    padding: '20px',
  };

  const headerStyle: React.CSSProperties = {
    fontSize: '2.5rem',
    fontWeight: 'bold',
    marginBottom: '10px',
    color: '#333',
  };

  const subHeaderStyle: React.CSSProperties = {
    fontSize: '1.2rem',
    marginBottom: '30px',
    color: '#555',
  };

  const cardStyle: React.CSSProperties = {
    backgroundColor: '#fff',
    padding: '20px 30px',
    borderRadius: '10px',
    boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
    width: '100%',
    maxWidth: '800px',
    marginBottom: '20px',
  };

  const buttonStyle: React.CSSProperties = {
    padding: '12px 24px',
    fontSize: '16px',
    borderRadius: '6px',
    border: 'none',
    cursor: 'pointer',
    transition: 'transform 0.3s ease',
    marginRight: '10px',
  };

  const inputStyle: React.CSSProperties = {
    padding: '10px',
    fontSize: '16px',
    borderRadius: '6px',
    border: '1px solid #ccc',
    width: '60%',
    marginRight: '10px',
  };

  const videoContainerStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  };

  const videoAreaStyle: React.CSSProperties = {
    width: '640px',
    height: '480px',
    backgroundColor: '#000',
    borderRadius: '10px',
    overflow: 'hidden',
    position: 'relative',
    marginBottom: '20px',
  };

  // 기존 flex 스타일 대신 Grid 레이아웃 사용 (반응형)
  const subscribersContainerStyle: React.CSSProperties = {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
    gap: '10px',
    width: '100%',
  };

  // 세션 입장 함수 (publisher 초기화 코드는 useEffect로 분리)
  const joinSession = async () => {
    if (joined) {
      alert('이미 입장하셨습니다.');
      return;
    }
    if (!nickname) {
      alert('닉네임을 입력해주세요.');
      return;
    }
    const OV = new OpenVidu();
    OVRef.current = OV;
    const mySession = OV.initSession();

    // 원격 스트림 생성 시 구독 컨테이너 추가
    mySession.on('streamCreated', (event: any) => {
      // 각 구독자 요소를 생성할 때 Grid에 맞게 100% 폭으로 지정
      const subscriberContainer = document.createElement('div');
      subscriberContainer.id = `subscriber-${event.stream.streamId}`;
      subscriberContainer.style.width = '100%'; // Grid 내에서 자동 확장
      subscriberContainer.style.height = '200px';
      subscriberContainer.style.border = '1px solid #ccc';
      subscriberContainer.style.borderRadius = '8px';
      subscriberContainer.style.overflow = 'hidden';
      subscriberContainer.style.boxShadow = '0 2px 4px rgba(0,0,0,0.1)';
      if (subscribersRef.current) {
        subscribersRef.current.appendChild(subscriberContainer);
      }
      mySession.subscribe(event.stream, subscriberContainer);
      setUserCount((prev) => prev + 1);
    });

    // 원격 스트림 종료 시 컨테이너 제거
    mySession.on('streamDestroyed', (event: any) => {
      const subscriberContainer = document.getElementById(`subscriber-${event.stream.streamId}`);
      if (subscriberContainer && subscribersRef.current) {
        subscribersRef.current.removeChild(subscriberContainer);
      }
      setUserCount((prev) => prev - 1);
    });

    try {
      // 백엔드에서 토큰 발급 요청 (JWT 토큰은 실제 값으로 대체)
      const response = await api.post(
        '/openvidu/join',
        { sessionName: roomName, nickname },
        {
          headers: { Authorization: `Bearer YOUR_JWT_TOKEN_HERE` },
          withCredentials: true,
        }
      );
      const { token } = response.data;

      // 세션 연결 (clientData에 닉네임 전달)
      await mySession.connect(token, { clientData: nickname });
      setSession(mySession);
      setUserCount(1);
      setJoined(true);
      // publisher 초기화는 아래 useEffect에서 처리됩니다.
    } catch (error) {
      console.error('세션 참가 중 오류 발생:', error);
      alert('세션 참가 중 오류가 발생했습니다.');
    }
  };

  // publisherRef가 렌더링된 후 publisher 초기화 (카메라 화면 표시)
  useEffect(() => {
    if (joined && session && !publisher && publisherRef.current && OVRef.current) {
      const publisherOptions = {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        mirror: true,
        resolution: '640x480',
      };
      const myPublisher = OVRef.current.initPublisher(publisherRef.current, publisherOptions, (error) => {
        if (error) {
          console.error('Publisher 초기화 에러:', error);
          alert('카메라/마이크 접근에 문제가 있습니다. 브라우저 권한을 확인하세요.');
        }
      });
      myPublisher.on('accessAllowed', () => {
        console.log('카메라/마이크 접근 허용됨');
      });
      myPublisher.on('accessDenied', () => {
        console.warn('카메라/마이크 접근 거부됨');
      });
      setPublisher(myPublisher);
      session.publish(myPublisher);
    }
  }, [joined, session, publisher]);

  // 세션 종료 함수
  const leaveSession = () => {
    if (session) session.disconnect();
    setSession(null);
    setPublisher(null);
    setUserCount(0);
    setJoined(false);
    if (subscribersRef.current) {
      subscribersRef.current.innerHTML = '';
    }
  };

  // 컴포넌트 unmount 시 세션 종료
  useEffect(() => {
    return () => {
      if (session) session.disconnect();
    };
  }, [session]);

  return (
    <div style={containerStyle}>
      <header style={{ textAlign: 'center', marginBottom: '30px' }}>
        <h1 style={headerStyle}>{roomName}</h1>
      </header>

      {/* 입장 전 화면 */}
      {!joined && (
        <div style={cardStyle}>
          <div style={{ textAlign: 'center', marginBottom: '20px' }}>
            <h2 style={{ margin: 0, color: '#333' }}>Debate Room 입장</h2>
            <p style={{ margin: '10px 0', color: '#777' }}>닉네임을 입력하고 입장하세요.</p>
          </div>
          <div style={{ display: 'flex', justifyContent: 'center' }}>
            <input
              type="text"
              placeholder="닉네임 입력"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
              style={inputStyle}
            />
            <button
              onClick={joinSession}
              style={{
                ...buttonStyle,
                backgroundColor: '#28a745',
                color: '#fff',
              }}
              onMouseOver={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1.05)')
              }
              onMouseOut={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1)')
              }
            >
              입장하기
            </button>
          </div>
        </div>
      )}

      {/* 입장 후 영상 영역 */}
      {joined && (
        <div style={cardStyle}>
          <div style={{ textAlign: 'center', marginBottom: '20px' }}>
            <h2 style={{ margin: 0, color: '#333' }}>참가자 수: {userCount}</h2>
          </div>

          <div style={videoContainerStyle}>
            <div style={videoAreaStyle}>
              <div ref={publisherRef} style={{ width: '100%', height: '100%' }}></div>
            </div>
            {/* 구독자 영상 영역을 Grid 레이아웃으로 표시 */}
            <div ref={subscribersRef} style={subscribersContainerStyle}></div>
          </div>

          <div style={{ textAlign: 'center', marginTop: '20px' }}>
            <button
              onClick={() => {
                if (publisher) {
                  const newAudioStatus = !isAudioActive;
                  publisher.publishAudio(newAudioStatus);
                  setIsAudioActive(newAudioStatus);
                }
              }}
              style={{
                ...buttonStyle,
                backgroundColor: '#6c757d',
                color: '#fff',
              }}
              onMouseOver={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1.05)')
              }
              onMouseOut={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1)')
              }
            >
              {isAudioActive ? '음소거' : '음소거 해제'}
            </button>
            <button
              onClick={() => {
                if (publisher) {
                  const newVideoStatus = !isVideoActive;
                  publisher.publishVideo(newVideoStatus);
                  setIsVideoActive(newVideoStatus);
                }
              }}
              style={{
                ...buttonStyle,
                backgroundColor: '#6c757d',
                color: '#fff',
              }}
              onMouseOver={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1.05)')
              }
              onMouseOut={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1)')
              }
            >
              {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
            </button>
            <button
              onClick={leaveSession}
              style={{
                ...buttonStyle,
                backgroundColor: '#dc3545',
                color: '#fff',
              }}
              onMouseOver={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1.05)')
              }
              onMouseOut={(e) =>
                ((e.currentTarget as HTMLButtonElement).style.transform = 'scale(1)')
              }
            >
              나가기
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default DebateRoom;
