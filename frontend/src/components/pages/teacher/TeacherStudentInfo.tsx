import { useLocation } from 'react-router-dom';
import { DateFormat } from '../../../utils/DateFormat';

const TeacherStudentInfo = () => {
  const location = useLocation();
  const {
    loginId,
    studentNumber,
    name,
    birth,
    gender,
    profileImagePath,
    certificateTime,
  } = location.state || {};
  const studentGender = gender === '남' ? '♂' : '♀';
  const date = DateFormat(certificateTime);
  return (
    <div className="flex  items-center flex-col  p-4 mt-[80px] ">
      <div className="flex w-[1100px] justify-items-center">
        {/* 좌측 학생 정보 */}
        <div className="w-[414px] mt-11 bg-gray-0 rounded-xl  mr-4">
          <div className="flex flex-col items-center">
            <img
              src={profileImagePath || '/assets/images/tmpProfile.png'}
              alt="학생 아바타"
              className="w-32 h-32 rounded-full border border-gray-300 mb-4"
            />
            <h1 className="headline-medium font-bold">
              {studentNumber}번 {name} {studentGender}
            </h1>
          </div>
          <div className="mt-6">
            <div className="mb-2 border-b border-gray-700">
              <label className="block text-gray-600 body-small">아이디</label>
              <div className="mt-[8px] h-[40px] body-medium">{loginId}</div>
            </div>
            <div className="mb-2 border-b border-gray-700">
              <label className="block text-gray-600 body-small">생년월일</label>
              <div className="mt-[8px] h-[40px] body-medium  border-b-gray-200">
                {birth}
              </div>
            </div>
            <div className="mb-4 border-b border-gray-700">
              <label className="block text-gray-600 body-small">가입일</label>
              <div className="mt-[8px] h-[40px] body-medium ">{date}</div>
            </div>
            <button className="w-[414px] h-[56px] border border-gray-500 body-medium text-gray-500 py-2 rounded-lg mb-2 hover:bg-gray-200 cursor-pointer">
              비밀번호 초기화
            </button>
            <button className="w-full h-[56px] text-system-danger border py-2  border-system-danger rounded-lg hover:bg-primary-200  cursor-pointer">
              방출하기
            </button>
          </div>
        </div>

        {/* 우측 상장 정보 */}
        <div>
          <div className="w-[630px] h-[518px] bg-gray-0 rounded-xl shadow-xsmall p-6">
            <h2 className="headline-medium font-bold mb-4">상장</h2>
            <div className="grid grid-cols-3 gap-4">
              {[...Array(3)].map((_, index) => (
                <div
                  key={index}
                  className="flex flex-col items-center justify-center p-4"
                >
                  <img
                    src="/assets/images/Medal.png"
                    alt="상장"
                    className="w-12 h-12 mb-2"
                  />
                  <p className="body-medium font-medium text-gray-700">
                    착한 어린이 상
                  </p>
                </div>
              ))}
            </div>
          </div>
          <div>
            <div className="w-full mt-6 flex-col ">
              <button className="w-full h-[56px] bg-primary-500 text-gray-0 py-3 rounded-lg hover:bg-primary-400 cursor-pointer">
                독서 기록 조회하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeacherStudentInfo;
