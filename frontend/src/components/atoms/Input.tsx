import classes from './Input.module.css';

interface PropsType {
  id: string;
  inputType: 'text' | 'password';
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input = ({ id, placeholder, onChange, inputType }: PropsType) => {
  return (
    <input
      type={inputType}
      id={id}
      placeholder={placeholder}
      onChange={onChange}
      className={`${classes.input} body-medium border-b-gray-200`}
    />
  );
};

export default Input;
