import Input from '../atoms/Input';
import Label from '../atoms/Label';
import classes from './InputField.module.css';

interface PropsType {
  id: string;
  label: string;
  placeholder: string;
  inputType: 'text' | 'password';
  validate: 'warning' | 'success' | 'danger' | '';
  onChange?: (value: string) => void;
}

const InputField = ({
  validate,
  id,
  label,
  placeholder,
  onChange,
  inputType,
}: PropsType) => {
  const getValidationMessage = () => {
    switch (validate) {
      case 'warning':
        return { message: 'Warning', icon: '/assets/images/Warning.svg' };
      case 'success':
        return { message: 'Success', icon: '/assets/images/Success.svg' };
      case 'danger':
        return { message: 'Danger', icon: '/assets/images/Danger.svg' };
      default:
        return { message: '', icon: '' };
    }
  };

  const { message, icon } = getValidationMessage();

  return (
    <div>
      <Label label={label} htmlFor={id} />
      <Input
        inputType={inputType}
        id={id}
        placeholder={placeholder}
        onChange={(e) => onChange?.(e.target.value)}
      />
      {validate && (
        <div
          className={`${classes.validate} mt-[8px] w-96 text-gray-800 caption-small`}
        >
          <span>
            <img src={icon} alt={message} />
          </span>
          <span>{message}</span>
        </div>
      )}
    </div>
  );
};

export default InputField;
