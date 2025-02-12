import React, { useState } from 'react';

interface ModalProps {
  type: 'passwordConfirm' | 'passwordReset' | 'createClass';
  onClose: () => void;
}

const Modal: React.FC<ModalProps> = ({ type, onClose }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-300 bg-opacity-50">
      <div className="bg-white rounded-[30px] shadow-lg p-6 w-[414px]">
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
              <span className="text-red-500">성룡초등학교 1학년 1반</span>
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
          <button className="flex-1 py-3 bg-primary-500 text-white rounded-[10px] ml-2">
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

const TeacherMyPage = () => {
  const [modalType, setModalType] = useState<ModalProps['type'] | null>(null);

  return (
    <div className="flex flex-col items-center p-6">
      <img
        className="w-48 h-48 rounded-full border border-gray-300"
        src="/assets/images/tmpProfile.png"
        alt="프로필 이미지"
      />
      <div className="mt-4 text-2xl font-semibold text-gray-800">나미소 님</div>
      <div className="mt-6 w-[414px] space-y-4">
        {[
          { label: '아이디', value: 'usertest' },
          { label: '이메일', value: 'usertest@baver.com' },
          { label: '소속', value: 'ㅁㅁ초등학교 1학년 2반' },
          { label: '전화번호', value: '010-1441-1441' },
        ].map((item, index) => (
          <div key={index} className="border-b pb-2">
            <div className="body-small text-gray-800">{item.label}</div>
            <div className="body-medium text-gray-800">{item.value}</div>
          </div>
        ))}
      </div>
      <div className="mt-6 w-[414px] flex justify-between">
        <button
          onClick={() => setModalType('passwordReset')}
          className="flex-1 px-3 py-4 border border-system-danger text-system-danger rounded-[10px] mr-2 cursor-pointer"
        >
          비밀번호 재설정
        </button>
        <button
          onClick={() => setModalType('createClass')}
          className="flex-1 px-3 py-4 bg-primary-500 text-white rounded-[10px] ml-2 cursor-pointer"
        >
          새 학급 생성
        </button>
      </div>
      <div className="mt-6 space-y-4 w-[414px]">
        <button className="w-full py-3 border border-secondary-500 text-secondary-500 rounded-[10px] cursor-pointer">
          프로필 이미지 수정
        </button>
        <button
          onClick={() => setModalType('passwordConfirm')}
          className="w-full py-3 border border-primary-500 text-primary-500 rounded-[10px] cursor-pointer"
        >
          회원정보 수정
        </button>
      </div>
      {modalType && (
        <Modal type={modalType} onClose={() => setModalType(null)} />
      )}
    </div>
  );
};

export default TeacherMyPage;
