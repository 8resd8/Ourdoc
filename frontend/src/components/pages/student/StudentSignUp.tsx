import { useState } from 'react';
import { signupStudentApi } from '../../../services/usersService';
import classes from './StudentSignup.module.css';
import InputField from '../../molecules/InputField';
import Button from '../../atoms/Button';
import Datepicker, { DateValueType } from 'react-tailwindcss-datepicker';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Label from '../../atoms/Label';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import SignupIdField from '../../molecules/SignupIdField';
import ButtonHalf from '../../atoms/ButtonHalf';
import RadioField from '../../molecules/RadioField';
import Modal from '../../commons/Modal';

interface signInRequestType {
  name: string;
  loginId: string;
  password: string;
  schoolName: string;
  grade: string;
  classNumber: string;
  studentNumber: string;
  birth: string;
}
const StudentSignUp = () => {
  const [gender, setGender] = useState('남자');
  const handleGenderChange = (selectedGender: string) => {
    setGender(selectedGender);
    setSignInRequest((prev) => ({ ...prev, gender: selectedGender }));
  };
  console.log(gender);
  const [signInRequest, setSignInRequest] = useState<signInRequestType>({
    name: '',
    loginId: '',
    password: '',
    schoolName: '',
    grade: '',
    classNumber: '',
    studentNumber: '',
    birth: '',
  });
  console.log(signInRequest);

  const handleInputChange = (id: string, value: string) => {
    setSignInRequest((prev) => ({ ...prev, [id]: value }));
  };

  const handleSignUp = async () => {
    try {
      // const response = await signUpApi({
      //   loginId: "",
      //   password: "",
      // });
      // console.log('회원가입 성공:', response);
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleSignUpClick = () => {
    setIsModalOpen(true);
  };

  const handleConfirm = async () => {
    try {
      await handleSignUp();
      setIsModalOpen(false);
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const [birthDate, setBirthDate] = useState(dayjs());

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
        <div>
          <div className="headline-medium text-gray-800 mb-2">
            성룡 초등학교 1학년 3반에 오신 것을 환영해요!
          </div>
          <span className="headline-medium text-gray-800">빈칸을 채우고, </span>

          <span className="headline-medium text-primary-500">회원가입</span>
          <span className="headline-medium text-gray-800">
            을 진행해주세요!
          </span>
          <div className="caption-medium text-gray-800 mb-4">
            모든 칸은 필수 입력 칸입니다.
          </div>
        </div>
        <div className={`${classes.input}`}>
          {/* <InputField
            id="loginId"
            label="아이디"
            placeholder="아이디를 입력해주세요"
            onChange={(value) => handleInputChange('loginId', value)}
          /> */}
          <SignupIdField
            id="loginId"
            label="아이디"
            placeholder="아이디를 입력해주세요"
            onChange={(value) => handleInputChange('loginId', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            id="password"
            label="비밀번호"
            placeholder="비밀번호를 입력해주세요"
            onChange={(value) => handleInputChange('password', value)}
          />
        </div>

        <div className={classes.input}>
          <InputField
            validate="warning"
            id="passwordCheck"
            label="비밀번호 확인"
            placeholder="비밀번호를 한번 더 입력해주세요"
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            id="name"
            label="이름"
            placeholder="이름을 입력해주세요"
            onChange={(value) => handleInputChange('name', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            id="number"
            label="출석번호"
            placeholder="출석번호를 입력해주세요"
            onChange={(value) => handleInputChange('studentNumber', value)}
          />
        </div>
        <div className={classes.input}>
          <Label label="생년월일" htmlFor="birth" />
          {/* <InputField
            id="birth"
            label="생년월일"
            placeholder="생년월일을 입력해주세요"
            onChange={(value) => handleInputChange('password', value)}
          /> */}
          <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="ko">
            <DatePicker
              slotProps={{
                popper: {
                  sx: {
                    '& .MuiPaper-root': {
                      minWidth: 400, // 팝업 너비
                      minHeight: 300, // 팝업 높이
                    },
                  },
                },
              }}
              openTo="year"
              views={['year', 'month', 'day']}
              format="YYYY-MM-DD"
              value={birthDate}
              onChange={(value: any) =>
                handleInputChange('birth', value.format('YYYY-MM-DD'))
              }
              sx={{
                minWidth: 420,
                '& .MuiOutlinedInput-notchedOutline': {
                  border: 'none',
                  borderRadius: 0,
                  borderBottom: '2px solid #e0e0e0',
                },
                '&:hover .MuiOutlinedInput-notchedOutline': {
                  borderBottom: '2px solid #e0e0e0',
                },
                '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                  borderBottom: '2px solid #ff6f61',
                },
              }}
            />
          </LocalizationProvider>
        </div>
        <div className={classes.input}>
          <RadioField
            id="gender"
            label="성별"
            selectedGender={gender}
            onGenderChange={handleGenderChange}
          />
        </div>
        <div className={classes.btn}>
          <Button type="" title="회원가입" onClick={handleSignUpClick} />
        </div>
        <Modal
          type="signup"
          isOpen={isModalOpen}
          onConfirm={handleConfirm}
          onCancel={handleCancel}
        />
      </div>
    </div>
  );
};

export default StudentSignUp;
