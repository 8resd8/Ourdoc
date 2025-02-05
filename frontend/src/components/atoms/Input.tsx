import classes from './Input.module.css';

interface propsType {
  placeholder: string;
}
const Input = (props: propsType) => {
  return (
    <div>
      <input
        type="text"
        placeholder={props.placeholder}
        className={`${classes.input} body-medium border-b-gray-200`}
      />
    </div>
  );
};

export default Input;
