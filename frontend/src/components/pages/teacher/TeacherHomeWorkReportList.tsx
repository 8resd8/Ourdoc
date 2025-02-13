const TeacherHomeWorkReportList = () => {
  const bookInfo = {
    title: '어린왕자 (Le Petit Prince)',
    author: '앙투안 드 생텍쥐페리',
    publisher: '새움',
    genre: '문학',
    year: 2022,
    image: '/assets/images/bookImage.png', // 이미지 경로
  };

  const tableData = [
    {
      no: 1,
      content: '도서명도서명도서명도서명',
      number: '8번',
      studentName: '김현우',
      submitDate: '5월 1일',
      status: '완료',
    },
    {
      no: 2,
      content: '도서명도서명도서명도서명도서명도서명도서명도서명',
      number: '8번',
      studentName: '김현우',
      submitDate: '5월 1일',
      status: '완료',
    },
    {
      no: 3,
      content: '도서명도서명도서명도서명',
      number: '8번',
      studentName: '김현우',
      submitDate: '5월 1일',
      status: '미완료',
    },
    {
      no: 4,
      content: '도서명도서명도서명도서명',
      number: '8번',
      studentName: '김현우',
      submitDate: '5월 1일',
      status: '미완료',
    },
    {
      no: 5,
      content: '도서명도서명도서명도서명',
      number: '8번',
      studentName: '김현우',
      submitDate: '5월 1일',
      status: '미완료',
    },
  ];

  return (
    <div className="w-[1200px] mx-auto mt-8">
      {/* 숙제 도서 카드 */}
      <div className="flex justify-center mb-8">
        <div className="flex bg-gray-0 shadow-xxsmall rounded-lg p-6 w-[630px] h-[340px]">
          <img
            src={bookInfo.image}
            alt={bookInfo.title}
            className="w-[190px] h-[282px] object-cover rounded-md mr-6"
          />
          <div className="">
            <h2 className="headline-medium font-bold mb-4">{bookInfo.title}</h2>
            <p className="body-medium text-gray-700 mb-2">
              저자: {bookInfo.author}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              출판사: {bookInfo.publisher}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              장르: {bookInfo.genre}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              출판년도: {bookInfo.year}
            </p>
          </div>
        </div>
      </div>

      {/* 제출한 독서록 리스트 테이블 */}
      <div className="w-full flex flex-col items-center">
        {/* 버튼 섹션 */}
        <div className="flex justify-end space-x-4 mb-4 w-[868px]">
          <button className="body-medium px-4 py-2 text-gray-500 rounded-[10px]  border border-gray-500">
            승인 완료만 보기
          </button>
          <button className="body-medium px-4 py-2 text-gray-500 rounded-[10px]  border border-gray-500">
            승인 미완료만 보기
          </button>
        </div>
        {/* 테이블 */}
        <table className="w-[868px] mb-6">
          <thead>
            <tr className="">
              <th className="px-4 py-2 border-b border-gray-200">No</th>
              <th className="px-4 py-2 border-b border-gray-200">내용</th>
              <th className="px-4 py-2 border-b border-gray-200">번호</th>
              <th className="px-4 py-2 border-b border-gray-200">학생 이름</th>
              <th className="px-4 py-2 border-b border-gray-200">제출 날짜</th>
              <th className="px-4 py-2 border-b border-gray-200">승인 여부</th>
            </tr>
          </thead>
          <tbody>
            {tableData.map((row, index) => (
              <tr key={index} className={`${index % 2 === 0 ? '' : ''}`}>
                <td className="px-4 py-2 text-center">{row.no}</td>
                <td className="px-4 py-2 truncate">{row.content}</td>
                <td className="px-4 py-2 text-center">{row.number}</td>
                <td className="px-4 py-2 text-center">{row.studentName}</td>
                <td className="px-4 py-2 text-center">{row.submitDate}</td>
                <td className="px-4 py-2 text-center">
                  {row.status === '완료' ? (
                    <button className="body-small text-system-success border-system-success border w-[49px] h-[28px] rounded-[5px]">
                      완료
                    </button>
                  ) : (
                    <button className="body-small border border-system-danger text-system-danger w-[49px] h-[28px] rounded-[5px]">
                      미완료
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="flex justify-center items-center mt-8 space-x-2">
          <button className="px-4 py-2 rounded-lg hover:bg-gray-100">
            &lt;
          </button>
          <span className="px-4 py-2 text-gray-500 body-small rounded-lg">
            1
          </span>
          <span className="px-4 py-2 text-gray-500 body-small rounded-lg">
            2
          </span>
          <span className="px-4 py-2 text-gray-500 body-small rounded-lg hover:bg-gray-100">
            3
          </span>
          <span className="px-4 py-2 text-gray-800 body-small rounded-lg hover:bg-gray-100">
            4
          </span>
          <button className="px-4 py-2 rounded-lg hover:bg-gray-100">
            &gt;
          </button>
        </div>
      </div>
    </div>
  );
};

export default TeacherHomeWorkReportList;
