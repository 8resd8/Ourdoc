import { ReactNode } from 'react';
import classes from './Modal.module.css';

interface ModalProps {
  isOpen: boolean;
  title: string;
  body: ReactNode;
  confirmText: string;
  cancelText: string;
  onConfirm: () => void;
  onCancel: () => void;
}

const Modal = ({
  isOpen,
  title,
  body,
  confirmText,
  cancelText,
  onConfirm,
  onCancel,
}: ModalProps) => {
  if (!isOpen) return null;

  return (
    <div className={classes.modalOverlay}>
      <div className={classes.modalContent}>
        <div className="text-center">
          <div className="mb-4 headline-medium">{title}</div>
          <div>{body}</div>
        </div>
        <div className={classes.buttonContainer}>
          <button
            onClick={onCancel}
            className="w-[180px] h-[48px] rounded-[10px] border border-gray-200 text-gray-800 cursor-pointer"
          >
            {cancelText}
          </button>
          <button
            onClick={onConfirm}
            className="w-[180px] h-[48px] rounded-[10px] bg-primary-500 text-gray-0 cursor-pointer"
          >
            {confirmText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
