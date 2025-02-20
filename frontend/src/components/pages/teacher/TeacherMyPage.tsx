import { useEffect, useState } from 'react';
import {
  createTeacherClass,
  getTeacherProfileApi,
  TeacherProfile,
} from '../../../services/teachersService';
import { changePasswordApi, signinApi } from '../../../services/usersService';
import { useNavigate } from 'react-router-dom';
import { notify } from '../../commons/Toast';
import { validateDate } from '@mui/x-date-pickers';

interface ModalProps {
  type: 'passwordConfirm' | 'passwordReset' | 'createClass' | 'profileEdit';
  onClose: () => void;
  loginClick: any;
  addClass: () => void;
  updatePassword: () => void;
  classData: any;
  onChange: (e: any) => void;
}

const Modal = ({
  classData,
  addClass,
  loginClick,
  updatePassword,
  type,
  onClose,
}: ModalProps) => {
  const [password, setPassword] = useState('');

  const login = () => {
    loginClick(password);
  };
  const addClassRoom = () => {
    addClass();
  };
  const update = async () => {
    try {
      if (passwordValidate == 'danger') {
        notify({
          type: 'error',
          text: '비밀번호를 확인하세요.',
        });
        return;
      }
      onClose();
      const response = await changePasswordApi({ newPassword: password });
      console.log(response);
    } catch (error) {}
  };

  const updatepassword = () => {
    if (passwordValidate == 'danger') {
      notify({
        type: 'error',
        text: '비밀번호를 확인하세요.',
      });
      return;
    }
    updatePassword();
  };
  const enterEvent = (e: any) => {
    if (e.key == 'Enter') {
      loginClick(password);
    }
  };
  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordValidate, setPasswordValidate] = useState<
    'warning' | 'success' | 'danger' | ''
  >('');

  const handleInputChange = (id: string, value: string) => {
    if (id == 'password') setPassword(value);
    if (id == 'check') setPasswordCheck(value);
  };

  useEffect(() => {
    setPasswordValidate(
      password !== '' && passwordCheck === password ? 'success' : 'danger'
    );
  }, [password, passwordCheck]);

  const getValidationMessage = () => {
    switch (passwordValidate) {
      case 'warning':
        return { message: 'Warning', icon: '/assets/images/Warning.svg' };
      case 'success':
        return { message: 'Success', icon: '/assets/images/Success.svg' };
      case 'danger':
        return { message: 'Danger', icon: '/assets/images/Danger.svg' };
      default:
        return { message: '', icon: '' };
    }
  };

  const { message, icon } = getValidationMessage();
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-300 bg-opacity-50">
      <div className="bg-gray-0 rounded-[30px] shadow-small p-6 w-[414px]">
        {type === 'passwordConfirm' && (
          <>
            <div className="text-center body-medium font-semibold text-gray-800">
              비밀번호를 입력해주세요.
            </div>
            <input
              onKeyDown={enterEvent}
              onChange={(e) => setPassword(e.target.value)}
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
              onChange={(e) => handleInputChange('password', e.target.value)}
            />
            <input
              type="password"
              className="mt-4 w-full border-b border-gray-800 p-2"
              placeholder="비밀번호 확인"
              onChange={(e) => handleInputChange('check', e.target.value)}
            />
            <div className={` mt-[8px] w-96 text-gray-800 caption-small`}>
              <span>
                <img src={icon} alt={message} />
              </span>
              <span>{message}</span>
            </div>
          </>
        )}
        {type === 'createClass' && (
          <>
            <div className="text-center body-medium font-semibold text-gray-800">
              새 학급 생성
            </div>
            <p className="mt-4 text-center text-gray-800">
              <span className="text-primary-500">
                {classData.school} {classData.grade}학년 {classData.class}반
              </span>
              으로 만들까요?
            </p>
          </>
        )}
        <div className="mt-6 flex justify-between ">
          <button
            onClick={onClose}
            className="flex-1 py-3 border border-gray-300 text-gray-400 rounded-[10px] mr-2 cursor-pointer"
          >
            취소
          </button>
          <button
            onClick={
              type == 'passwordConfirm'
                ? login
                : type == 'createClass'
                  ? addClassRoom
                  : update
            }
            className="flex-1 py-3 bg-primary-500 text-gray-0 rounded-[10px] ml-2 cursor-pointer"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

const TeacherMyPage = () => {
  const [modalType, setModalType] = useState<ModalProps['type'] | null>(null);
  const [teacherUser, setTeacherUser] = useState<TeacherProfile>();
  const navigate = useNavigate();
  const [newPassword, setNewPassword] = useState('');

  const changePassword = (e: any) => {
    setNewPassword(e.target.value);
  };
  const updatePassword = async () => {
    try {
      const response = await changePasswordApi({ newPassword: newPassword });
      setModalType(null);
      console.log(response);
    } catch (error) {}
  };

  const fetchData = async () => {
    const response = await getTeacherProfileApi();
    setTeacherUser(response);
  };
  console.log(teacherUser);

  const handleLogin = async (password: string) => {
    if (teacherUser)
      try {
        const response = await signinApi({
          userType: '교사',
          loginId: teacherUser.loginId,
          password: password,
        });
        navigate(`/teacher/profile-update?name=${teacherUser.name}&loginId=${teacherUser.loginId}&email=${teacherUser.email}&phone=${teacherUser.phone}&schoolName=${teacherUser.schoolName}&grade=${teacherUser.grade}&classNumber=${teacherUser.classNumber}&schoolId=${teacherUser.schoolId}&profileImage=${teacherUser.profileImagePath}
`);
      } catch (error: any) {
        notify({
          type: 'error',
          text: '사용자 인증에 실패했습니다.',
        });
        console.error('로그인 실패:', error);
      }
  };

  const createClass = async () => {
    try {
      const response = await createTeacherClass();
      console.log(response);
    } catch (error) {}
  };

  const classData = {
    grade: teacherUser?.grade,
    class: teacherUser?.classNumber,
    school: teacherUser?.schoolName,
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    teacherUser && (
      <div className="flex flex-col items-center p-6">
        <img
          className="w-48 h-48 rounded-full border border-gray-300"
          src={teacherUser.profileImagePath}
          alt="프로필 이미지"
        />
        <div className="mt-4 headline-medium font-semibold text-gray-800">
          {teacherUser.name} 님
        </div>
        <div className="mt-6 w-[414px] space-y-4">
          {[
            { label: '아이디', value: teacherUser.loginId },
            { label: '이메일', value: teacherUser.email },
            {
              label: '소속',
              value: `${teacherUser.schoolName} ${teacherUser.grade}학년 ${teacherUser.classNumber}반`,
            },
            { label: '전화번호', value: teacherUser.phone },
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
            className="flex-1 px-3 py-4 bg-primary-500 text-gray-0 rounded-[10px] ml-2 cursor-pointer"
          >
            새 학급 생성
          </button>
        </div>
        <div className="mt-6 space-y-4 w-[414px]">
          <button
            onClick={() => setModalType('passwordConfirm')}
            className="w-full py-3 border border-primary-500 text-primary-500 rounded-[10px] cursor-pointer"
          >
            회원정보 수정
          </button>
        </div>
        {modalType && (
          <Modal
            updatePassword={updatePassword}
            onChange={changePassword}
            classData={classData}
            addClass={createClass}
            loginClick={handleLogin}
            type={modalType}
            onClose={() => setModalType(null)}
          />
        )}
      </div>
    )
  );
};

export default TeacherMyPage;
