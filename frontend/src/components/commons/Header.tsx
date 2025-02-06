import classes from './Header.module.css';
const Header = () => {
  return (
    <div>
      <div className={`${classes.test} display-xlarge`}>헤더</div>
      {/* <div className="report-font display-xlarge">
        저는 오늘 어린왕자를 읽었어요
      </div> */}
    </div>
  );
};
export default Header;
