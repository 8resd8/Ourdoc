import classes from './Button.module.css';

interface PropsType {
  title: string;
  type: 'filled' | 'outlined';
  color: 'primary' | 'secondary';
  onClick?: () => void;
  disabled?: boolean;
  flexible?: boolean;
}

const Button = ({
  type,
  title,
  color,
  onClick,
  disabled,
  flexible,
}: PropsType) => {
  const baseStyle = flexible
    ? `inline-flex px-4 py-3 justify-center items-center rounded-[10px] cursor-pointer hover:brightness-80`
    : `${classes.btn} rounded-[10px] cursor-pointer hover:brightness-80`;
  const filledStyle =
    color === 'primary'
      ? 'bg-primary-500 text-gray-0'
      : 'bg-secondary-500 text-gray-0';
  const outlinedStyle =
    color === 'primary'
      ? 'border border-primary-500 text-primary-500'
      : 'border border-secondary-500 text-secondary-500';
  const disabledStyle = 'bg-gray-500 text-gray-0';

  const buttonStyle = `${baseStyle} ${disabled ? disabledStyle : type === 'filled' ? filledStyle : outlinedStyle}`;

  return (
    <button className={buttonStyle} onClick={onClick} disabled={disabled}>
      {title}
    </button>
  );
};

export default Button;
