interface CardProps {
  name: string;
  loginId: string;
  birth: string;
  studentNumber: number;
  profileImagePath?: string | null;
  gender: string;
  certificateTime: string | null;
  onClick: () => void;
}

const Card = ({
  onClick,
  gender,
  certificateTime,
  name,
  loginId,
  birth,
  studentNumber,
  profileImagePath,
}: CardProps) => {
  return (
    <div
      onClick={onClick}
      className="border border-gray-200 rounded-[10px] flex w-[185px] h-[240px] p-[24px] flex-col items-center cursor-pointer"
    >
      <img
        src={profileImagePath || '/assets/images/tmpProfile.png'}
        className="h-[120px] w-[120px] rounded-full"
        alt="학생 프로필"
      />
      <div className="body-medium text-gray-800 text-center">
        {studentNumber}번 {name}
      </div>

      <div className="w-full flex justify-start items-center mt-2">
        <span className="text-gray-700 caption-medium mr-2">아이디</span>
        <span className="text-gray-700 caption-medium">{loginId}</span>
      </div>

      <div className="w-full flex justify-start items-center mt-2">
        <span className="text-gray-700 caption-medium mr-2">생년월일</span>
        <span className="text-gray-700 caption-medium">{birth}</span>
      </div>
    </div>
  );
};

export default Card;
