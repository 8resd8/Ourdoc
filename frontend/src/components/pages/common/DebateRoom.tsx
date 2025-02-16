import { OpenVidu, Publisher, Session } from 'openvidu-browser';
import { useEffect, useRef, useState } from 'react';
import classes from './DabateRoom.module.css';
import { useLocation } from 'react-router-dom';

export const DebateRoom = () => {
  const location = useLocation();
  const token = location.state.token;
  const publisherRef = useRef<HTMLDivElement>(null);
  const subscribersRef = useRef<HTMLDivElement>(null);
  const OVRef = useRef<OpenVidu | null>(null);
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isAudioActive, setIsAudioActive] = useState<boolean>(true);
  const [isVideoActive, setIsVideoActive] = useState<boolean>(true);
  const [session, setSession] = useState<Session | null>(null);
  const [isScreenSharing, setIsScreenSharing] = useState<boolean>(false);
  const [screenPublisher, setScreenPublisher] = useState<Publisher | null>(
    null
  );

  // 이미 구독된 스트림에 대해 중복 호출하지 않도록 처리하는 헬퍼 함수
  const subscribeToStream = (stream: any, currentSession: Session) => {
    // 자신의 스트림이면 구독하지 않음
    if (
      stream.connection.connectionId === currentSession.connection.connectionId
    )
      return;
    // 이미 해당 스트림 구독 컨테이너가 있으면 중복 구독 방지
    if (document.getElementById(`subscriber-${stream.streamId}`)) return;

    const subscriberContainer = document.createElement('div');
    subscriberContainer.className = classes['subscriber-container'];
    subscriberContainer.id = `subscriber-${stream.streamId}`;

    // 클릭 시 확대/축소 기능 (CSS 클래스 토글)
    subscriberContainer.addEventListener('click', () => {
      subscriberContainer.classList.toggle(classes['enlarged']);
    });

    if (subscribersRef.current) {
      subscribersRef.current.appendChild(subscriberContainer);
    }

    currentSession.subscribe(stream, subscriberContainer);
  };

  // 누락된 스트림을 구독하기 위한 헬퍼 함수
  const subscribeToMissingStreams = (currentSession: Session) => {
    currentSession.remoteConnections.forEach((connection) => {
      if (
        connection.connectionId !== currentSession.connection.connectionId &&
        connection.stream &&
        !document.getElementById(`subscriber-${connection.stream.streamId}`)
      ) {
        subscribeToStream(connection.stream, currentSession);
      }
    });
  };

  // 백엔드 토큰 발급 후 세션에 입장하는 함수
  const joinSession = async () => {
    const OV = new OpenVidu();
    OVRef.current = OV;
    const mySession = OV.initSession();

    // 새로운 스트림 생성 시 구독 처리
    mySession.on('streamCreated', (event: any) => {
      subscribeToStream(event.stream, mySession);
    });

    // 원격 스트림 종료 시 구독자 컨테이너 제거
    mySession.on('streamDestroyed', (event: any) => {
      const subscriberContainer = document.getElementById(
        `subscriber-${event.stream.streamId}`
      );
      if (subscriberContainer && subscribersRef.current) {
        subscribersRef.current.removeChild(subscriberContainer);
      }
    });

    // 기존 사용자: 새 사용자가 동기화를 요청하면 sync-response 신호 전송
    mySession.on('signal:sync-request', (event: any) => {
      if (mySession.connection.connectionId !== event.from.connectionId) {
        mySession
          .signal({
            data: '',
            type: 'sync-response',
          })
          .catch((error) => console.error('sync-response error:', error));
      }
    });

    // 새 사용자: 기존 사용자로부터 sync-response 신호 수신 시 누락 스트림 재구독
    mySession.on('signal:sync-response', (event: any) => {
      subscribeToMissingStreams(mySession);
    });

    try {
      await mySession.connect(token);
      setSession(mySession);

      // 이미 연결된 참가자들의 스트림에 대해 즉시 구독
      mySession.remoteConnections.forEach((connection) => {
        if (connection.stream) {
          subscribeToStream(connection.stream, mySession);
        }
      });

      // 추가 구독 시도 및 동기화를 위한 signal 전송 (join 모드인 경우)
      setTimeout(() => {
        mySession.remoteConnections.forEach((connection) => {
          if (connection.stream) {
            subscribeToStream(connection.stream, mySession);
          }
        });
        mySession
          .signal({
            data: '',
            type: 'sync-request',
          })
          .catch((error) => console.error('sync-request error:', error));
      }, 500);
    } catch (error) {
      console.error('세션 참가 중 오류 발생:', error);
      alert('세션 참가 중 오류가 발생했습니다.');
    }
  };

  // 화면 공유 토글 함수
  const toggleScreenShare = async () => {
    if (!session || !OVRef.current) return;

    if (!isScreenSharing) {
      // 화면 공유 시작
      try {
        const screenPub = OVRef.current.initPublisher(
          publisherRef.current as HTMLElement,
          {
            videoSource: 'screen', // 화면 공유를 위한 옵션
            publishAudio: true,
            publishVideo: true,
            mirror: false,
            resolution: '640x480',
          },
          (error) => {
            if (error) {
              console.error('화면 공유 publisher 생성 오류:', error);
              alert('화면 공유 시작에 실패했습니다.');
            }
          }
        );

        screenPub.on('accessAllowed', () => {
          console.log('화면 공유 권한 허용됨');
        });
        screenPub.on('accessDenied', () => {
          console.warn('화면 공유 권한 거부됨');
        });
        screenPub.on('streamDestroyed', (event: any) => {
          console.log('화면 공유가 중단되었습니다.');
          setIsScreenSharing(false);
          setScreenPublisher(null);
          // 카메라 publisher가 존재한다면 다시 publish
          if (publisher) {
            session.publish(publisher);
          }
        });

        // 기존 카메라 publisher unpublish
        if (publisher) {
          session.unpublish(publisher);
        }

        session.publish(screenPub);
        setScreenPublisher(screenPub);
        setIsScreenSharing(true);
      } catch (error) {
        console.error('화면 공유 시작 오류:', error);
      }
    } else {
      // 화면 공유 종료
      if (screenPublisher) {
        session.unpublish(screenPublisher);
        setScreenPublisher(null);
      }
      if (publisher) {
        session.publish(publisher);
      }
      setIsScreenSharing(false);
    }
  };

  // 세션 종료 처리
  const leaveSession = () => {
    if (session) {
      session.disconnect();
    }
    setSession(null);
    setPublisher(null);
    if (subscribersRef.current) {
      subscribersRef.current.innerHTML = '';
    }
  };

  useEffect(() => {
    joinSession();
  }, []);

  // publisher 초기화: 세션 입장 후 실행
  useEffect(() => {
    if (session && !publisher && publisherRef.current && OVRef.current) {
      const publisherOptions = {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        mirror: true,
        resolution: '640x480',
      };
      const myPublisher = OVRef.current.initPublisher(
        publisherRef.current as HTMLElement,
        publisherOptions,
        (error) => {
          if (error) {
            console.error('Publisher 초기화 에러:', error);
            alert(
              '카메라/마이크 접근에 문제가 있습니다. 브라우저 권한을 확인하세요.'
            );
          }
        }
      );
      myPublisher.on('accessAllowed', () => {
        console.log('카메라/마이크 접근 허용됨');
      });
      myPublisher.on('accessDenied', () => {
        console.warn('카메라/마이크 접근 거부됨');
      });
      setPublisher(myPublisher);
      session.publish(myPublisher);
    }
  }, [session, publisher]);

  // 컴포넌트 언마운트 시 세션 종료
  useEffect(() => {
    return () => {
      if (session) session.disconnect();
    };
  }, [session]);

  return (
    <div className={classes.container}>
      <div className={classes.card}>
        <div className={classes['video-container']}>
          <div className={classes['video-area']} ref={publisherRef}></div>
          <div
            className={classes['subscribers-container']}
            ref={subscribersRef}
          ></div>
        </div>
        <div className={classes['button-group']}>
          <button
            onClick={() => {
              if (publisher) {
                const newAudioStatus = !isAudioActive;
                publisher.publishAudio(newAudioStatus);
                setIsAudioActive(newAudioStatus);
              }
            }}
            className={`${classes.button} ${classes['control-button']}`}
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
            className={`${classes.button} ${classes['control-button']}`}
          >
            {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
          </button>
          <button
            onClick={toggleScreenShare}
            className={`${classes.button} ${classes['control-button']}`}
          >
            {isScreenSharing ? '화면 공유 중지' : '화면 공유'}
          </button>
          <button
            onClick={leaveSession}
            className={`${classes.button} ${classes['leave-button']}`}
          >
            나가기
          </button>
        </div>
      </div>
    </div>
  );
};
