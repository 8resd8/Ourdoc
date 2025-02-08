import Input from '../atoms/Input';
import Label from '../atoms/Label';
import classes from './InputField.module.css';

interface PropsType {
  id: string;
  label: string;
  placeholder: string;
  validate: string;
  onChange?: (value: string) => void;
}

const InputField = ({
  validate,
  id,
  label,
  placeholder,
  onChange,
}: PropsType) => {
  return (
    <div>
      <Label label={label} htmlFor={id} />
      <Input
        id={id}
        placeholder={placeholder}
        onChange={(e) => onChange?.(e.target.value)}
      />
      {validate === 'warning' && (
        <div
          className={`${classes.validate} mt-[8px] w-96 text-gray-800 caption-small`}
        >
          <span>
            <img src="/assets/images/Success.svg" />
          </span>
          <span>Success</span>
        </div>
      )}
    </div>
  );
};

export default InputField;
