import classes from './Label.module.css';
interface propsType {
  label: string;
  htmlFor: string;
}

const Label = ({ label, htmlFor }: propsType) => {
  return (
    <div className={classes.label}>
      <label htmlFor={htmlFor} className={` body-small text-gray-800`}>
        {label}
      </label>
    </div>
  );
};

export default Label;
