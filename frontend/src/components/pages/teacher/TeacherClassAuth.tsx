const TeacherClassAuth = () => {
  const data = [
    {
      no: 1,
      number: '8번',
      name: '김미소',
      id: 'usertest',
      birthDate: '1999.01.10',
      approved: false,
    },
    {
      no: 2,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 3,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 4,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 5,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 6,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 7,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 8,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 9,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
    {
      no: 10,
      number: '1번',
      name: '김미소',
      id: '2024',
      birthDate: '1999.01.10',
      approved: true,
    },
  ];

  return (
    <div className="w-[1056px] m-auto p-24">
      <div className="flex justify-between items-center mb-4">
        <h2 className="headline-large">학급 인증 관리</h2>
        <div className="flex space-x-2">
          <button className="w-[123px] h-[50px] border border-secondary-500 text-secondary-500 body-medium rounded-[10px]">
            신규 가입 초대
          </button>
          <button className="w-[123px] h-[50px] border border-primary-500 text-primary-500 body-medium rounded-[10px]">
            소속 변경 초대
          </button>
        </div>
      </div>
      <table className="w-full border-collapse">
        <thead>
          <tr>
            <th className=" body-medium border-gray-200 px-4 py-2">No</th>
            <th className=" body-medium border-gray-200 px-4 py-2">번호</th>
            <th className=" body-medium border-gray-200 px-4 py-2">
              학생 이름
            </th>
            <th className=" body-medium border-gray-200 px-4 py-2">아이디</th>
            <th className=" body-medium border-gray-200 px-4 py-2">생년월일</th>
            <th className=" body-medium border-gray-200 px-4 py-2">
              승인 결과
            </th>
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => (
            <tr key={index} className="text-center">
              <td className=" border-gray-200 px-4 py-2">{row.no}</td>
              <td className=" border-gray-200 px-4 py-2">{row.number}</td>
              <td className=" border-gray-200 px-4 py-2">{row.name}</td>
              <td className=" border-gray-200 px-4 py-2">{row.id}</td>
              <td className=" border-gray-200 px-4 py-2">{row.birthDate}</td>
              <td className=" border-gray-200 px-4 py-2">
                <div className="flex space-x-2 justify-center">
                  <button className="text-system-success border border-system-success px-3 py-1 rounded body-small cursor-pointer">
                    승인
                  </button>
                  <button className="text-system-danger border border-system-danger px-3 py-1 rounded body-small cursor-pointer">
                    거절
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

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

export default TeacherClassAuth;
