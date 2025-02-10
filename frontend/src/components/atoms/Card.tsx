const Card = () => {
  return (
    <div className="border border-gray-200 rounded-[10px] flex w-[185px] h-[240px] p-[24px] flex-col items-center">
      <img
        src="/assets/images/tmpProfile.png"
        className="h-[120px] w-[120px]"
      />
      <div className="body-medium text-gray-800 text-center">1번 김현우</div>

      {/* 아이디 */}
      <div className="w-full flex justify-start items-center mt-2">
        <span className="text-gray-700 caption-medium mr-2">아이디</span>
        <span className="text-gray-700 caption-medium">readz</span>
      </div>

      {/* 생년월일 */}
      <div className="w-full flex justify-start items-center mt-2">
        <span className="text-gray-700 caption-medium mr-2">생년월일</span>
        <span className="text-gray-700 caption-medium">2000.01.10</span>
      </div>
    </div>
  );
};

export default Card;
