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
      className={`${classes.input} ${isSelected ? 'text-primary-500 body-medium border-primary-500 border' : 'border-gray-200 border body-medium text-gray-200'} `}
    >
      {placeholder}
    </span>
  );
};

export default Radio;
