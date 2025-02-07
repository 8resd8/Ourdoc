// components/atoms/Modal.tsx
import classes from './Modal.module.css';

interface ModalProps {
  isOpen: boolean;
  onConfirm: () => void;
  onCancel: () => void;
}

const Modal = ({ isOpen, onConfirm, onCancel }: ModalProps) => {
  if (!isOpen) return null;

  return (
    <div className={classes.modalOverlay}>
      <div className={classes.modalContent}>
        <div className="text-center">
          <div className="mb-4 headline-medium">
            입력하신 정보를 확인할게요.
          </div>
          <div className="text-primary-500">
            성룡 초등학교 1학년 3반 12번, 김미소님!
          </div>
          <div>생년월일은 2000년 4월 23일,</div>
          <div>성별은 남자,</div>
          <div>사용하시려는 아이디는 smile0423 입니다.</div>
          <div className="mt-4">회원가입을 진행할까요?</div>
        </div>
        <div className={classes.buttonContainer}>
          <button
            onClick={onCancel}
            className="w-[180px] h-[48px] rounded-[10px] border border-gray-200 text-gray-800 cursor-pointer"
          >
            아니요, 다시할래요
          </button>
          <button
            onClick={onConfirm}
            className="w-[180px] h-[48px] rounded-[10px] bg-primary-500 text-white cursor-pointer"
          >
            네, 진행할래요
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
