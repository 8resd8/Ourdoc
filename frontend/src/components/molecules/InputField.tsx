import Input from '../atoms/Input';
import Label from '../atoms/Label';

interface PropsType {
  id: string;
  label: string;
  placeholder: string;
  onChange?: (value: string) => void;
}

const InputField = ({ id, label, placeholder, onChange }: PropsType) => {
  return (
    <div>
      <Label label={label} htmlFor={id} />
      <Input
        id={id}
        placeholder={placeholder}
        onChange={(e) => onChange?.(e.target.value)}
      />
    </div>
  );
};

export default InputField;
