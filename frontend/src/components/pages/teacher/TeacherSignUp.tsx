import { useEffect, useState } from 'react';
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
import {
  checkIdApi,
  signupTeacherApi,
  SignupTeacherRequest,
} from '../../../services/usersService';
import { notify } from '../../commons/Toast';
import { useNavigate } from 'react-router-dom';
import { DateFormat } from '../../../utils/DateFormat';

interface signUpRequestType {
  loginId: string;
  password: string;
  name: string;
  email: string;
  phone: string;
  birth: string;
}
const TeacherSignUp = () => {
  const [gender, setGender] = useState('남');
  const handleGenderChange = (selectedGender: string) => {
    setGender(selectedGender);
    setSignUpRequest((prev) => ({ ...prev, gender: selectedGender }));
  };
  const [isFormValid, setIsFormValid] = useState(false);
  const [certificateFile, setCertificateFile] = useState<File | null>(null);
  const [signUpRequest, setSignUpRequest] = useState<signUpRequestType>({
    loginId: '',
    password: '',
    name: '',
    email: '',
    phone: '',
    birth: '',
  });

  const router = useNavigate();

  const handleSignUp = async () => {
    try {
      if (!certificateFile) {
        alert('재직증명서를 업로드해주세요.');
        return;
      }

      const requestData = {
        name: signUpRequest.name,
        loginId: signUpRequest.loginId,
        password: signUpRequest.password,
        birth: signUpRequest.birth,
        gender: gender,
        email: signUpRequest.email,
        phone: signUpRequest.phone,
        // certificateFile: certificateFile,
      };

      await signupTeacherApi(requestData, certificateFile);
      router('/pending'); // 승인대기 페이지로 이동
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
  const handleUploadConfirm = (file: File | null) => {
    if (file) {
      setCertificateFile(file);
    }
    setIsUploadModalOpen(false);
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

  const [passwordValidate, setPasswordValidate] = useState<
    'warning' | 'success' | 'danger' | ''
  >('');
  const [isIdChecked, setIsIdChecked] = useState(false);
  const [passwordCheck, setPasswordCheck] = useState('');

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

  useEffect(() => {
    const isValid =
      Object.values(signUpRequest).every((value) => value !== '') &&
      isIdChecked &&
      passwordValidate === 'success';
    setIsFormValid(isValid);
  }, [signUpRequest, isIdChecked, passwordValidate]);

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
            isIdChecked={isIdChecked}
            onCheckDuplicate={handleCheckDuplicateId}
          />
        </div>
        <div className={classes.input}>
          <InputField
            inputType="password"
            validate=""
            id="password"
            label="비밀번호"
            placeholder="비밀번호를 입력해주세요"
            onChange={(value) => handleInputChange('password', value)}
          />
        </div>

        <div className={classes.input}>
          <InputField
            inputType="password"
            validate={passwordValidate}
            id="passwordCheck"
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
            inputType="text"
            validate=""
            id="email"
            label="이메일"
            placeholder="이메일을 입력해주세요"
            onChange={(value) => handleInputChange('email', value)}
          />
        </div>
        <div className={classes.input}>
          <InputField
            inputType="text"
            validate=""
            id="phone"
            label="전화번호"
            placeholder="전화번호를 입력해주세요"
            onChange={(value) => handleInputChange('phone', value)}
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
            disabled={!isFormValid}
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
              <div>
                <span className="text-primary-500">{signUpRequest.name}</span>
                <span>님!</span>
              </div>
              <div>
                생년월일은{' '}
                <span className="text-primary-500">
                  {DateFormat(signUpRequest.birth, '')}
                </span>
                ,
              </div>
              <div>
                성별은 <span className="text-primary-500">{gender}자</span>,
              </div>
              <div>
                이메일은{' '}
                <span className="text-primary-500">{signUpRequest.email}</span>,
              </div>
              <div>
                전화번호는{' '}
                <span className="text-primary-500">{signUpRequest.phone}</span>,
              </div>
              <div>
                사용하시려는 아이디는{' '}
                <span className="text-primary-500">
                  {signUpRequest.loginId}
                </span>{' '}
                입니다.
              </div>
              <div className="mt-4 headline-small">회원가입을 진행할까요?</div>
            </div>
          }
          confirmText={'네, 진행할래요'}
          cancelText={'아니요, 다시할래요'}
        />
        <UploadModal
          type=""
          isOpen={isUploadModalOpen}
          onConfirm={handleUploadConfirm}
          onCancel={handleUploadCancel}
        />
      </div>
    </div>
  );
};

export default TeacherSignUp;
