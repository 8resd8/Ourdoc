export const AdminListTile = ({
  no,
  teacherId,
  loginId,
  name,
  email,
  phone,
  certificateImageUrl,
  onClick,
}: {
  no: number;
  teacherId: any;
  loginId: string;
  name: string;
  email: string;
  phone: string;
  certificateImageUrl: string;
  onClick: any;
}) => {
  return (
    <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50">
      <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
      <div className="w-[332px] truncate text-gray-800 body-medium ">
        {teacherId}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {loginId}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {name}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {email}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {phone}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        <a href={certificateImageUrl}>재직증명서 확인</a>
      </div>
      <div className="w-[200px] text-center">
        <button
          onClick={() => onClick(true, teacherId)}
          className="text-gray-700 body-small mr-5 cursor-pointer rounded-[10px]  border border-primary-200 hover:bg-primary-400 hover:text-gray-0 p-5"
        >
          승인
        </button>
        <button
          onClick={() => onClick(false, teacherId)}
          className="text-gray-700 body-small rounded-[10px] cursor-pointer border border-primary-200 hover:bg-primary-400 hover:text-gray-0 p-5"
        >
          거절
        </button>
      </div>
    </div>
  );
};
