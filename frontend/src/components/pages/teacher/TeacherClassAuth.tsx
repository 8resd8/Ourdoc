import { useEffect, useState } from 'react';
import {
  getPendingStudentsListApi,
  PendingStudentProfile,
} from '../../../services/teachersService';

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
  useEffect(() => {
    fetchClassAuth();
  }, []);

  return (
    <div className="w-[1056px] m-auto p-24">
      <div className="flex justify-between items-center mb-4">
        <h2 className="headline-large">학급 인증 관리</h2>
        <div className="flex space-x-2">
          <button className="w-[123px] h-[50px] border border-secondary-500 text-secondary-500 body-medium rounded-[10px]">
            신규 가입 초대
          </button>
          <button className="w-[123px] h-[50px] border border-primary-500 text-primary-500 body-medium rounded-[10px]">
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
                  <button className="text-system-success border border-system-success px-3 py-1 rounded body-small cursor-pointer">
                    승인
                  </button>
                  <button className="text-system-danger border border-system-danger px-3 py-1 rounded body-small cursor-pointer">
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
    </div>
  );
};

export default TeacherClassAuth;
