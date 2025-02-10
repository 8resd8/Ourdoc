import classes from './Button.module.css';

interface PropsType {
  title: string;
  type: string;
  onClick?: () => void;
}

const Button = ({ type, title, onClick }: PropsType) => {
  console.log(onClick);
  const buttonStyle =
    type === 'upload'
      ? `${classes.btn} border border-secondary-500 bg-gray-0 text-secondary-500 rounded-[10px]`
      : `${classes.btn} text-gray-0 bg-primary-500`;

  return (
    <button className={buttonStyle} onClick={onClick}>
      {title}
    </button>
  );
};

export default Button;
