import Button from '../../atoms/Button';
import Input from '../../atoms/Input';
import Label from '../../atoms/Label';
import InputField from '../../molecules/InputField';
import classes from './SignIn.module.css';
const SignIn = () => {
  return (
    <div className={`${classes.base}`}>
      <div>
        <img
          className={`${classes.logo_img}`}
          alt="logoImage"
          src="/assets/images/logo.png"
        />
      </div>
      <div>
        <span>학생</span>
        <span>교사</span>
      </div>
      <div>
        <InputField label="아이디" placeholder="아이디를 입력해주세요" />
      </div>
      <div>
        <InputField label="비밀번호" placeholder="비밀번호를 입력해주세요" />
      </div>
      <Button title="로그인" />
    </div>
  );
};

export default SignIn;
