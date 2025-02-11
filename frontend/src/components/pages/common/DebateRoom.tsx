import React, {useState, useEffect, useRef} from 'react';
import {OpenVidu, Session, Publisher, Subscriber, StreamManager} from 'openvidu-browser';
import {useParams} from 'react-router-dom';
import {api} from "../../../services/api.ts";

const DebateRoom: React.FC = () => {
    const {sessionName} = useParams<{ sessionName: string }>();

    const [session, setSession] = useState<Session | null>(null);
    const [publisher, setPublisher] = useState<Publisher | null>(null);
    const [userCount, setUserCount] = useState<number>(0);
    const [nickname, setNickname] = useState<string>('');

    const publisherRef = useRef<HTMLDivElement>(null);
    const subscribersRef = useRef<HTMLDivElement>(null);
    const ovRef = useRef<OpenVidu | null>(null);

    const [OV, setOV] = useState<OpenVidu>();
    const [subscribers, setSubscribers] = useState<Array<StreamManager>>([]);

    const joinSession = async () => {
        if (!nickname.trim()) {
            alert('닉네임을 입력해주세요!');
            return;
        }
        const newOV = new OpenVidu();
        newOV.enableProdMode();
        const newSession = newOV.initSession();


        try {
            const response = await api.post(
                'http://localhost:8080/openvidu/join',
                {
                    sessionName,
                    nickname,
                },
                {
                    headers: {
                        Authorization: `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0OCIsInJvbGUiOiLtlZnsg50iLCJpYXQiOjE3MzkyNTQxNzYsImV4cCI6MTczOTI5MDE3Nn0.uFmydygK6m0008il1MJ10EHsOy5eO54srAXhO91CB3Q`,
                    },
                    withCredentials: true,
                }
            );
            const {token} = response.data;

            // OpenVidu 인스턴스와 세션 초기화
            const ov = new OpenVidu();
            ovRef.current = ov;
            const mySession: Session = ov.initSession();

            // 새로운 원격 스트림이 생성되면 구독 처리
            mySession.on('streamCreated', (event: any) => {
                console.log('새 스트림 생성:', event);
                // 구독자용 div 생성 (id는 streamId를 포함)
                const subscriberContainer = document.createElement('div');
                subscriberContainer.id = `subscriber-${event.stream.streamId}`;
                subscriberContainer.className = 'w-64 h-48 border border-gray-400 m-2';
                if (subscribersRef.current) {
                    subscribersRef.current.appendChild(subscriberContainer);
                }
                // 해당 div를 타겟으로 구독
                const subscriber: Subscriber = mySession.subscribe(event.stream, subscriberContainer);
                setUserCount((prev) => prev + 1);
            });

            // 원격 스트림이 종료되면 해당 div 제거
            mySession.on('streamDestroyed', (event: any) => {
                console.log('스트림 종료:', event);
                const subscriberContainer = document.getElementById(`subscriber-${event.stream.streamId}`);
                if (subscriberContainer && subscribersRef.current) {
                    subscribersRef.current.removeChild(subscriberContainer);
                }
                setUserCount((prev) => prev - 1);
            });

            // 세션 연결 (clientData로 닉네임 전송)
            await mySession.connect(token, {clientData: nickname});
            setSession(mySession);
            // 연결 성공 시, 내 영상(publisher)도 포함하여 초기 참가자 수 1로 설정
            setUserCount(1);

            // 로컬 영상(Publisher) 생성 옵션 설정
            const publisherOptions = {
                audioSource: undefined,    // 기본 오디오
                videoSource: undefined,    // 기본 비디오
                publishAudio: true,
                publishVideo: true,
                mirror: true,
            };

            // publisherRef에 publisher 생성 및 렌더링
            if (publisherRef.current) {
                const myPublisher: Publisher = ov.initPublisher(publisherRef.current, publisherOptions);
                setPublisher(myPublisher);
                mySession.publish(myPublisher);
            }
        } catch (error) {
            console.error('세션 참가 중 오류 발생', error);
            alert('방이 가득 찼거나 오류가 발생했습니다.');
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

            {/* 세션에 연결 전: 닉네임 입력 및 입장 버튼 */}
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

            {/* 세션 연결 후: 게시자 및 구독자 영상 영역 */}
            {session && (
                <div className="mt-4 w-full flex flex-col items-center">
                    <p className="text-green-500 mb-4">세션에 성공적으로 연결됨!</p>
                    <div className="w-full flex justify-center">
                        <div
                            id="publisher"
                            ref={publisherRef}
                            className="w-64 h-48 border border-gray-400"
                        ></div>
                    </div>
                    <div
                        id="subscribers"
                        ref={subscribersRef}
                        className="w-full flex flex-wrap justify-center mt-4"
                    >
                        {/* 구독자 영상은 streamCreated 이벤트에서 동적으로 추가됩니다. */}
                    </div>
                </div>
            )}
        </div>
    );
};

export default DebateRoom;
