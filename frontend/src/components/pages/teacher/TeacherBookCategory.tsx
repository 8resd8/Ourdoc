import SelectVariants from '../../commons/SelectVariants';
import classes from './TeacherBookCategory.module.css';

const TeacherBookCategory = () => {
  const data = [
    {
      no: 1,
      title: '별이 빛나는 밤에',
      author: '김미소',
      publisher: '김미소',
      year: 2024,
      readers: '10명',
    },
    {
      no: 2,
      title: '별이 빛나는 밤에',
      author: '김미소',
      publisher: '김미소',
      year: 2024,
      readers: '10명',
    },
    {
      no: 3,
      title: '별이 빛나는 밤에',
      author: '김미소',
      publisher: '김미소',
      year: 2024,
      readers: '10명',
    },
  ];

  return (
    <div className="w-[1200px] m-auto mt-8">
      {/* 검색 영역 */}

      <div className="flex items-center justify-center mb-6">
        <div className="flex justify-center space-x-4">
          <SelectVariants />
          <input
            id="search"
            type="text"
            placeholder="검색어를 입력해주세요."
            className={`${classes.input} w-[522px] rounded px-3`}
          />
        </div>
      </div>

      {/* 숙제 도서 제목 */}
      <h2 className="headline-large text-center mb-[36px] mt-[72px]">
        숙제 도서
      </h2>

      {/* 카테고리 버튼 */}
      <div className="flex justify-center space-x-4 mb-6">
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/edit.png"
            className="mb-1"
            alt="숙제 아이콘"
          />
          <span>숙제</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/class.png"
            className="mb-1"
            alt="학급 아이콘"
          />
          <span>학급</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/heart.png"
            className="mb-1"
            alt="관심 아이콘"
          />
          <span>관심</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/school.png"
            className="mb-1"
            alt="학년 아이콘"
          />
          <span>학년</span>
        </button>
      </div>

      {/* 테이블 */}
      <table className="w-full border-collapse mb-6">
        <thead>
          <tr className="border-b">
            <th className="px-4 py-2 body-medium">No</th>
            <th className="px-4 py-2 body-medium">도서명</th>
            <th className="px-4 py-2 body-medium">저자</th>
            <th className="px-4 py-2 body-medium">출판사</th>
            <th className="px-4 py-2 body-medium">출판 년도</th>
            <th className="px-4 py-2 body-medium">독서 현황</th>
            <th className="px-4 py-2 body-medium">삭제</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => (
            <tr key={index} className="text-center">
              <td className="px-4 py-2">{row.no}</td>
              <td className="px-4 py-2">{row.title}</td>
              <td className="px-4 py-2">{row.author}</td>
              <td className="px-4 py-2">{row.publisher}</td>
              <td className="px-4 py-2">{row.year}</td>
              <td className="px-4 py-2">{row.readers}</td>
              <td className="px-4 py-2">
                <button className="text-system-danger body-small px-3 py-1 border border-system-danger rounded">
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="flex justify-center items-center mt-8 space-x-2">
        <button className="px-4 py-2 rounded-lg hover:bg-gray-100">&lt;</button>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg">1</span>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg">2</span>
        <span className="px-4 py-2 text-gray-500 body-small rounded-lg hover:bg-gray-100">
          3
        </span>
        <span className="px-4 py-2 text-gray-800 body-small rounded-lg hover:bg-gray-100">
          4
        </span>
        <button className="px-4 py-2 rounded-lg hover:bg-gray-100">&gt;</button>
      </div>
    </div>
  );
};

export default TeacherBookCategory;
