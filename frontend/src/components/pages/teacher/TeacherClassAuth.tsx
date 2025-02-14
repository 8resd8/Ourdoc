import { useEffect, useState } from 'react';
import {
  generateChangeInviteCodeApi,
  generateSignupInviteCodeApi,
  getPendingStudentsListApi,
  PendingStudentProfile,
  updateStudentAffiliationApi,
} from '../../../services/teachersService';
import Modal from '../../commons/Modal';
import { Copy } from 'lucide-react';
import CopyUrlButton from '../../commons/CopyUrlButton';

const TeacherClassAuth = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [students, setStudents] = useState<PendingStudentProfile[]>([]);
  const [totalPages, setTotalPages] = useState(1);
  const PAGE_SIZE = 10;
  const fetchClassAuth = async (page = 0) => {
    try {
      const params = { size: PAGE_SIZE, page };
      const response = await getPendingStudentsListApi(params);
      setTotalPages(response.totalPages);
      setStudents(response.content);
      setCurrentPage(page);
      console.log(response);
    } catch (error: any) {
      console.error('학급 인증 목록 조회 실패:', error);
    }
  };
  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchClassAuth(page);
    }
  };
  const handleApprove = async (
    studentLoginId: string,
    studentNumber: number,
    isApproved: boolean
  ) => {
    try {
      const response = await updateStudentAffiliationApi(
        studentLoginId,
        studentNumber,
        isApproved
      );
      console.log(response);

      fetchClassAuth(currentPage);
    } catch (error: any) {
      console.error('학생 소속 승인/거절 실패:', error);
    }
  };

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [qr, setQr] = useState('');
  const [url, setUrl] = useState('');
  /**
   * Base64 문자열을 이미지로 다운로드하는 함수
   */
  const downloadBase64Image = (base64Data: string, fileName: string) => {
    try {
      // Base64 → Blob 변환
      const byteString = atob(base64Data);
      const arrayBuffer = new ArrayBuffer(byteString.length);
      const uint8Array = new Uint8Array(arrayBuffer);
      for (let i = 0; i < byteString.length; i++) {
        uint8Array[i] = byteString.charCodeAt(i);
      }
      const blob = new Blob([uint8Array], { type: 'image/png' });

      // Blob → URL 생성
      const url = URL.createObjectURL(blob);

      // 다운로드 링크 생성 및 클릭
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      link.click();

      // URL 해제
      URL.revokeObjectURL(url);
    } catch (error) {
      console.error('이미지 다운로드 실패:', error);
    }
  };

  const handleConfirm = async () => {
    try {
      const base64Data = url;
      downloadBase64Image(base64Data, 'qr_code.png');
      setIsModalOpen(false);
    } catch (error) {
      console.error('QR 다운로드 실패:', error);
    }
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const inviteNewStudent = async () => {
    try {
      const response = await generateSignupInviteCodeApi();
      setQr(response.url as string);
      setUrl(response.qrImageBase64 as string);
      setIsModalOpen(true);
    } catch (error) {
      console.error('신규 가입 초대 코드 발급 실패:', error);
    }
  };

  const inviteChangeStudent = async () => {
    try {
      const response = await generateChangeInviteCodeApi();
      setQr(response.url as string);
      setUrl(response.qrImageBase64 as string);
      setIsModalOpen(true);
    } catch (error) {
      console.error('소속 변경 초대 코드 발급 실패:', error);
    }
  };
  useEffect(() => {
    fetchClassAuth();
  }, []);

  return (
    <div className="w-[1056px] m-auto p-24">
      <div className="flex justify-between items-center mb-4">
        <h2 className="headline-large">학급 인증 관리</h2>
        <div className="flex space-x-2">
          <button
            onClick={inviteNewStudent}
            className="cursor-pointer w-[123px] h-[50px] border border-secondary-500 text-secondary-500 body-medium rounded-[10px]"
          >
            신규 가입 초대
          </button>
          <button
            onClick={inviteChangeStudent}
            className="cursor-pointer w-[123px] h-[50px] border border-primary-500 text-primary-500 body-medium rounded-[10px]"
          >
            소속 변경 초대
          </button>
        </div>
      </div>
      <table className="w-full border-collapse">
        <thead>
          <tr>
            <th className=" body-medium border-gray-200 px-4 py-2">No</th>
            <th className=" body-medium border-gray-200 px-4 py-2">번호</th>
            <th className=" body-medium border-gray-200 px-4 py-2">
              학생 이름
            </th>
            <th className=" body-medium border-gray-200 px-4 py-2">아이디</th>
            <th className=" body-medium border-gray-200 px-4 py-2">생년월일</th>
            <th className=" body-medium border-gray-200 px-4 py-2">
              승인 결과
            </th>
          </tr>
        </thead>
        <tbody>
          {students.map((row, index) => (
            <tr key={index} className="text-center">
              <td className=" border-gray-200 px-4 py-2">
                {index + 10 * currentPage + 1}
              </td>
              <td className=" border-gray-200 px-4 py-2">
                {row.studentNumber}
              </td>
              <td className=" border-gray-200 px-4 py-2">{row.name}</td>
              <td className=" border-gray-200 px-4 py-2">{row.loginId}</td>
              <td className=" border-gray-200 px-4 py-2">{row.birth}</td>
              <td className=" border-gray-200 px-4 py-2">
                <div className="flex space-x-2 justify-center">
                  <button
                    onClick={() => {
                      handleApprove(row.loginId, row.studentNumber, true);
                    }}
                    className="text-system-success border border-system-success px-3 py-1 rounded body-small cursor-pointer"
                  >
                    승인
                  </button>
                  <button
                    onClick={() => {
                      handleApprove(row.loginId, row.studentNumber, false);
                    }}
                    className="text-system-danger border border-system-danger px-3 py-1 rounded body-small cursor-pointer"
                  >
                    거절
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* 페이지네이션 */}
      <div className="flex justify-center items-center mt-8 space-x-2">
        {/* 이전 페이지 버튼 */}
        <button
          className={`px-4 py-2 rounded-lg cursor-pointer ${
            currentPage === 0
              ? 'text-gray-400 cursor-not-allowed'
              : 'hover:bg-gray-100'
          }`}
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 0}
        >
          &lt;
        </button>

        {/* 페이지 번호 표시 */}
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            className={`px-4 py-2 body-small rounded-lg cursor-pointer ${
              currentPage === index
                ? 'bg-primary-500 text-white'
                : 'hover:bg-gray-100 text-gray-500'
            }`}
            onClick={() => handlePageChange(index)}
          >
            {index + 1}
          </button>
        ))}

        {/* 다음 페이지 버튼 */}
        <button
          className={`px-4 py-2 rounded-lg cursor-pointer ${
            currentPage === totalPages - 1
              ? 'text-gray-400 cursor-not-allowed'
              : 'hover:bg-gray-100'
          }`}
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages - 1}
        >
          &gt;
        </button>
      </div>
      <Modal
        isOpen={isModalOpen}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
        title={''}
        body={
          <div>
            <img
              className="w-full"
              src={`data:image/jpeg;base64,${url}`}
              alt="QR Code"
            />
            <div className="flex justify-end">
              <CopyUrlButton url={qr} />
            </div>
          </div>
        }
        confirmText={'다운로드'}
        cancelText={'닫기'}
      />
    </div>
  );
};

export default TeacherClassAuth;
