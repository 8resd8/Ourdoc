import { useEffect, useState } from 'react';
import {
  getClassStudentListApi,
  StudentListResponse,
  StudentProfile,
} from '../../../services/teachersService';
import Card from '../../atoms/Card';
import classes from './TeacherClassInfo.module.css';
import { useNavigate } from 'react-router-dom';

const PAGE_SIZE = 10;

const TeacherClassInfo = () => {
  const navigate = useNavigate();
  const [students, setStudents] = useState<StudentProfile[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchStudentInfo = async (page = 0) => {
    try {
      const params = { size: PAGE_SIZE, page };
      const response: StudentListResponse =
        await getClassStudentListApi(params);

      setStudents(response.content);
      setTotalPages(response.totalPages); // totalPages를 API 응답에 맞게 수정
      setCurrentPage(page);
    } catch (error) {
      console.error('학생 정보 가져오기 실패:', error);
      setStudents([]);
    }
  };

  useEffect(() => {
    fetchStudentInfo();
  }, []);

  const navigateToAuth = () => {
    navigate('/teacher/class-auth');
  };

  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchStudentInfo(page);
    }
  };

  return (
    <div className={`${classes.root} mx-auto p-4`}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-large text-gray-800">학급 정보</h1>
        <button
          onClick={navigateToAuth}
          className="px-4 py-2 cursor-pointer border border-primary-500 rounded-lg text-primary-500"
        >
          인증 관리
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-5 gap-[12px] ">
        {students.map((student, key) => (
          <Card
            key={key}
            gender={student.gender}
            certificateTime={student.certificateTime}
            name={student.name}
            loginId={student.loginId}
            birth={student.birth}
            studentNumber={student.studentNumber}
            profileImagePath={student.profileImagePath}
            onClick={() =>
              navigate('/teacher/student-info', {
                state: {
                  studentNumber: student.studentNumber,
                  name: student.name,
                  loginId: student.loginId,
                  birth: student.birth,
                  gender: student.gender,
                  profileImagePath: student.profileImagePath,
                  certificateTime: student.certificateTime,
                },
              })
            }
          />
        ))}
      </div>

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

export default TeacherClassInfo;
