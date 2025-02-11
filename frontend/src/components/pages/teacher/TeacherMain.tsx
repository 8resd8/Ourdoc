import { signoutApi } from '../../../services/usersService';
import { useNavigate } from 'react-router';

const TeacherMain = () => {
  const navigate = useNavigate();

  const logout = async () => {
    try {
      await signoutApi();
      navigate('/');
    } catch (error) {
      console.error('로그아웃 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div>
      TeacherMain Component
      <div onClick={logout}>logout</div>
    </div>
  );
};

export default TeacherMain;
