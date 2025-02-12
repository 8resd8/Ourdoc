import React, {useState, useEffect, useRef} from 'react';
import {OpenVidu, Session, Publisher, Subscriber} from 'openvidu-browser';
import {useParams} from 'react-router-dom';
import {api} from '../../../services/api';

const DebateRoom: React.FC = () => {
    // URL 파라미터에서 sessionName을 받아오며, 없으면 기본값 "TestRoom" 사용
    const {sessionName} = useParams<{ sessionName: string }>();
    const roomName = sessionName || 'TestRoom';

    // 상태 변수들
    const [session, setSession] = useState<Session | null>(null);
    const [publisher, setPublisher] = useState<Publisher | null>(null);
    const [userCount, setUserCount] = useState<number>(0);
    const [nickname, setNickname] = useState<string>('');
    const [isAudioActive, setIsAudioActive] = useState<boolean>(true);
    const [isVideoActive, setIsVideoActive] = useState<boolean>(true);

    // DOM Ref: publisher와 subscriber 컨테이너
    const publisherRef = useRef<HTMLDivElement>(null);
    const subscribersRef = useRef<HTMLDivElement>(null);
    const OVRef = useRef<OpenVidu | null>(null);

    // 세션 참가 함수
    const joinSession = async () => {
        if (!nickname.trim()) {
            alert('닉네임을 입력해주세요!');
            return;
        }

        // OpenVidu 인스턴스 생성
        const OV = new OpenVidu();
        OVRef.current = OV;

        // 세션 초기화
        const mySession: Session = OV.initSession();

        // 이벤트: 원격 스트림 생성 시 (구독)
        mySession.on('streamCreated', (event: any) => {
            console.log('새 원격 스트림 생성:', event);
            const subscriberContainer = document.createElement('div');
            subscriberContainer.id = `subscriber-${event.stream.streamId}`;
            subscriberContainer.style.width = '300px';
            subscriberContainer.style.height = '200px';
            subscriberContainer.style.border = '1px solid #ccc';
            subscriberContainer.style.margin = '5px';
            if (subscribersRef.current) {
                subscribersRef.current.appendChild(subscriberContainer);
            }
            const subscriber: Subscriber = mySession.subscribe(event.stream, subscriberContainer);
            setUserCount(prev => prev + 1);
        });

        // 이벤트: 원격 스트림 종료 시
        mySession.on('streamDestroyed', (event: any) => {
            console.log('원격 스트림 종료:', event);
            const subscriberContainer = document.getElementById(`subscriber-${event.stream.streamId}`);
            if (subscriberContainer && subscribersRef.current) {
                subscribersRef.current.removeChild(subscriberContainer);
            }
            setUserCount(prev => prev - 1);
        });

        try {
            // 백엔드로부터 토큰 발급 요청 (JWT 토큰을 실제 값으로 교체)
            const response = await api.post(
                '/openvidu/join',
                {sessionName: roomName, nickname},
                {
                    headers: {
                        Authorization: `Bearer YOUR_JWT_TOKEN_HERE`,
                    },
                    withCredentials: true,
                }
            );
            const {token} = response.data;
            console.log('받은 토큰:', token);

            // 세션 연결 (clientData로 닉네임 전달)
            await mySession.connect(token, {clientData: nickname});
            setSession(mySession);
            setUserCount(1);

            // 로컬 Publisher 옵션 설정 (이 시점에 getUserMedia가 호출되어 권한 요청 발생)
            const publisherOptions = {
                audioSource: undefined,
                videoSource: undefined,
                publishAudio: true,
                publishVideo: true,
                mirror: true,
            };

            // publisherRef.current를 컨테이너로 전달하여 OpenVidu가 내부적으로 video element를 생성하고 attach
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
                // 추가: publisher 이벤트로 권한 상태 확인
                myPublisher.on('accessAllowed', () => {
                    console.log('카메라/마이크 접근 허용됨');
                });
                myPublisher.on('accessDenied', () => {
                    console.warn('카메라/마이크 접근 거부됨');
                });
                setPublisher(myPublisher);
                // 로컬 Publisher 게시 (이때 브라우저가 getUserMedia 호출 → 권한 요청 표시)
                mySession.publish(myPublisher);
            }
        } catch (error) {
            console.error('세션 참가 중 오류 발생:', error);
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

    // 세션 나가기 함수 (disconnect 시 오류 발생하면 try-catch로 잡음)
    const leaveSession = () => {
        if (session) {
            try {
                session.disconnect();
            } catch (error) {
                console.error('세션 disconnect 중 에러:', error);
            }
            setSession(null);
            setPublisher(null);
            setUserCount(0);
            if (subscribersRef.current) {
                subscribersRef.current.innerHTML = '';
            }
        }
    };

    // 컴포넌트 언마운트 시 세션 정리 (에러 잡기)
    useEffect(() => {
        return () => {
            if (session) {
                try {
                    session.disconnect();
                } catch (error) {
                    console.error('Cleanup disconnect 에러:', error);
                }
            }
        };
    }, [session]);

    return (
        <div style={{padding: '20px', textAlign: 'center'}}>
            <h1>방 제목: {roomName}</h1>
            <p>현재 참가자 수: {userCount} / 5</p>
            {!session ? (
                <div id="join">
                    <h2>영상방 입장</h2>
                    <form
                        onSubmit={(e) => {
                            joinSession();
                            e.preventDefault();
                        }}
                    >
                        <div>
                            <label htmlFor="nickname">닉네임:</label>
                            <input
                                id="nickname"
                                type="text"
                                value={nickname}
                                onChange={(e) => setNickname(e.target.value)}
                                required
                            />
                        </div>
                        <button type="submit" style={{marginTop: '10px'}}>입장</button>
                    </form>
                </div>
            ) : (
                <div id="room">
                    <div id="room-header">
                        <h2>{roomName}</h2>
                        <button onClick={leaveSession}>나가기</button>
                    </div>
                    <div
                        id="video-container"
                        style={{
                            display: 'flex',
                            justifyContent: 'center',
                            gap: '20px',
                            marginTop: '20px',
                        }}
                    >
                        <div
                            id="publisher"
                            ref={publisherRef}
                            style={{
                                width: '300px',
                                height: '200px',
                                border: '1px solid #000',
                            }}
                        ></div>
                        <div
                            id="subscribers"
                            ref={subscribersRef}
                            style={{display: 'flex', flexWrap: 'wrap', gap: '10px'}}
                        ></div>
                    </div>
                    <div id="controls" style={{marginTop: '20px'}}>
                        <button onClick={toggleAudio} style={{marginRight: '10px'}}>
                            {isAudioActive ? '음소거' : '음소거 해제'}
                        </button>
                        <button onClick={toggleVideo} style={{marginRight: '10px'}}>
                            {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default DebateRoom;
