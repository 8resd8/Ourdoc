import Label from '../atoms/Label';
import SignupInput from '../atoms/SignupIdInput';

interface PropsType {
  id: string;
  label: string;
  placeholder: string;
  onChange?: (value: string) => void;
}

const SignupIdField = ({ id, label, placeholder, onChange }: PropsType) => {
  return (
    <div>
      <Label label={label} htmlFor={id} />
      <SignupInput
        id={id}
        placeholder={placeholder}
        onChange={(e) => onChange?.(e.target.value)}
      />
      <button className="bg-primary-500 h-10 w-20 rounded-xl body-small text-gray-0">
        중복확인
      </button>
    </div>
  );
};

export default SignupIdField;
