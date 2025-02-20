import { useRef, useState, useEffect } from 'react';
import classes from './UploadModal.module.css';

interface ModalProps {
  isOpen: boolean;
  onConfirm: (file: File | null) => void;
  onCancel: () => void;
  type: string;
}

const UploadModal = ({ type, isOpen, onConfirm, onCancel }: ModalProps) => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    // 모달이 닫힐 때 파일 선택 초기화
    if (!isOpen) {
      setSelectedFile(null);
      setPreviewUrl(null);
      if (fileInputRef.current) {
        fileInputRef.current.value = '';
      }
    }
  }, [isOpen]);

  if (!isOpen) return null;

  const handleFileClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      if (file.type === 'application/pdf' || file.type.startsWith('image/')) {
        setSelectedFile(file);

        if (type === 'profile' && file.type.startsWith('image/')) {
          const reader = new FileReader();
          reader.onloadend = () => {
            setPreviewUrl(reader.result as string);
          };
          reader.readAsDataURL(file);
        } else {
          setPreviewUrl(null);
        }
      } else {
        alert('PDF 또는 이미지 파일만 업로드 가능합니다.');
      }
    }
    // 파일 입력 초기화
    event.target.value = '';
  };

  const handleConfirm = () => {
    onConfirm(selectedFile);
  };

  const handleCancel = () => {
    setSelectedFile(null);
    setPreviewUrl(null);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
    onCancel();
  };

  return (
    <div className={classes.modalOverlay}>
      <div className={classes.modalContent}>
        <div className="text-center headline-medium">
          {type === 'profile' ? (
            <div className="mb-10">프로필 사진을 선택해주세요.</div>
          ) : (
            <div className="mb-10">재직증명서를 첨부해주세요.</div>
          )}
        </div>
        <div
          className={`m-auto cursor-pointer flex justify-center`}
          onClick={handleFileClick}
        >
          <input
            type="file"
            ref={fileInputRef}
            onChange={handleFileChange}
            accept={
              type === 'profile' ? '.jpg,.jpeg,.png' : '.pdf,.jpg,.jpeg,.png'
            }
            className="hidden"
          />
          {type === 'profile' && previewUrl ? (
            <img
              src={previewUrl}
              alt="Preview"
              className="w-48 h-48 object-cover rounded-full border-4 border-gray-200"
            />
          ) : (
            <img
              src="../assets/images/fileImage.png"
              alt="File upload icon"
              className="w-[48px] h-[48px] object-contain"
            />
          )}
        </div>
        {selectedFile && (
          <div className="body-medium text-gray-600 text-center mt-3">
            {selectedFile.name}
          </div>
        )}
        <div className={classes.buttonContainer}>
          <button
            onClick={handleCancel}
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
