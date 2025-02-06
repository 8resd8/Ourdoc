import Input from '../atoms/Input';
import Label from '../atoms/Label';

interface propsType {
  label: string;
  placeholder: string;
}

const InputField = (props: propsType) => {
  return (
    <div>
      <Label label={props.label} />
      <Input placeholder={props.placeholder} />
    </div>
  );
};

export default InputField;
