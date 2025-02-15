import { useParams } from 'react-router-dom';
import { createBookReportApi } from '../../../services/bookReportsService';
import Modal from '../../commons/Modal';
import { useState } from 'react';
import { convertHandToTextApi } from '../../../services/ocrSService';
import { useRecoilValue } from 'recoil';
import { currentUserState } from '../../../recoil';

const StudentReportWrite = () => {
  const { bookId } = useParams();
  const [reportContent, setReportContent] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [ocrCheck, setOcrCheck] = useState(false);
  const [selectedImage, setSelectedImage] = useState<File | null>(null);

  const param = {
    bookId: bookId ? bookId : '',
    beforeContent: reportContent,
    imageUrl: '',
    ocrCheck: ocrCheck ? '사용' : '미사용',
  };
  const user = useRecoilValue(currentUserState);
  console.log(user);

  const writeReport = async () => {
    const response = await createBookReportApi(param);
    console.log(response);
  };
  console.log(reportContent);

  const handleOCR = async () => {
    if (selectedImage) {
      try {
        const response = await convertHandToTextApi(selectedImage);
        setReportContent(response.afterContent);
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
              은ㅇㅁㄹ
            </div>
          </div>
          <div className="flex flex-row">
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              지은이
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              김미소
            </div>
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              출판사
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              기므므
            </div>
            <div className="w-[70px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              작성일
            </div>
            <div className="w-[130px] py-[8px] px-[8px] border border-gray-900 justify-center items-center text-center text-gray-800 report-font truncate">
              2024.01.03
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
          <div className="flex justify-end">
            <div
              className="inline-flex py-[12px] w-[305px] border border-secondary-500 bg-gray-0 rounded-[10px] justify-center items-center text-center text-secondary-500 body-medium cursor-pointer"
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
          title={'독서록을 업로드 할까요?'}
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
