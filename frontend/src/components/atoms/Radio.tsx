import classes from './Radio.module.css';

interface PropsType {
  placeholder: string;
  isSelected: boolean;
  onClick: () => void;
}

const Radio = ({ placeholder, isSelected, onClick }: PropsType) => {
  return (
    <span
      onClick={onClick}
      className={`${classes.input} ${isSelected ? classes.selected : ''} text-primary-500 body-medium`}
    >
      {placeholder}
    </span>
  );
};

export default Radio;
