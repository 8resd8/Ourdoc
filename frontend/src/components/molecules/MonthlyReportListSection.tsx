const ReportItem = ({ date, count }: { date: string; count: number }) => (
  <div className="w-[366px] px-[72px] py-3 border-b border-gray-200 justify-between items-center inline-flex">
    <div className="text-gray-800 body-medium">{date}</div>
    <div className="text-gray-800 body-medium">{count}권</div>
  </div>
);

export const MonthlyReportListSection = () => {
  const reports = [
    { date: '11일', count: 1 },
    { date: '11일', count: 1 },
    { date: '11일', count: 2 },
    { date: '11일', count: 3 },
    { date: '11일', count: 1 },
    { date: '11일', count: 2 },
    { date: '11일', count: 2 },
    { date: '11일', count: 2 },
    { date: '11일', count: 2 },
  ];

  return (
    <div className="w-[444px] h-[368px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex ">
      <div className="text-gray-800 headline-medium">10월</div>
      <div className="self-stretch grow shrink basis-0 justify-start items-start gap-2 inline-flex overflow-y-scroll">
        <div className="grow shrink basis-0 self-stretch flex-col justify-start items-start inline-flex">
          {reports.map((report, index) => (
            <ReportItem key={index} date={report.date} count={report.count} />
          ))}
        </div>
      </div>
    </div>
  );
};
