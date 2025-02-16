import { useEffect, useState } from 'react';
import {
  BookReportDetail,
  deleteBookReportApi,
  getBookReportDetailApi,
} from '../../../services/bookReportsService';
import { useParams } from 'react-router-dom';
import { format } from 'prettier';
import { DateFormat } from '../../../utils/DateFormat';
import { useRecoilValue } from 'recoil';
import { currentUserState } from '../../../recoil';

const StudentReportDetail = () => {
  const { id } = useParams();
  const [report, setReport] = useState<BookReportDetail | null>(null);
  const fetchReport = async () => {
    if (id) {
      const response = await getBookReportDetailApi(id);
      setReport(response);
      console.log(response);
    }
  };

  const deleteReport = async () => {
    const response = await deleteBookReportApi(Number(id));
    console.log(response);
  };
  useEffect(() => {
    fetchReport();
  }, []);
  const user = useRecoilValue(currentUserState);

  return (
    <div className="">
      <div className="flex flex-row justify-center">
        <div className="flex flex-col w-[600px] ml-[200px] mr-[120px]">
          <div className="flex flex-col w-[600px] relative">
            <div className="flex flex-row justify-between">
              <img
                src="/assets/images/writeReportTitle.png"
                className="object-contain w-[150px] h-[120px]"
              />
              <img
                src="/assets/images/writeReportLike.png"
                className="h-[100px] w-[100px]"
              />
            </div>
            <div className="text-end text-gray-800 report-font">
              {user.schoolName} {user.grade}학년 {user.classNumber}반{' '}
              {user.studentNumber}번
            </div>
            <div className="flex flex-row">
              <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                책제목
              </div>
              <div className="w-[530px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                {report?.bookTitle}
              </div>
            </div>
            <div className="flex flex-row">
              <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                지은이
              </div>
              <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                {report?.author}
              </div>
              <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                출판사
              </div>
              <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                {report?.publisher}
              </div>
              <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                작성일
              </div>
              <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
                {report && DateFormat(report.createdAt, 'report')}
              </div>
            </div>
            <div className="w-[600px] h-[543px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-gray-800 report-font break-words overflow-auto">
              {report?.beforeContent}
            </div>
            <img
              src="/assets/images/goodStamp.png"
              className="w-[172px] h-[172px] absolute bottom-0 right-0"
            />
          </div>
          <div className="flex justify-end">
            <div
              onClick={deleteReport}
              className="cursor-pointer inline-flex py-[12px] px-[16px] mt-1 bg-system-danger rounded-[10px] justify-center items-center text-center text-gray-0 body-medium"
            >
              삭제하기
            </div>
          </div>
        </div>
        <div className="flex flex-col w-[600px] mt-[110px]">
          <div>
            <div className="text-gray-800 body-small">AI 선생님 의견</div>
            <div className="w-[413px] h-[421px] px-6 py-4 rounded-[5px] border border-gray-200 justify-center items-center gap-2.5 inline-flex">
              <div className="w-[389px]">{report?.aiComment}</div>
            </div>
          </div>
          <div>
            <div className="text-gray-800 body-small">담임 선생님 의견</div>
            <div className="w-[413px] h-[126px] px-6 py-4 rounded-[5px] border border-gray-200 justify-center items-center gap-2.5 inline-flex">
              <div className="w-[389px]">{report?.teacherComment}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentReportDetail;
