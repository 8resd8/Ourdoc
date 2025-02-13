import React, {useState, useEffect, useRef} from 'react';
import {OpenVidu, Session, Publisher} from 'openvidu-browser';
import {useParams} from 'react-router-dom';
import {api} from '../../../services/api';
import './DabateRoom.css'; // 파일명이 DebateRoom.css 인지 확인하세요!

const DebateRoom: React.FC = () => {
    // URL 파라미터에서 sessionName 추출 (있으면 기본값으로 사용)
    const {sessionName} = useParams<{ sessionName: string }>();
    // 사용자가 입력하는 세션 이름 (초기값은 URL 파라미터 값 또는 빈 문자열)
    const [sessionInput, setSessionInput] = useState<string>(sessionName || '');
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

    // 백엔드에서 새 토큰을 발급받아 세션에 입장하는 함수
    const joinSession = async () => {
        if (joined) {
            alert('이미 입장하셨습니다.');
            return;
        }
        if (!sessionInput.trim()) {
            alert('세션 이름을 입력해주세요.');
            return;
        }
        if (!nickname.trim()) {
            alert('닉네임을 입력해주세요.');
            return;
        }

        const OV = new OpenVidu();
        OVRef.current = OV;
        const mySession = OV.initSession();

        // 원격 스트림 생성 시 구독 컨테이너 추가
        mySession.on('streamCreated', (event: any) => {
            const subscriberContainer = document.createElement('div');
            subscriberContainer.id = `subscriber-${event.stream.streamId}`;
            subscriberContainer.className = 'subscriber-container';
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
            // 백엔드에서 새 토큰 발급 요청 (JWT 토큰은 실제 값으로 대체)
            const response = await api.post(
                '/openvidu/join',
                {sessionName: sessionInput, nickname},
                {
                    headers: {Authorization: `Bearer YOUR_JWT_TOKEN_HERE`},
                    withCredentials: true,
                }
            );
            const {token} = response.data;

            // 새 토큰으로 세션 연결 (clientData에 닉네임 전달)
            await mySession.connect(token, {clientData: nickname});
            setSession(mySession);
            setUserCount(1);
            setJoined(true);
        } catch (error) {
            console.error('세션 참가 중 오류 발생:', error);
            alert('세션 참가 중 오류가 발생했습니다.');
        }
    };

    // publisherRef 렌더링 후 publisher 초기화 (카메라 화면 표시)
    useEffect(() => {
        if (
            joined &&
            session &&
            !publisher &&
            publisherRef.current &&
            OVRef.current
        ) {
            const publisherOptions = {
                audioSource: undefined,
                videoSource: undefined,
                publishAudio: true,
                publishVideo: true,
                mirror: true,
                resolution: '640x480',
            };
            const myPublisher = OVRef.current.initPublisher(
                publisherRef.current,
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

    // 공유하기 버튼 클릭 시, 세션 이름만 포함한 URL을 인코딩하여 클립보드에 복사
    // autojoin 파라미터를 추가하여 링크 클릭 시 자동 입장하도록 처리
    const shareLink = () => {
        const shareUrl = `${window.location.origin}/debate/${encodeURIComponent(
            sessionInput
        )}?autojoin=true`;
        navigator.clipboard
            .writeText(shareUrl)
            .then(() => alert('링크 복사 완료'))
            .catch((err) => console.error('링크 복사 중 오류:', err));
    };

    // URL의 autojoin 파라미터가 있을 경우, 자동으로 입장 (기본 닉네임은 'Guest'로 설정)
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        if (params.get('autojoin') === 'true' && !joined) {
            if (!nickname) {
                setNickname('Guest');
            }
            // sessionInput는 이미 URL 파라미터에서 초기화됨
            joinSession();
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [joined, nickname, sessionInput]);

    return (
        <div className="container">
            <header className="header-container">
                <h1 className="header">{joined ? sessionInput : 'Debate Room'}</h1>
            </header>

            {/* 입장 전 화면 */}
            {!joined && (
                <div className="card">
                    <div className="card-header">
                        <h2>Debate Room 입장</h2>
                        <p>세션 이름과 닉네임을 입력하고 입장하세요.</p>
                    </div>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder="세션 이름 입력"
                            value={sessionInput}
                            onChange={(e) => setSessionInput(e.target.value)}
                            className="input"
                        />
                    </div>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder="닉네임 입력"
                            value={nickname}
                            onChange={(e) => setNickname(e.target.value)}
                            className="input"
                        />
                        <button onClick={joinSession} className="button join-button">
                            입장하기
                        </button>
                    </div>
                </div>
            )}

            {/* 입장 후 영상 영역 */}
            {joined && (
                <div className="card">
                    <div className="card-header">
                        <h2>참가자 수: {userCount}</h2>
                    </div>

                    <div className="video-container">
                        <div className="video-area">
                            <div ref={publisherRef} className="publisher"></div>
                        </div>
                        <div ref={subscribersRef} className="subscribers-container"></div>
                    </div>

                    <div className="button-group">
                        <button
                            onClick={() => {
                                if (publisher) {
                                    const newAudioStatus = !isAudioActive;
                                    publisher.publishAudio(newAudioStatus);
                                    setIsAudioActive(newAudioStatus);
                                }
                            }}
                            className="button control-button"
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
                            className="button control-button"
                        >
                            {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
                        </button>
                        <button onClick={leaveSession} className="button leave-button">
                            나가기
                        </button>
                        <button onClick={shareLink} className="button share-button">
                            공유하기
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default DebateRoom;
