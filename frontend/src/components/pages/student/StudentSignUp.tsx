import { useState, useEffect } from 'react';
import {
  checkIdApi,
  signupStudentApi,
  SignupStudentRequest,
} from '../../../services/usersService';
import classes from './StudentSignup.module.css';
import InputField from '../../molecules/InputField';
import Button from '../../atoms/Button';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Label from '../../atoms/Label';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import SignupIdField from '../../molecules/SignupIdField';
import RadioField from '../../molecules/RadioField';
import Modal from '../../commons/Modal';
import { notify } from '../../commons/Toast';
import { useLocation, useNavigate } from 'react-router-dom';

const StudentSignUp = () => {
  const [gender, setGender] = useState('남');
  const handleGenderChange = (selectedGender: string) => {
    setGender(selectedGender);
    setSignUpRequest((prev) => ({ ...prev, gender: selectedGender }));
  };
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const schoolName = queryParams.get('schoolName');
  console.log('schoolName', schoolName);
  const schoolId = queryParams.get('schoolId');
  console.log('schoolId', schoolId);
  const grade = queryParams.get('grade');
  console.log('grade', grade);
  const classNumber = queryParams.get('classNumber');
  console.log('classNumber', classNumber);

  const [signUpRequest, setSignUpRequest] = useState<SignupStudentRequest>({
    loginId: '',
    password: '',
    name: '',
    studentNumber: 0,
    birth: '',
    gender: gender,
    schoolName: schoolName || '',
    schoolId: schoolId ? parseInt(schoolId) : 0,
    grade: grade ? parseInt(grade) : 0,
    classNumber: classNumber ? parseInt(classNumber) : 0,
  });

  const [isFormValid, setIsFormValid] = useState(false);
  const [isIdChecked, setIsIdChecked] = useState(false);
  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordValidate, setPasswordValidate] = useState<
    'warning' | 'success' | 'danger' | ''
  >('');

  useEffect(() => {
    const isValid =
      Object.values(signUpRequest).every((value) => value !== '') &&
      isIdChecked &&
      passwordValidate === 'success';
    setIsFormValid(isValid);
  }, [signUpRequest, isIdChecked, passwordValidate]);

  const handleInputChange = (id: string, value: string) => {
    setSignUpRequest((prev) => ({ ...prev, [id]: value }));
    if (id === 'loginId') {
      setIsIdChecked(false);
    }
    if (id === 'passwordCheck') {
      setPasswordCheck(value);
      setPasswordValidate(
        value === signUpRequest.password ? 'success' : 'danger'
      );
    }
  };
  const router = useNavigate();
  const handleSignUp = async () => {
    try {
      console.log('회원가입 요청:', signUpRequest);
      const response = await signupStudentApi(signUpRequest);
      console.log('회원가입 성공:', response);
      notify({
        type: 'success',
        text: '회원가입이 완료되었습니다.',
      });
      router('/pending'); // 승인대기 페이지로 이동
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };

  const handleCheckDuplicateId = async () => {
    try {
      if (signUpRequest.loginId === '') {
        notify({
          type: 'error',
          text: '아이디를 입력해주세요',
        });
        return;
      }
      const response = await checkIdApi({ loginId: signUpRequest.loginId });
      if (response) {
        notify({
          type: 'error',
          text: '중복된 아이디입니다.',
        });
      } else {
        notify({
          type: 'success',
          text: '사용가능한 아이디입니다.',
        });
        setIsIdChecked(true);
      }
    } catch (error) {
      console.error('아이디 중복 확인 실패:', error);
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

  const [birthDate, setBirthDate] = useState<dayjs.Dayjs | null>(null);

  const handleBirthDateChange = (newValue: dayjs.Dayjs | null) => {
    setBirthDate(newValue);
    handleInputChange('birth', newValue?.format('YYYY-MM-DD') || '');
  };
  console.log(gender);

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
          <SignupIdField
            id="loginId"
            label="아이디"
            placeholder="아이디를 입력해주세요"
            onChange={(value) => handleInputChange('loginId', value)}
            onCheckDuplicate={handleCheckDuplicateId}
            isIdChecked={isIdChecked}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            inputType="password"
            id="password"
            label="비밀번호"
            placeholder="비밀번호를 입력해주세요"
            onChange={(value) => handleInputChange('password', value)}
          />
        </div>

        <div className={classes.input}>
          <InputField
            validate={passwordValidate}
            id="passwordCheck"
            inputType="password"
            label="비밀번호 확인"
            placeholder="비밀번호를 한번 더 입력해주세요"
            onChange={(value) => handleInputChange('passwordCheck', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            inputType="text"
            id="name"
            label="이름"
            placeholder="이름을 입력해주세요"
            onChange={(value) => handleInputChange('name', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            inputType="text"
            id="number"
            label="출석번호"
            placeholder="출석번호를 입력해주세요"
            onChange={(value) => handleInputChange('studentNumber', value)}
          />
        </div>
        <div className={classes.input}>
          <Label label="생년월일" htmlFor="birth" />
          <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="ko">
            <DatePicker
              value={birthDate}
              onChange={handleBirthDateChange}
              format="YYYY-MM-DD"
              views={['year', 'month', 'day']}
              openTo="year"
              slotProps={{
                popper: {
                  sx: {
                    '& .MuiPaper-root': {
                      minWidth: 400,
                      minHeight: 300,
                    },
                  },
                },
              }}
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
          <Button
            title="회원가입"
            onClick={handleSignUpClick}
            disabled={!isFormValid}
            type={'filled'}
            color={'primary'}
          />
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
