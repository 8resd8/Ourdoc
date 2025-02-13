import Label from '../atoms/Label';
import SignupInput from '../atoms/SignupIdInput';

interface PropsType {
  id: string;
  label: string;
  placeholder: string;
  onChange?: (value: string) => void;
  onCheckDuplicate: () => void;
  isIdChecked: boolean;
}

const SignupIdField = ({
  id,
  label,
  placeholder,
  onChange,
  onCheckDuplicate,
  isIdChecked,
}: PropsType) => {
  return (
    <div>
      <Label label={label} htmlFor={id} />
      <SignupInput
        id={id}
        placeholder={placeholder}
        onChange={(e) => onChange?.(e.target.value)}
        disabled={isIdChecked}
      />
      <button
        className={`h-10 w-20 rounded-xl body-small cursor-pointer ${
          isIdChecked ? 'bg-gray-500 text-gray-0' : 'bg-primary-500 text-gray-0'
        }`}
        onClick={onCheckDuplicate}
        disabled={isIdChecked}
      >
        {isIdChecked ? '확인완료' : '중복확인'}
      </button>
    </div>
  );
};

export default SignupIdField;
