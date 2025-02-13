export const NotificationDetailTile = () => {
  const type = '독서록';
  const body =
    '안녕 클레오파트라 세상에서안녕 클레오파트라 세상에서안녕 클레오파트라안녕 클레오파트라 세상에서안녕 클레오파트라 세상에서안녕 클레오파트라';
  const writer = '클레오파트라';
  const date = '2시간전';
  const isRead = true;
  return (
    <div className="w-[846px] self-stretch p-[24px] justify-between items-center inline-flex hover:bg-primary-50 cursor-pointer">
      <div className="text-center text-primary-500 body-medium">{type}</div>
      <div className="w-[589px] flex-col justify-start items-end gap-3 inline-flex">
        <div
          className={`self-stretch ${isRead ? 'body-medium-bold' : 'body-medium'} text-gray-800 truncate`}
        >
          {body}
        </div>
        <div className="self-stretch text-gray-800 caption-medium truncate">
          {writer}
        </div>
      </div>
      <div className="text-center text-[#4e4e4e] body-medium">{date}</div>
    </div>
  );
};
