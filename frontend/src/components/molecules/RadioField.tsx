import Label from '../atoms/Label';
import Radio from '../atoms/Radio';
import classes from './RadioField.module.css';
interface PropsType {
  id: string;
  label: string;
  selectedGender: string;
  onGenderChange: (gender: string) => void;
}

const RadioField = ({
  id,
  label,
  selectedGender,
  onGenderChange,
}: PropsType) => {
  return (
    <div className={classes.radioGroup}>
      <Label label={label} htmlFor={id} />
      <div className={classes.radioContainer}>
        <Radio
          placeholder="남자"
          isSelected={selectedGender === '남'}
          onClick={() => onGenderChange('남')}
        />
        <Radio
          placeholder="여자"
          isSelected={selectedGender === '여'}
          onClick={() => onGenderChange('여')}
        />
      </div>
    </div>
  );
};

export default RadioField;
