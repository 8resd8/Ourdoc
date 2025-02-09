const RoomCard: React.FC<{
  title: string;
  school: string;
  teacher: string;
  currentParticipants: number;
  maxParticipants: number;
  date: string;
  selected?: boolean;
}> = ({
  title,
  school,
  teacher,
  currentParticipants,
  maxParticipants,
  date,
  selected,
}) => {
  return (
    <div
      className={`flex flex-col p-4 border rounded-lg shadow-sm ${
        selected ? 'bg-red-50 border-gray-200' : ' border-gray-200'
      }`}
    >
      <h3 className="text-lg font-semibold text-gray-800">{title}</h3>
      <p className="text-sm text-gray-500">
        {school} | {teacher}
      </p>
      <div className="flex justify-between items-center mt-4 text-sm text-gray-700">
        <span className="font-medium text-gray-600">
          {currentParticipants} / {maxParticipants} 명
        </span>
      </div>
      <div className="text-right text-xs text-gray-500 mt-2">{date}</div>
    </div>
  );
};

const WebRtcBoard: React.FC = () => {
  const rooms = Array(8).fill({
    title: '은혜로운 까치 토론방',
    school: '성룡 초등학교',
    teacher: '김보라 선생님',
    currentParticipants: 4,
    maxParticipants: 17,
    date: '2시간 전',
  });

  return (
    <div className="container mx-auto p-4 w-[950px]">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">독서토론 게시판</h1>
        <button className="px-4 py-2 border border-primary-500 border-w body-medium rounded-lg text-primary-500">
          방 만들기
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-4">
        {rooms.map((room, index) => (
          <RoomCard key={index} {...room} selected={index === 0} />
        ))}
      </div>

      <div className="flex justify-center items-center mt-8 space-x-2">
        <button className="px-4 py-2 border rounded-lg hover:bg-gray-100">
          &lt;
        </button>
        <span className="px-4 py-2 border rounded-lg bg-gray-100">1</span>
        <button className="px-4 py-2 border rounded-lg hover:bg-gray-100">
          &gt;
        </button>
      </div>
    </div>
  );
};

export default WebRtcBoard;
