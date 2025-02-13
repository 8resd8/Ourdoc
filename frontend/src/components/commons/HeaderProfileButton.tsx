export const HeaderProfileButton = ({
  name,
  imagePath,
  onClick,
}: {
  name: string;
  imagePath: string;
  onClick: () => void;
}) => {
  return (
    <div
      className="flex flex-col items-center space-y-[8px] cursor-pointer"
      onClick={onClick}
    >
      <img src={imagePath} className="w-[40px]" />
      <div className="body-medium">{name}님</div>
    </div>
  );
};
