import { DropdownTile } from '../commons/DropdownTile';

export const DropdownMenu = ({
  list,
}: {
  list: { text: string; onClick: () => void }[];
}) => {
  return (
    <div className="right-[80px] w-[120px] bg-gray-0 border border-gray-200 shadow-xsmall cursor-pointer z-10">
      {list.map((item) => {
        return <DropdownTile text={item.text} onClick={item.onClick} />;
      })}
    </div>
  );
};
