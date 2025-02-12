import React, {useState, useEffect, useRef} from 'react';
import {OpenVidu, Session, Publisher, Subscriber, StreamManager} from 'openvidu-browser';
import {useParams} from 'react-router-dom';
import {api} from '../../../services/api'; // 백엔드 API 호출용 axios 인스턴스 등

const DebateRoom: React.FC = () => {
    // URL 파라미터에서 세션명 추출
    const {sessionName} = useParams<{ sessionName: string }>();

    // 상태 변수들
    const [session, setSession] = useState<Session | null>(null);
    const [publisher, setPublisher] = useState<Publisher | null>(null);
    const [userCount, setUserCount] = useState<number>(0);
    const [nickname, setNickname] = useState<string>('');
    const [isAudioActive, setIsAudioActive] = useState<boolean>(true);
    const [isVideoActive, setIsVideoActive] = useState<boolean>(true);
    const [remoteStreams, setRemoteStreams] = useState<StreamManager[]>([]);

    // Ref 변수 (publisher 및 구독자 DOM 컨테이너)
    const publisherRef = useRef<HTMLDivElement>(null);
    const subscribersRef = useRef<HTMLDivElement>(null);
    const OVRef = useRef<OpenVidu | null>(null);

    // 세션 참가 및 미디어 게시 함수
    const joinSession = async () => {
        if (!nickname.trim()) {
            alert('닉네임을 입력해주세요!');
            return;
        }

        // 단일 OpenVidu 인스턴스 생성
        const OV = new OpenVidu();
        OV.enableProdMode();
        OVRef.current = OV;

        // 세션 초기화
        const mySession: Session = OV.initSession();

        // 원격 스트림 생성 이벤트 핸들러
        mySession.on('streamCreated', (event: any) => {
            console.log('새 스트림 생성:', event);
            // 구독자용 컨테이너 생성
            const subscriberContainer = document.createElement('div');
            subscriberContainer.id = `subscriber-${event.stream.streamId}`;
            subscriberContainer.style.width = '300px';
            subscriberContainer.style.height = '200px';
            subscriberContainer.style.border = '1px solid #ccc';
            subscriberContainer.style.margin = '5px';
            if (subscribersRef.current) {
                subscribersRef.current.appendChild(subscriberContainer);
            }
            // 원격 스트림 구독
            const subscriber: Subscriber = mySession.subscribe(event.stream, subscriberContainer);
            setRemoteStreams(prev => [...prev, subscriber]);
            setUserCount(prev => prev + 1);
        });

        // 원격 스트림 종료 이벤트 핸들러
        mySession.on('streamDestroyed', (event: any) => {
            console.log('스트림 종료:', event);
            const subscriberContainer = document.getElementById(`subscriber-${event.stream.streamId}`);
            if (subscriberContainer && subscribersRef.current) {
                subscribersRef.current.removeChild(subscriberContainer);
            }
            setRemoteStreams(prev =>
                prev.filter((streamManager) => streamManager !== event.stream)
            );
            setUserCount(prev => prev - 1);
        });

        try {
            // 백엔드로부터 OpenVidu 토큰 발급 요청
            // 백엔드 API는 /openvidu/join 엔드포인트를 통해 토큰을 발급합니다.
            const response = await api.post(
                '/openvidu/join',
                {sessionName, nickname},
                {
                    headers: {
                        Authorization: `Bearer YOUR_JWT_TOKEN_HERE`, // 실제 JWT 토큰으로 교체하세요.
                    },
                    withCredentials: true,
                }
            );
            const {token} = response.data;

            // 세션 연결 (clientData에 닉네임 전달)
            await mySession.connect(token, {clientData: nickname});
            setSession(mySession);
            setUserCount(1);

            // 로컬 미디어(카메라/마이크) 게시 옵션 설정
            const publisherOptions = {
                audioSource: undefined, // 기본 오디오 장치 사용
                videoSource: undefined, // 기본 비디오 장치 사용
                publishAudio: true,
                publishVideo: true,
                mirror: true,
            };

            // publisher 초기화 (로컬 스트림 요청)
            if (publisherRef.current) {
                const myPublisher: Publisher = OV.initPublisher(
                    publisherRef.current,
                    publisherOptions,
                    (error) => {
                        if (error) {
                            console.error('Publisher 초기화 에러:', error);
                            alert('카메라/마이크 접근에 문제가 있습니다. 브라우저 권한을 확인하세요.');
                        } else {
                            console.log('Publisher 초기화 성공');
                        }
                    }
                );
                setPublisher(myPublisher);
                // 로컬 스트림 게시
                mySession.publish(myPublisher);
            }
        } catch (error) {
            console.error('세션 참가 중 오류 발생', error);
            alert('세션 참가 중 오류가 발생했습니다.');
        }
    };

    // 오디오 토글 함수
    const toggleAudio = () => {
        if (publisher) {
            const newStatus = !isAudioActive;
            publisher.publishAudio(newStatus);
            setIsAudioActive(newStatus);
        }
    };

    // 비디오 토글 함수
    const toggleVideo = () => {
        if (publisher) {
            const newStatus = !isVideoActive;
            publisher.publishVideo(newStatus);
            setIsVideoActive(newStatus);
        }
    };

    // 세션 나가기 함수
    const leaveSession = () => {
        if (session) {
            session.disconnect();
            setSession(null);
            setPublisher(null);
            setRemoteStreams([]);
            setUserCount(0);
            if (subscribersRef.current) {
                subscribersRef.current.innerHTML = '';
            }
        }
    };

    // 컴포넌트 언마운트 시 세션 정리
    useEffect(() => {
        return () => {
            if (session) {
                session.disconnect();
            }
        };
    }, [session]);

    return (
        <div style={{padding: '20px', textAlign: 'center'}}>
            <h1>방 제목: {sessionName}</h1>
            <p>현재 참가자 수: {userCount} / 5</p>

            {/* 세션 미연결 상태: 닉네임 입력 및 입장 버튼 */}
            {!session ? (
                <>
                    <input
                        type="text"
                        placeholder="닉네임 입력"
                        value={nickname}
                        onChange={(e) => setNickname(e.target.value)}
                        style={{padding: '10px', fontSize: '16px', width: '250px', marginBottom: '10px'}}
                    />
                    <br/>
                    <button
                        onClick={joinSession}
                        style={{padding: '10px 20px', fontSize: '16px', cursor: 'pointer'}}
                    >
                        입장
                    </button>
                </>
            ) : (
                <>
                    {/* 세션 연결 상태: 로컬 Publisher와 원격 구독자 영역 */}
                    <div style={{display: 'flex', justifyContent: 'center', gap: '20px', marginTop: '20px'}}>
                        <div id="publisher" ref={publisherRef}
                             style={{width: '300px', height: '200px', border: '1px solid #000'}}></div>
                        <div id="subscribers" ref={subscribersRef}
                             style={{display: 'flex', flexWrap: 'wrap', gap: '10px'}}></div>
                    </div>
                    {/* 컨트롤 버튼 */}
                    <div style={{marginTop: '20px'}}>
                        <button onClick={toggleAudio} style={{padding: '10px', marginRight: '10px'}}>
                            {isAudioActive ? '음소거' : '음소거 해제'}
                        </button>
                        <button onClick={toggleVideo} style={{padding: '10px', marginRight: '10px'}}>
                            {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
                        </button>
                        <button onClick={leaveSession} style={{padding: '10px', backgroundColor: 'red', color: '#fff'}}>
                            나가기
                        </button>
                    </div>
                </>
            )}
        </div>
    );
};

export default DebateRoom;
