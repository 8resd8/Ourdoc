import { useEffect, useState } from 'react';
import {
  getStudentProfileApi,
  StudentProfileResponse,
  updateStudentProfileApi,
} from '../../../services/studentsService';
import UploadModal from '../../commons/UploadModal';

interface ModalProps {
  type: 'passwordConfirm' | 'passwordReset' | 'createClass';
  onClose: () => void;
}

const Modal = ({ type, onClose }: ModalProps) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-300 bg-opacity-50">
      <div className="bg-gray-0 rounded-[30px] shadow-small p-6 w-[414px]">
        {type === 'passwordConfirm' && (
          <>
            <div className="text-center body-medium font-semibold text-gray-800">
              비밀번호를 입력해주세요.
            </div>
            <input
              type="password"
              className="mt-4 w-full border-b border-gray-800 p-2"
              placeholder="비밀번호"
            />
          </>
        )}
        {type === 'passwordReset' && (
          <>
            <div className="text-center body-medium font-semibold text-gray-800">
              변경할 비밀번호를 입력해주세요.
            </div>
            <input
              type="password"
              className="mt-4 w-full border-b border-gray-800 p-2"
              placeholder="새 비밀번호"
            />
            <input
              type="password"
              className="mt-4 w-full border-b border-gray-800 p-2"
              placeholder="비밀번호 확인"
            />
          </>
        )}
        {type === 'createClass' && (
          <>
            <div className="text-center body-medium font-semibold text-gray-800">
              새 학급 생성
            </div>
            <p className="mt-4 text-center text-gray-800">
              <span className="text-primary-500">성룡초등학교 1학년 1반</span>
              으로 만들까요?
            </p>
          </>
        )}
        <div className="mt-6 flex justify-between">
          <button
            onClick={onClose}
            className="flex-1 py-3 border border-gray-300 text-gray-400 rounded-[10px] mr-2"
          >
            취소
          </button>
          <button className="flex-1 py-3 bg-primary-500 text-gray-0 rounded-[10px] ml-2">
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

const StudentMyPage = () => {
  const [modalType, setModalType] = useState<ModalProps['type'] | null>(null);
  const [user, setUser] = useState<StudentProfileResponse>();
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);

  const handleUploadClick = () => {
    setIsUploadModalOpen(true);
  };
  const handleUploadCancel = () => {
    setIsUploadModalOpen(false);
  };
  const handleUploadConfirm = async (file: File | null) => {
    if (file) {
      try {
        const formData = new FormData();
        formData.append('profileImage', file);
        const response = await updateStudentProfileApi(formData);
        userData();
      } catch (error) {
        console.error('프로필 이미지 업로드 실패:', error);
      }
    }
    setIsUploadModalOpen(false);
  };
  const userData = async () => {
    try {
      const response = await getStudentProfileApi();
      setUser(response);
    } catch (error) {}
  };
  useEffect(() => {
    userData();
  }, []);

  return (
    <div className="flex flex-col items-center p-6">
      <img
        className="w-48 h-48 rounded-full border border-gray-300"
        src={user?.profileImage}
        alt="프로필 이미지"
      />
      <div className="mt-4 headline-medium font-semibold text-gray-800">
        {user?.name} 님
      </div>
      <div className="mt-6 w-[414px] space-y-4">
        {[
          { label: '아이디', value: user?.loginId },
          {
            label: '소속',
            value:
              user?.schoolName +
              ' ' +
              user?.grade +
              '학년' +
              ' ' +
              user?.classNumber +
              '반',
          },
          { label: '생년월일', value: user?.birth },
        ].map((item, index) => (
          <div key={index} className="border-b pb-2">
            <div className="body-small text-gray-800">{item.label}</div>
            <div className="body-medium text-gray-800">{item.value}</div>
          </div>
        ))}
      </div>
      <div className="mt-6 space-y-4 w-[414px]">
        <button
          onClick={handleUploadClick}
          className="w-full py-3 border border-secondary-500 text-secondary-500 rounded-[10px] cursor-pointer"
        >
          프로필 이미지 수정
        </button>
      </div>
      <UploadModal
        type="profile"
        isOpen={isUploadModalOpen}
        onConfirm={handleUploadConfirm}
        onCancel={handleUploadCancel}
      />
    </div>
  );
};

export default StudentMyPage;
