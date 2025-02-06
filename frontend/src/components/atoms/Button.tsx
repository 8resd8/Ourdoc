import classes from './Button.module.css';

interface PropsType {
  title: string;
  onClick?: () => void;
}

const Button = ({ title, onClick }: PropsType) => {
  return (
    <button
      className={`${classes.btn} text-gray-0 bg-primary-500`}
      onClick={onClick}
    >
      {title}
    </button>
  );
};

export default Button;
