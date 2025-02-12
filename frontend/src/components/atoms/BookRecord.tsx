interface BookRecordProps {
  date: string;
  count: string;
}

export const BookRecord = ({ date, count }: BookRecordProps) => {
  return (
    <div className="w-[366px] px-[72px] py-3 border-b border-gray-200 justify-between items-center inline-flex">
      <div className="body-medium text-gray-700">{date}</div>
      <div className="body-medium text-gray-800">{count}</div>
    </div>
  );
};
