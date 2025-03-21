import { DropdownTile } from '../commons/DropdownTile';

export const DropdownMenu = ({
  list,
}: {
  list: { text: string; onClick: () => void }[];
}) => {
  return (
    <div className="right-[80px] w-[120px] p-1 bg-gray-0 border border-gray-200 shadow-xsmall cursor-pointer z-10">
      {list.map((item, index) => {
        return (
          <DropdownTile key={index} text={item.text} onClick={item.onClick} />
        );
      })}
    </div>
  );
};
