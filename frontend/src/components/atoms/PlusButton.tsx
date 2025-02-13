export const PlusButton = ({ onClick }: { onClick: () => void }) => {
  return (
    <img
      src="/assets/images/plus.png"
      className="w-[24px] cursor-pointer"
      onClick={onClick}
    />
  );
};
