import { useState } from 'react';
import classes from './TeacherSignUp.module.css';
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
import UploadModal from '../../commons/UploadModal';
import { signupTeacherApi } from '../../../services/usersService';

interface signInRequestType {
  loginId: string;
  password: string;
  name: string;
  email: string;
  phone: string;
  birth: string;
}
const TeacherSignUp = () => {
  const [gender, setGender] = useState('남자');
  const handleGenderChange = (selectedGender: string) => {
    setGender(selectedGender);
    setSignInRequest((prev) => ({ ...prev, gender: selectedGender }));
  };
  console.log(gender);
  const [signInRequest, setSignInRequest] = useState<signInRequestType>({
    loginId: '',
    password: '',
    name: '',
    email: '',
    phone: '',
    birth: '',
  });
  console.log(signInRequest);

  const handleInputChange = (id: string, value: string) => {
    setSignInRequest((prev) => ({ ...prev, [id]: value }));
  };

  const handleSignUp = async () => {
    try {
      const certificateFile = new File(['temp'], 'certificate.png', {
        type: 'image/png',
      });

      // JSON 데이터를 문자열로 변환
      const teacherData = {
        name: 'zz',
        loginId: '1',
        password: '1',
        birth: '1',
        gender: '1',
        email: '1',
        phone: '1',
      };

      const formData = new FormData();
      formData.append(
        'request',
        new Blob([JSON.stringify(teacherData)], { type: 'application/json' })
      );
      formData.append('certificateFile', certificateFile);

      const response = await signupTeacherApi(formData);

      console.log('회원가입 성공:', response);
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);

  const handleSignUpClick = () => {
    setIsModalOpen(true);
  };
  const handleUploadClick = () => {
    setIsUploadModalOpen(true);
  };

  const handleSignUpConfirm = async () => {
    try {
      await handleSignUp();
      setIsModalOpen(false);
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };
  const handleUploadConfirm = async () => {
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

  const handleUploadCancel = () => {
    setIsUploadModalOpen(false);
  };

  const [birthDate, setBirthDate] = useState<dayjs.Dayjs | null>(null);

  const handleBirthDateChange = (newValue: dayjs.Dayjs | null) => {
    setBirthDate(newValue);
    handleInputChange('birth', newValue?.format('YYYY-MM-DD') || '');
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
        <div>
          <div className="mb-2">
            <span className="headline-medium text-primary-500 ">
              우리들의 독서기록
            </span>
            <span className="headline-medium text-gray-800">
              에 오신 것을 환영해요!
            </span>
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
            isIdChecked={false}
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
        <div
          className={`${classes.validate} mt-1 w-96 text-gray-800 caption-small`}
        >
          Success
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
            id="email"
            label="이메일"
            placeholder="이메일을 입력해주세요"
            onChange={(value) => handleInputChange('email', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            validate=""
            id="phone"
            label="전화번호"
            placeholder="전화번호를 입력해주세요"
            onChange={(value) => handleInputChange('email', value)}
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
            title="재직증명서 첨부하기"
            type="outlined"
            color="secondary"
            onClick={handleUploadClick}
          />
        </div>
        <div className={classes.btn}>
          <Button
            type="filled"
            color="primary"
            title="회원가입"
            onClick={handleSignUpClick}
          />
        </div>
        <Modal
          isOpen={isModalOpen}
          onConfirm={handleSignUpConfirm}
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
              <div className="mt-4 headline-small">회원가입을 진행할까요?</div>
            </div>
          }
          confirmText={'네, 진행할래요'}
          cancelText={'아니요, 다시할래요'}
        />
        <UploadModal
          isOpen={isUploadModalOpen}
          onConfirm={handleUploadConfirm}
          onCancel={handleUploadCancel}
        />
      </div>
    </div>
  );
};

export default TeacherSignUp;
