import { useEffect } from 'react';
import { getClassStudentListApi } from '../../../services/teachersService';
import Card from '../../atoms/Card';
import classes from './TeacherClassInfo.module.css';
import { useNavigate } from 'react-router-dom';

const TeacherClassInfo = () => {
  const navigate = useNavigate();

  const handleStudentInfo = async () => {
    try {
      const response = await getClassStudentListApi();
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const navigateToAuth = () => {
    navigate('/teacher/class-auth');
  };

  const handleCardClick = () => {
    navigate(`/teacher/student-info`);
  };

  useEffect(() => {
    handleStudentInfo();
  }, []);

  return (
    <div className={`${classes.root} mx-auto p-4`}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-large text-gray-800">학급 정보</h1>
        <button
          onClick={navigateToAuth}
          className="px-4 py-2 cursor-pointer border border-primary-500 border-w body-medium rounded-lg text-primary-500"
        >
          인증 관리
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-5 gap-[12px]">
        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((studentId) => (
          // <div onClick={() => handleCardClick()}>
          <Card key={studentId} />
          // </div>
        ))}
      </div>

      <div className="flex justify-center items-center mt-8 space-x-2">
        <button className="px-4 py-2 rounded-lg hover:bg-gray-100">&lt;</button>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg">1</span>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg">2</span>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg hover:bg-gray-100">
          3
        </span>
        <span className="px-4 py-2 text-gray-800 body-small rounded-lg hover:bg-gray-100">
          4
        </span>
        <button className="px-4 py-2 rounded-lg hover:bg-gray-100">&gt;</button>
      </div>
    </div>
  );
};

export default TeacherClassInfo;
