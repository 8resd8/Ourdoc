import { useEffect, useState } from 'react';
import Button from '../../atoms/Button';
import InputField from '../../molecules/InputField';
import classes from './StudentSetUp.module.css';
import Modal from '../../commons/Modal';
import { useLocation, useNavigate } from 'react-router-dom';
import {
  LoginResponse,
  setupStudentApi,
  SetupStudentRequest,
  signinApi,
} from '../../../services/usersService';
import { notify } from '../../commons/Toast';

const StudentSetup = () => {
  const [userType, setUserType] = useState('학생');
  const [loginInfo, setLoginInfo] = useState({ loginId: '', password: '' });
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isFormValid, setIsFormValid] = useState(false);
  const [studentNumber, setStudentNumber] = useState('');
  const [user, setuser] = useState<LoginResponse>();
  const router = useNavigate();

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const schoolName = queryParams.get('schoolName');
  const schoolId = queryParams.get('schoolId');
  const grade = queryParams.get('grade');
  const classNumber = queryParams.get('classNumber');
  const classId = queryParams.get('classId');

  const handleInputChange = (id: string, value: string) => {
    setLoginInfo((prev) => ({ ...prev, [id]: value }));
  };

  const handleLogin = async () => {
    try {
      const response = await signinApi({
        userType,
        loginId: loginInfo.loginId,
        password: loginInfo.password,
      });

      notify({ type: 'success', text: `반갑습니다, ${response.name}님!` });

      setuser(response);
      setIsLoggedIn(true);
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };

  const handleSetUp = async () => {
    try {
      const request: SetupStudentRequest = {
        classId: parseInt(classId!),
        classNumber: parseInt(classNumber!),
        grade: parseInt(grade!),
        schoolId: parseInt(schoolId!),
        schoolName: schoolName!,
        studentNumber: parseInt(studentNumber!),
      };

      await setupStudentApi(request);
      router('/pending');
    } catch (error) {}
  };

  const handleKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key === 'Enter') {
      handleLogin();
    }
  };
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleSignUpClick = () => {
    setIsModalOpen(true);
  };

  const handleConfirm = async () => {
    try {
      await handleSetUp();
      setIsModalOpen(false);
    } catch (error) {}
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    const isValid = Object.values(loginInfo).every((value) => value !== '');
    setIsFormValid(isValid);
  }, [loginInfo]);

  return (
    <div className={classes.root}>
      <div className={`${classes.base}`}>
        <div>
          <img
            className={`${classes.logo_img}`}
            alt="logoImage"
            src="/assets/images/logo1.png"
          />
        </div>
        {isLoggedIn ? (
          <div>
            <div>
              <span className="headline-medium text-primary-500 mb-2">
                {schoolName} {grade}학년 {classNumber}반
              </span>
              <span className="headline-medium text-gray-800 mb-2">
                에 오신 것을 환영해요!
              </span>
            </div>
            <div className="headline-medium text-gray-800 mt-2">
              출석번호를 입력해주세요.
            </div>
            <div className="caption-medium text-gray-800 mt-1">
              모든 칸은 필수 입력 칸입니다.
            </div>
            <div
              className={classes.input}
              onKeyDown={(e) => {
                if (e.key == 'Enter') {
                  handleSignUpClick();
                }
              }}
            >
              <InputField
                value=""
                inputType="text"
                validate=""
                id="studentNumber"
                label="출석번호"
                placeholder="출석번호를 입력해주세요"
                onChange={(value) => setStudentNumber(value)}
              />
            </div>
            <div className={classes.btn}>
              <Button
                type="filled"
                color="primary"
                title="소속변경"
                disabled={studentNumber == ''}
                onClick={handleSignUpClick}
              />
            </div>
          </div>
        ) : (
          <div>
            <div>
              <span className="headline-medium text-primary-500 mb-2">
                {schoolName} {grade}학년 {classNumber}반
              </span>
              <span className="headline-medium text-gray-800 mb-2">
                에 오신 것을 환영해요!
              </span>
              <div className="headline-medium text-gray-800 mt-2">
                로그인을 먼저 진행해주세요.
              </div>
            </div>
            <div className={classes.input}>
              <InputField
                value=""
                inputType="text"
                validate=""
                id="loginId"
                label="아이디"
                placeholder="아이디를 입력해주세요"
                onChange={(value) => handleInputChange('loginId', value)}
              />
            </div>
            <div className={classes.input} onKeyDown={handleKeyPress}>
              <InputField
                inputType="password"
                validate=""
                id="password"
                label="비밀번호"
                placeholder="비밀번호를 입력해주세요"
                onChange={(value) => handleInputChange('password', value)}
              />
            </div>
            <div className={classes.btn}>
              <Button
                type="filled"
                color="primary"
                title="로그인"
                disabled={!isFormValid}
                onClick={handleLogin}
              />
            </div>
          </div>
        )}
      </div>
      <Modal
        isOpen={isModalOpen}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
        title={'입력하신 정보를 확인할게요.'}
        body={
          <div>
            <div className="text-primary-500">
              {schoolName} {grade}학년 {classNumber}반 {studentNumber}번,{' '}
              {user?.name}님! 학년 반 번호가 정확하다면 소속 변경을
              진행해주세요.
            </div>
            <div className="mt-4 headline-small">소속변경을 진행할까요?</div>
          </div>
        }
        confirmText={'네, 진행할래요'}
        cancelText={'아니요, 다시할래요'}
      />
    </div>
  );
};

export default StudentSetup;
