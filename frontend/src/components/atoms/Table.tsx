import { ReactNode } from 'react';

export enum TableAlignType {
  left,
  center,
  right,
}
interface TableHeaderProps {
  label: string;
  align?: TableAlignType;
  width?: number;
}

export const TableHeader = (props: TableHeaderProps) => {
  let align = '';
  switch (props.align) {
    case TableAlignType.left:
      align = 'text-start';
      break;
    case TableAlignType.center:
      align = 'text-center';
      break;
    case TableAlignType.right:
      align = 'text-end';
      break;
    default:
      align = 'text-center';
      break;
  }

  return (
    <div
      className={`text-gray-800 body-medium ${align}`}
      style={{ width: props.width }}
    >
      {props.label}
    </div>
  );
};

export const Table = ({
  headers,
  datas,
}: {
  headers: TableHeaderProps[];
  datas: ReactNode[];
}) => {
  return (
    <div className="self-stretch h-auto flex flex-col items-center">
      <div className="w-full h-12 px-6 py-3 border-b border-gray-700 flex items-center gap-[18px]">
        {headers.map((header, index) => (
          <TableHeader
            key={index}
            label={header.label}
            align={header.align}
            width={header.width ?? 120}
          />
        ))}
      </div>
      {datas.map((item, index) => {
        return (
          <div key={index} className="w-full flex flex-col">
            {item}
          </div>
        );
      })}
    </div>
  );
};
