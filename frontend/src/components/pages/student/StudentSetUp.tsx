import { useState } from 'react';
import Button from '../../atoms/Button';
import InputField from '../../molecules/InputField';
import classes from './StudentSetUp.module.css';
import Modal from '../../commons/Modal';
import { useNavigate } from 'react-router-dom';

const StudentSetup = () => {
  const [userType, setUserType] = useState('학생');
  const [loginInfo, setLoginInfo] = useState({ loginId: '', password: '' });
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [studentNumber, setStudentNumber] = useState('');
  const router = useNavigate();
  const handleInputChange = (id: string, value: string) => {
    setLoginInfo((prev) => ({ ...prev, [id]: value }));
  };

  const handleLogin = async () => {
    try {
      const response = await signInApi({
        userType,
        loginId: loginInfo.loginId,
        password: loginInfo.password,
      });
      console.log('로그인 성공:', response);
      setIsLoggedIn(true);
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };

  const handleSetUp = async () => {
    try {
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
                성룡 초등학교 1학년 3반
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
            <div className={classes.input}>
              <InputField
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
                onClick={handleSignUpClick}
              />
            </div>
          </div>
        ) : (
          <div>
            <div>
              <img
                className={`${classes.logo_img}`}
                alt="logoImage"
                src="/assets/images/logo1.png"
              />
            </div>
            <div>
              <span className="headline-medium text-primary-500 mb-2">
                성룡 초등학교 1학년 3반
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
                validate=""
                id="loginId"
                label="아이디"
                placeholder="아이디를 입력해주세요"
                onChange={(value) => handleInputChange('loginId', value)}
              />
            </div>
            <div className={classes.input} onKeyDown={handleKeyPress}>
              <InputField
                validate="warning"
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
              성룡 초등학교 1학년 3반 12번, 김미소님!
            </div>
            <div>생년월일은 2000년 4월 23일,</div>
            <div>성별은 남자,</div>
            <div>사용하시려는 아이디는 smile0423 입니다.</div>
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
function signInApi(arg0: {
  userType: string;
  loginId: string;
  password: string;
}) {
  throw new Error('Function not implemented.');
}
