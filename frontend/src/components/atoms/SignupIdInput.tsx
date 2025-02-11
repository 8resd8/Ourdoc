import classes from './SignupIdInput.module.css';

interface PropsType {
  id: string;
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  disabled?: boolean;
}

const SignupInput = ({ id, placeholder, onChange, disabled }: PropsType) => {
  return (
    <input
      type="text"
      id={id}
      placeholder={placeholder}
      onChange={onChange}
      className={`${classes.input} body-medium border-b-gray-200 ${disabled ? 'text-gray-500' : 'text-gray-800'}`}
      disabled={disabled}
    />
  );
};

export default SignupInput;
