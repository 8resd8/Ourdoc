interface ButtonTitle {
  title: string;
}
import classes from './Button.module.css';
const Button = (props: ButtonTitle) => {
  return (
    <button className={`${classes.btn} text-gray-0 bg-primary-500`}>
      {props.title}
    </button>
  );
};
export default Button;
