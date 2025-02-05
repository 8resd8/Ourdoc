interface propsType {
  label: string;
}

const Label = (props: propsType) => {
  return (
    <div>
      <label htmlFor="input" className="body-small">
        {props.label}
      </label>
    </div>
  );
};

export default Label;
