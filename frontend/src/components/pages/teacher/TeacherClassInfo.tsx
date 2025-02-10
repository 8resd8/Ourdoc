import Card from '../../atoms/Card';
import classes from './TeacherClassInfo.module.css';
const TeacherClassInfo = () => {
  return (
    <div className={`${classes.root}  mx-auto p-4`}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-large text-gray-800">학급 정보</h1>
        <button className="px-4 py-2 border border-primary-500 border-w body-medium rounded-lg text-primary-500">
          인증 관리
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-5  gap-[12px]">
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
      </div>

      <div className="flex justify-center items-center mt-8 space-x-2">
        <button className="px-4 py-2  rounded-lg hover:bg-gray-100">
          &lt;
        </button>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg ">
          1
        </span>
        <span className="px-4 py-2 text-gray-500 body-small  rounded-lg ">
          2
        </span>
        <span className="px-4 py-2 text-gray-500 body-small  rounded-lg hover:bg-gray-100">
          3
        </span>
        <span className="px-4 py-2 text-gray-800 body-small rounded-lg hover:bg-gray-100">
          4
        </span>
        <button className="px-4 py-2  rounded-lg hover:bg-gray-100">
          &gt;
        </button>
      </div>
    </div>
  );
};

export default TeacherClassInfo;
