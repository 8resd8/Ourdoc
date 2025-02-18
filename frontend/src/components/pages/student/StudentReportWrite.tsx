import { useNavigate, useParams } from 'react-router-dom';
import {
  createBookReportApi,
  saveAiFeedbackApi,
} from '../../../services/bookReportsService';
import Modal from '../../commons/Modal';
import { useState } from 'react';
import { convertHandToTextApi } from '../../../services/ocrSService';
import { useRecoilValue } from 'recoil';
import { currentUserState } from '../../../recoil';
import {
  getAIFeedbackApi,
  getAISpellingApi,
} from '../../../services/aiService';
import { DateFormat } from '../../../utils/DateFormat';
import { setRecoil } from 'recoil-nexus';
import { loadingState } from '../../../recoil/atoms/loadingAtoms';

const StudentReportWrite = () => {
  const queryParams = new URLSearchParams(location.search);
  const homeworkId = queryParams.get('homeworkId');
  const author = queryParams.get('author');
  const title = queryParams.get('title');
  const publisher = queryParams.get('publisher');

  const { id } = useParams();
  const [reportContent, setReportContent] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [afterContent, setAfterContent] = useState('');
  const [ocrCheck, setOcrCheck] = useState(false);
  const [ocrImagePath, setOcrImagePath] = useState('');
  const [selectedImage, setSelectedImage] = useState<File | null>(null);

  const param = {
    bookId: id ? id : '',
    beforeContent: reportContent,
    imageUrl: ocrImagePath,
    ocrCheck: ocrCheck ? '사용' : '미사용',
    // homeworkId: homeworkId,
  };
  const user = useRecoilValue(currentUserState);
  const navigate = useNavigate();
  const writeReport = async () => {
    try {
      setRecoil(loadingState, true);
      const write = await createBookReportApi(param);
      const aiFeedback = await getAIFeedbackApi({ content: reportContent });
      const aiSpelling = await getAISpellingApi({ content: reportContent });
      setAfterContent(aiSpelling.feedbackContent);

      const save = saveAiFeedbackApi({
        bookReportId: write,
        feedbackContent: aiFeedback.feedbackContent,
        spellingContent: aiSpelling.feedbackContent,
      });

      setRecoil(loadingState, false);
      navigate(-1);
    } catch (error) {
      setRecoil(loadingState, false);
    }
  };

  const handleOCR = async () => {
    if (selectedImage) {
      try {
        const response = await convertHandToTextApi(selectedImage);
        setReportContent(response.enhancerContent);
        setOcrImagePath(response.ocrImagePath);
        setIsModalOpen(false);
        setOcrCheck(true);
      } catch (error) {
        console.error('OCR 변환 중 오류 발생:', error);
      }
    }
  };
  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setSelectedImage(file);
    }
  };

  const today = new Date().toISOString();
  return (
    <div className="flex flex-row justify-center">
      <div className="flex flex-col w-[600px]">
        <div className="flex flex-col w-[600px]">
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
              {title}
            </div>
          </div>
          <div className="flex flex-row">
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              지은이
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              {author}
            </div>
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              출판사
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              {publisher}
            </div>
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              작성일
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              {DateFormat(today, 'report')}
            </div>
          </div>
          <div className="w-[600px] h-[543px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-gray-800 report-font break-words overflow-auto">
            <textarea
              className="w-[550px] h-full resize-none outline-none border-none text-gray-800 report-font break-words"
              style={{ overflow: 'hidden' }}
              value={reportContent}
              onChange={(e) => setReportContent(e.target.value)}
            />
          </div>
          <div className="flex justify-end mt-4 gap-2">
            <div
              className="inline-flex py-[12px] pw-[2px] w-[305px] border border-secondary-500 bg-gray-0 rounded-[10px] justify-center items-center text-center text-secondary-500 body-medium cursor-pointer"
              onClick={() => setIsModalOpen(true)}
            >
              사진 업로드
            </div>
            <div
              onClick={writeReport}
              className="inline-flex py-[12px] w-[305px] bg-primary-500 rounded-[10px] justify-center items-center text-center text-gray-0 body-medium cursor-pointer"
            >
              독서록 저장
            </div>
          </div>
        </div>
        <Modal
          isOpen={isModalOpen}
          onConfirm={handleOCR}
          onCancel={() => setIsModalOpen(false)}
          title={
            '업로드 시 작성중이던 독서록이 사라져요. 독서록 업로드를 진행할까요?'
          }
          body={
            <div>
              <input
                className="cursor-pointer"
                type="file"
                accept="image/*"
                onChange={handleImageUpload}
              />
            </div>
          }
          confirmText={'네'}
          cancelText={'아니요'}
        />
      </div>
    </div>
  );
};

export default StudentReportWrite;
