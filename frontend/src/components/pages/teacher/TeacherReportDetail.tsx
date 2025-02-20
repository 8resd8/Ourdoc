import { useState, useEffect } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { currentUserState } from '../../../recoil';
import {
  BookReportDetail,
  getBookReportDetailApi,
  deleteBookReportApi,
  approveBookReportApi,
  createTeacherCommentApi,
  TeacherCommentRequest,
  deleteTeacherCommentApi,
  updateTeacherCommentApi,
} from '../../../services/bookReportsService';
import { DateFormat } from '../../../utils/DateFormat';
import { notify } from '../../commons/Toast';
import { HighlightText } from '../../atoms/HighlightText';

const TeacherReportDetail = () => {
  const { id } = useParams();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const name = queryParams.get('name');
  const studentNumber = queryParams.get('studentNumber');
  const [report, setReport] = useState<BookReportDetail | null>(null);
  const [teacherComment, setTeacherComment] = useState('');
  const [feedbackView, setFeedbackView] = useState(false);
  const fetchReport = async () => {
    const response = await getBookReportDetailApi(Number(id));
    setReport(response);
    setTeacherComment(response.teacherComment);
    console.log(response);
  };

  const reportApproveStamp = async (bookReportId: number) => {
    const response = await approveBookReportApi(bookReportId);
    fetchReport();
  };
  const addTeacherComment = async () => {
    const teacherCommentRequest: TeacherCommentRequest = {
      comment: teacherComment,
    };
    const response = await createTeacherCommentApi(
      Number(id),
      teacherCommentRequest
    );
    fetchReport();
    notify({
      type: 'success',
      text: '의견이 등록되었습니다.',
    });
  };
  const removeTeacherComment = async () => {
    const response = await deleteTeacherCommentApi(Number(id));
    notify({
      type: 'success',
      text: '의견이 삭제되었습니다.',
    });
    fetchReport();
  };
  const modifyTeacherComment = async () => {
    const teacherCommentRequest: TeacherCommentRequest = {
      comment: teacherComment,
    };
    const response = await updateTeacherCommentApi(
      Number(id),
      teacherCommentRequest
    );
    fetchReport();

    notify({
      type: 'success',
      text: '의견이 수정되었습니다.',
    });
  };
  console.log(report);

  useEffect(() => {
    fetchReport();
  }, []);

  return (
    <div className="py-3.5">
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
              {report?.schoolName} {report?.grade}학년 {report?.classNumber}반{' '}
              {report?.studentNumber}번 {name}
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
            {report?.bookReportApproveStatus == '있음' ? (
              <img
                src="/assets/images/goodStamp.png"
                className="w-[172px] h-[172px] absolute bottom-0 right-0"
              />
            ) : (
              ''
            )}
          </div>
          <div className="flex justify-end">
            {report?.bookReportApproveStatus == '있음' ? (
              <div>
                <button
                  onClick={() => setFeedbackView(!feedbackView)}
                  className="h-[50px] cursor-pointer inline-flex py-[12px] px-[10px] mt-2 mr-3 border text-system-success border-system-success rounded-[10px] justify-center items-center text-center body-medium"
                >
                  {feedbackView ? '선생님 의견 보기' : '맞춤법 검사'}
                </button>
              </div>
            ) : (
              <div>
                <button
                  onClick={() => setFeedbackView(!feedbackView)}
                  className="h-[50px] cursor-pointer inline-flex py-[12px] px-[10px] mt-2 mr-3 border text-system-success border-system-success rounded-[10px] justify-center items-center text-center body-medium"
                >
                  {feedbackView ? '선생님 의견 보기' : '맞춤법 검사'}
                </button>
                <button
                  onClick={() => reportApproveStamp(Number(id))}
                  className="cursor-pointer inline-flex py-[12px] px-[16px] mt-1 bg-primary-500 rounded-[10px] justify-center items-center text-center text-gray-0 body-medium"
                >
                  도장 찍기
                </button>
              </div>
            )}
          </div>
        </div>
        {!feedbackView && (
          <div className="flex flex-col w-[600px] mt-[120px]">
            <div>
              <div className="text-gray-800 body-small">AI 선생님 의견</div>
              <div className="w-[413px] h-m-[421px] px-6 py-4 rounded-[5px] border border-gray-200 justify-center items-center gap-2.5 inline-flex">
                <div className="w-[389px] report-font tracking-widest whitespace-pre-wrap">
                  {report?.aiComment}
                </div>
              </div>
            </div>
            <div className="mt-3">
              <div className="text-gray-800 body-small mt-2 mb-1">
                담임 선생님 의견
              </div>
              <textarea
                placeholder="의견을 작성해주세요."
                value={teacherComment === '없음' ? '' : teacherComment}
                onChange={(e) => setTeacherComment(e.target.value)}
                className="w-[413px] h-[126px] px-6 py-4 rounded-[5px] border border-gray-200 resize-none text-gray-800 report-font break-words"
                style={{
                  verticalAlign: 'top',
                  textAlign: 'left',
                }}
                disabled={report?.bookReportApproveStatus === '있음'}
              />

              {report?.teacherComment == '없음' ? (
                <div>
                  <div className="flex justify-end w-[413px] mt-3 ">
                    <div
                      onClick={() => addTeacherComment()}
                      className=" cursor-pointer inline-flex py-[12px] px-[16px] border border-secondary-500 rounded-[10px] justify-center items-center text-center text-secondary-500 body-medium"
                    >
                      의견 작성
                    </div>
                  </div>
                </div>
              ) : report?.bookReportApproveStatus == '없음' ? (
                <div>
                  <div className="flex justify-end w-[413px] mt-3">
                    <div
                      onClick={() => modifyTeacherComment()}
                      className="cursor-pointer inline-flex py-[12px] px-[16px] border border-secondary-500 rounded-[10px] justify-center items-center text-center text-secondary-500 body-medium mr-2"
                    >
                      의견 수정
                    </div>
                    <div
                      onClick={() => removeTeacherComment()}
                      className="cursor-pointer inline-flex py-[12px] px-[16px] border border-system-danger rounded-[10px] justify-center items-center text-center text-system-danger body-medium"
                    >
                      의견 삭제
                    </div>
                  </div>
                  <div>
                    <div className="flex justify-end w-[413px] mt-3"></div>
                  </div>
                </div>
              ) : (
                ''
              )}
            </div>
          </div>
        )}

        {feedbackView && (
          <div className="flex flex-col w-[600px] mr-[120px]">
            <div className="flex flex-col w-[600px] relative">
              <div className="flex flex-row justify-between">
                <div className="object-contain w-[150px] h-[120px]" />
                <div className="h-[140px] w-[100px]" />
              </div>
              <div className="text-end text-gray-800 report-font">{'    '}</div>
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
                <HighlightText
                  beforeContent={report?.beforeContent}
                  afterContent={report?.afterContent}
                />
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};
export default TeacherReportDetail;
