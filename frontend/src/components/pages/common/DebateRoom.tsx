import React, {useState, useEffect, useRef} from 'react';
import {OpenVidu, Session, Publisher, Subscriber, StreamManager} from 'openvidu-browser';
import {useParams} from 'react-router-dom';
import {api} from "../../../services/api.ts";

const DebateRoom: React.FC = () => {
    const {sessionName} = useParams<{ sessionName: string }>();

    // 상태 변수
    const [session, setSession] = useState<Session | null>(null);
    const [publisher, setPublisher] = useState<Publisher | null>(null);
    const [userCount, setUserCount] = useState<number>(0);
    const [nickname, setNickname] = useState<string>('');
    const [isAudioActive, setIsAudioActive] = useState<boolean>(true);
    const [isVideoActive, setIsVideoActive] = useState<boolean>(true);

    // Ref 변수
    const publisherRef = useRef<HTMLDivElement>(null);
    const subscribersRef = useRef<HTMLDivElement>(null);
    const ovRef = useRef<OpenVidu | null>(null);

    // 세션 참가 함수
    const joinSession = async () => {
        if (!nickname.trim()) {
            alert('닉네임을 입력해주세요!');
            return;
        }

        // OpenVidu 인스턴스 생성 및 세션 초기화
        const ov = new OpenVidu();
        ov.enableProdMode();
        const mySession: Session = ov.initSession();

        try {
            // 백엔드에 토큰 발급 요청 (JWT 토큰은 실제 값으로 교체)
            const response = await api.post(
                '/openvidu/join',
                {sessionName, nickname},
                {
                    headers: {
                        Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0OCIsInJvbGUiOiLtlZnsg50iLCJpYXQiOjE3MzkyNzc1MzAsImV4cCI6MTczOTMxMzUzMH0.d2MB5umdBz0nId3e3H6S38zIpAWnuVWEtcrZN_-ZeZk`,
                    },
                    withCredentials: true,
                }
            );
            const {token} = response.data;

            // OpenVidu 인스턴스 저장
            ovRef.current = ov;

            // 새로운 원격 스트림 생성 이벤트 처리
            mySession.on('streamCreated', (event: any) => {
                console.log('새 스트림 생성:', event);
                const subscriberContainer = document.createElement('div');
                subscriberContainer.id = `subscriber-${event.stream.streamId}`;
                subscriberContainer.className = 'subscriber-box';
                if (subscribersRef.current) {
                    subscribersRef.current.appendChild(subscriberContainer);
                }
                // 원격 스트림 구독
                const subscriber: Subscriber = mySession.subscribe(event.stream, subscriberContainer);
                setUserCount(prev => prev + 1);
            });

            // 원격 스트림 종료 이벤트 처리
            mySession.on('streamDestroyed', (event: any) => {
                console.log('스트림 종료:', event);
                const subscriberContainer = document.getElementById(`subscriber-${event.stream.streamId}`);
                if (subscriberContainer && subscribersRef.current) {
                    subscribersRef.current.removeChild(subscriberContainer);
                }
                setUserCount(prev => prev - 1);
            });

            // 세션 연결 (clientData로 닉네임 전달)
            await mySession.connect(token, {clientData: nickname});
            setSession(mySession);
            setUserCount(1);

            // 로컬 영상(Publisher) 옵션 설정
            const publisherOptions = {
                audioSource: undefined, // 기본 오디오 장치 사용
                videoSource: undefined, // 기본 비디오 장치 사용
                publishAudio: true,
                publishVideo: true,
                mirror: true,
            };

            // publisherRef에 publisher 생성 및 렌더링
            if (publisherRef.current) {
                const myPublisher: Publisher = ov.initPublisher(publisherRef.current, publisherOptions, (error) => {
                    if (error) {
                        console.error('Publisher 초기화 에러:', error);
                    }
                });
                setPublisher(myPublisher);
                mySession.publish(myPublisher);
            }

        } catch (error) {
            console.error('세션 참가 중 오류 발생', error);
            alert('방이 가득 찼거나 오류가 발생했습니다.');
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
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-6">
            <h1 className="text-2xl font-bold mb-4">방 제목: {sessionName}</h1>
            <p className="mb-2">현재 참가자 수: {userCount} / 5</p>

            {/* 세션 미연결 시: 닉네임 입력 및 입장 버튼 */}
            {!session && (
                <>
                    <input
                        type="text"
                        placeholder="닉네임 입력"
                        value={nickname}
                        onChange={(e) => setNickname(e.target.value)}
                        className="w-64 px-3 py-2 border border-gray-300 rounded-md mb-4"
                    />
                    <button
                        onClick={joinSession}
                        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md"
                    >
                        입장
                    </button>
                </>
            )}

            {/* 세션 연결 후: 게시자 및 구독자 영상 영역과 컨트롤 */}
            {session && (
                <div className="mt-4 w-full flex flex-col items-center">
                    <p className="text-green-500 mb-4">세션에 성공적으로 연결됨!</p>
                    <div className="w-full flex justify-center gap-4">
                        <div
                            id="publisher"
                            ref={publisherRef}
                            className="w-64 h-48 border border-gray-400"
                        ></div>
                        <div
                            id="subscribers"
                            ref={subscribersRef}
                            className="w-64 h-48 border border-gray-400"
                        ></div>
                    </div>
                    <div className="mt-4 flex gap-4">
                        <button
                            onClick={toggleAudio}
                            className="bg-gray-500 hover:bg-gray-600 text-white px-3 py-2 rounded"
                        >
                            {isAudioActive ? '음소거' : '음소거 해제'}
                        </button>
                        <button
                            onClick={toggleVideo}
                            className="bg-gray-500 hover:bg-gray-600 text-white px-3 py-2 rounded"
                        >
                            {isVideoActive ? '비디오 끄기' : '비디오 켜기'}
                        </button>
                        <button
                            onClick={leaveSession}
                            className="bg-red-500 hover:bg-red-600 text-white px-3 py-2 rounded"
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
