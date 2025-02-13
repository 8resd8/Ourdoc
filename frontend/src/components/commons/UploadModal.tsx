import { useRef, useState } from 'react';
import classes from './UploadModal.module.css';

interface ModalProps {
  isOpen: boolean;
  onConfirm: (file: File | null) => void;
  onCancel: () => void;
}

const UploadModal = ({ isOpen, onConfirm, onCancel }: ModalProps) => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  if (!isOpen) return null;

  const handleFileClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      if (file.type === 'application/pdf' || file.type.startsWith('image/')) {
        setSelectedFile(file);
      } else {
        alert('PDF 또는 이미지 파일만 업로드 가능합니다.');
      }
    }
  };

  const handleConfirm = () => {
    onConfirm(selectedFile);
  };

  return (
    <div className={classes.modalOverlay}>
      <div className={classes.modalContent}>
        <div className="text-center headline-medium">
          <div className="mb-10">재직증명서를 첨부해주세요.</div>
        </div>
        <div
          className={`${classes.icon} m-auto cursor-pointer`}
          onClick={handleFileClick}
        >
          <input
            type="file"
            ref={fileInputRef}
            onChange={handleFileChange}
            accept=".pdf,.jpg,"
            className="hidden"
          />
          <img src="../assets/images/fileImage.png" />
          {/* {selectedFile && (
            <div className="body-medium text-gray-600">{selectedFile.name}</div>
          )} */}
        </div>
        <div className={classes.buttonContainer}>
          <button
            onClick={onCancel}
            className="w-[180px] h-[48px] rounded-[10px] border border-gray-200 text-gray-800 cursor-pointer"
          >
            취소
          </button>
          <button
            onClick={handleConfirm}
            className="w-[180px] h-[48px] rounded-[10px] bg-primary-500 text-gray-0 cursor-pointer"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default UploadModal;
