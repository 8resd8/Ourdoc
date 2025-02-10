import classes from './Pending.module.css';
const Pending = () => {
  return (
    <div>
      <div className={classes.root}>
        <div className={`${classes.base}`}>
          <div>
            <img
              src="/assets/images/logo1.png"
              alt="logo"
              className={`${classes.logo_img}`}
            />
          </div>
          <div className="text-center">
            <div className="headline-medium text-gray-800">승인 중입니다.</div>
            <div className="headline-medium text-gray-800 mt-2">
              관리자 혹은 담당 선생님께 문의해주세요.
            </div>
            <div className="flex justify-center mt-4">
              <img
                src="/assets/images/pending.png"
                alt="logo"
                // className={`${classes.logo_img}`}
              />
            </div>
            <div className="mt-[50px]">
              <button className="w-[414px] h-[48px] rounded-[10px] body-medium  bg-primary-500 text-gray-0 cursor-pointer">
                확인
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Pending;
