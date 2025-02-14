export const NotificationTile = ({
  type,
  message,
  time,
}: {
  type: string;
  message: string;
  time: string;
}) => (
  <div className="self-stretch py-3 justify-start items-center gap-6 inline-flex hover:bg-primary-50 cursor-pointer">
    <div className="grow shrink basis-0 h-6 justify-start items-center gap-6 flex px-2">
      <div className="w-[50px] text-start text-primary-500 body-medium">
        {type}
      </div>
      <div className="grow shrink basis-0 h-6 justify-start items-center gap-3 flex">
        <div className="grow shrink basis-0 text-gray-800 body-medium">
          {message}
        </div>
        <div className="text-gray-800 caption-medium">{time}</div>
      </div>
    </div>
  </div>
);
