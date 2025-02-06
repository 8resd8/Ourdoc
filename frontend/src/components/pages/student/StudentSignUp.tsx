import { useState } from 'react';
import { signUpApi } from '../../../api/auth/AuthApi';
import classes from './StudentSignup.module.css';
import InputField from '../../molecules/InputField';
import Button from '../../atoms/Button';
import Datepicker, { DateValueType } from 'react-tailwindcss-datepicker';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Label from '../../atoms/Label';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
const StudentSignUp = () => {
  const [formData, setFormData] = useState({
    loginId: '',
    password: '',
    passwordCheck: '',
    name: '',
    number: '',
    gender: '',
  });
  const [birthDate, setBirthDate] = useState(dayjs());

  const validations = {
    loginId: (value: string) => {
      if (!value) return '아이디를 입력해주세요';
      if (value.length < 4) return '아이디는 4자 이상이어야 합니다';
      return null;
    },
    password: (value: string) => {
      if (!value) return '비밀번호를 입력해주세요';
      if (value.length < 8) return '비밀번호는 8자 이상이어야 합니다';
      return null;
    },
    passwordCheck: (value: string) => {
      if (!value) return '비밀번호를 다시 입력해주세요';
      if (value !== formData.password) return '비밀번호가 일치하지 않습니다';
      return null;
    },
    name: (value: string) => {
      if (!value) return '이름을 입력해주세요';
      return null;
    },
    number: (value: string) => {
      if (!value) return '출석번호를 입력해주세요';
      if (!/^\d+$/.test(value)) return '숫자만 입력해주세요';
      return null;
    },
    gender: (value: string) => {
      if (!value) return '성별을 선택해주세요';
      return null;
    },
  };

  const handleInputChange = (id: string, value: string) => {
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  return (
    <div className={classes.root}>
      <div className={classes.base}>
        <div>
          <img
            className={classes.logo_img}
            alt="logoImage"
            src="/assets/images/logo1.png"
          />
        </div>
        <div className={classes.input}>
          <InputField
            id="loginId"
            label="아이디"
            placeholder="아이디를 입력해주세요"
            value={formData.loginId}
            onChange={(value) => handleInputChange('loginId', value)}
            validation={validations.loginId}
          />
        </div>
        <div className={classes.input}>
          <InputField
            id="password"
            label="비밀번호"
            type="password"
            placeholder="비밀번호를 입력해주세요"
            value={formData.password}
            onChange={(value) => handleInputChange('password', value)}
            validation={validations.password}
          />
        </div>
        {/* 나머지 입력 필드들도 동일한 방식으로 수정 */}
        <div className={classes.btn}>
          <Button title="회원가입" onClick={handleSignUp} />
        </div>
      </div>
    </div>
  );
};
