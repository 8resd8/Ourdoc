import classes from './SignupIdInput.module.css';

interface PropsType {
  id: string;
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const SignupInput = ({ id, placeholder, onChange }: PropsType) => {
  return (
    <input
      type="text"
      id={id}
      placeholder={placeholder}
      onChange={onChange}
      className={`${classes.input} body-medium border-b-gray-200`}
    />
  );
};

export default SignupInput;
