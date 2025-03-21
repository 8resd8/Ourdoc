import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../atoms/Button';
import InputField from '../../molecules/InputField';
import classes from './SignIn.module.css';
import { signinApi } from '../../../services/usersService';
import { currentUserState } from '../../../recoil';
import { useSetRecoilState } from 'recoil';
import { notify } from '../../commons/Toast';
import { getRecoil } from 'recoil-nexus';

const SignIn = () => {
  const [userType, setUserType] = useState('학생');
  const [loginInfo, setLoginInfo] = useState({ loginId: '', password: '' });
  const navigate = useNavigate();
  const setUser = useSetRecoilState(currentUserState);

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

      setUser(response);

      if (userType === '학생') {
        navigate('/student/main');
      } else if (userType === '교사') {
        navigate('/teacher/main');
      }
    } catch (error: any) {
      notify({
        type: 'error',
        text: '로그인이 실패했습니다.',
      });
    }
  };

  const handleKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key === 'Enter') {
      handleLogin();
    }
  };

  useEffect(() => {
    if (getRecoil(currentUserState).role == '교사') {
      setUserType('교사');
    }
  }, []);

  return (
    <div className={classes.root}>
      <div className={`${classes.base} shadow-medium`}>
        <div>
          <img
            className={`${classes.logo_img}`}
            alt="logoImage"
            src="/assets/images/logo1.png"
          />
        </div>
        <div className={classes.toggle}>
          <span
            onClick={() => setUserType('학생')}
            className={`${classes.toggle_student} headline-medium ${
              userType === '학생'
                ? 'text-primary-500 border-b-2 border-b-primary-500'
                : 'text-gray-300'
            }`}
          >
            학생
          </span>
          <span className={`${classes.line} bg-gray-200`}></span>
          <span
            onClick={() => setUserType('교사')}
            className={`${classes.toggle_teacher} headline-medium ${
              userType === '교사'
                ? 'text-primary-500 border-b-2 border-b-primary-500'
                : 'text-gray-300'
            }`}
          >
            교사
          </span>
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            inputType="text"
            id="loginId"
            label="아이디"
            placeholder="아이디를 입력해주세요"
            onChange={(value) => handleInputChange('loginId', value)}
          />
        </div>
        <div className={classes.input} onKeyDown={handleKeyPress}>
          <InputField
            validate=""
            inputType="password"
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
        <div
          onClick={() => navigate('/teacher/signup')}
          className={`${classes.btn_admin}  ${userType === '교사' ? 'visible' : 'invisible'} mt-2 ml-84 text-gray-500 body-small`}
        >
          교사 회원가입
        </div>
      </div>
    </div>
  );
};

export default SignIn;
