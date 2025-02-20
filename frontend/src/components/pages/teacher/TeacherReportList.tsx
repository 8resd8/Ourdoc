import { useEffect, useState } from 'react';
import {
  searchClass,
  searchStudentByClass,
} from '../../../services/teachersService';
import { getTeacherBookReportsList } from '../../../services/bookReportsService';
import { Table, TableAlignType } from '../../atoms/Table';
import { PaginationButton } from '../../atoms/PagenationButton';
import { TeacherAllReportListTile } from '../../commons/TeacherAllReportListTile';
import { useNavigate } from 'react-router-dom';

const TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '도서명',
    width: 240,
    align: TableAlignType.left,
  },
  {
    label: '번호',
    width: 60,
  },
  {
    label: '학생 이름',
  },
  {
    label: '제출 날짜',
  },
  {
    label: '승인 여부',
  },
];
const TeacherReportList = () => {
  const [selectedYear, setSelectedYear] = useState<string>('');
  const [selectedClass, setSelectedClass] = useState<{
    classId: number;
    schoolName: string;
    year: string;
    grade: number;
    classNumber: number;
  } | null>(null);
  const [selectedStudent, setSelectedStudent] = useState<{
    studentNumber: number;
    studentName: string;
  } | null>(null);
  const [students, setStudents] = useState<any[]>([]);

  const [classes, setClasses] = useState<any[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [reports, setReports] = useState<any[]>([]);

  const fetchClass = async () => {
    const response = await searchClass();
    setClasses(response.teachersRoom);
  };

  const getStudentByClass = async (classId: number) => {
    const response = await searchStudentByClass(classId);
    setStudents(response.teachersClassStudents);
    setReports([]);
    setSelectedStudent(null);
  };

  const param = {
    page: 0,
    size: 10,
    year: '',
    studentNumber: '',
    studentName: '',
    schoolName: '',
  };
  const getReportsByStudent = async (page = 0, param: any) => {
    try {
      const response = await getTeacherBookReportsList(param);
      setTotalPages(response.bookReports.totalPages);
      setCurrentPage(page);
      setReports(response.bookReports.content);
    } catch (error) {}
  };

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      getReportsByStudent(pageNumber, {
        year: selectedYear,
        studentNumber: selectedStudent?.studentNumber,
        studentName: selectedStudent?.studentName,
        schoolName: selectedClass?.schoolName,
      });
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}월 ${date.getDate()}일`;
  };

  useEffect(() => {
    fetchClass();
  }, []);
  const navigate = useNavigate();
  const goReportDetail = (
    id: number,
    studentNumber: string,
    studentName: string
  ) => {
    navigate(
      `/teacher/report/detail/${id}/?studentNumber=${studentNumber}&name=${studentName}`
    );
  };

  return (
    <div className="flex flex-row ">
      <div className="flex ">
        <div className="w-[226px] py-4 items-center left-0 top-[110px] border-r-2 border-gray-200 overflow-hidden">
          <div className="ml-4 top-[136px]  text-gray-800 caption-medium">
            반별 조회
          </div>
          <div className="h-[100px] left-0 top-[164px]  flex-col justify-start items-start gap-4 inline-flex">
            {Object.entries(classes).map(([year, classes]) => (
              <div key={year} className="h-[136px] relative">
                <div className="w-[226px] h-10 px-4 py-2 left-0 top-0 flex-col justify-start items-start gap-2 inline-flex">
                  <div className="self-stretch grow shrink basis-0 justify-start items-center gap-2 inline-flex">
                    <div data-svg-wrapper className="relative cursor-pointer">
                      <svg
                        onClick={() => {
                          setSelectedYear(year);
                          selectedYear == year ? setSelectedYear('') : '';
                        }}
                        width="16"
                        height="16"
                        viewBox="0 0 16 16"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          d={
                            selectedYear === year
                              ? 'M4 6L8 10L12 6'
                              : 'M12 10L8 6L4 10'
                          }
                          stroke="black"
                          strokeWidth="2"
                          strokeLinecap="round"
                          strokeLinejoin="round"
                        />
                      </svg>
                    </div>
                    <div
                      className="text-gray-800 body-medium font-bold cursor-pointer"
                      onClick={() => {
                        setSelectedYear(year);
                        selectedYear == year ? setSelectedYear('') : '';
                      }}
                    >
                      {year}년도
                    </div>
                  </div>
                </div>
                {selectedYear === year && (
                  <div>
                    {classes.map((classInfo: any) => (
                      <div
                        key={classInfo.classId}
                        className={`cursor-pointer w-[226px] px-10 py-2 left-0 ${
                          selectedClass?.classId === classInfo.classId
                            ? 'bg-primary-50'
                            : 'bg-gray-0'
                        } flex-col justify-start items-start gap-2 inline-flex`}
                        onClick={() => {
                          setSelectedClass(classInfo);
                          getStudentByClass(classInfo.classId);
                        }}
                      >
                        <div
                          className={`${
                            selectedClass?.classId === classInfo.classId
                              ? 'text-primary-500 font-bold'
                              : 'text-gray-800'
                          } body-medium`}
                        >
                          <div>{classInfo.schoolName}</div>
                          <span>
                            {classInfo.grade}학년 {classInfo.classNumber}반
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>

        <div className="h-[914px] left-[227px] top-[110px] border-r border-gray-200">
          <div className="h-[850px] w-[200px]  left-0 top-[50px] flex-col justify-start items-start gap-2 inline-flex overflow-y-auto">
            <div className="left-[16px] py-4 px-5 text-center text-gray-800 caption-medium">
              학생별 보기
            </div>
            {students.map((student: any) => (
              <div
                key={student.studentNumber}
                className={`h-10 py-2 ${
                  selectedStudent &&
                  selectedStudent.studentNumber === student.studentNumber
                    ? 'bg-primary-50'
                    : 'bg-gray-0'
                } justify-start items-center gap-2 inline-flex cursor-pointer`}
                onClick={() => {
                  setSelectedStudent(student);
                  getReportsByStudent(0, {
                    year: selectedYear,
                    studentNumber: student.studentNumber,
                    studentName: student.studentName,
                    schoolName: selectedClass ? selectedClass.schoolName : '',
                  });
                }}
              >
                <div
                  className={`text-center w-[60px] ${
                    selectedStudent?.studentNumber === student.studentNumber
                      ? 'text-primary-500'
                      : 'text-gray-800'
                  } body-small`}
                >
                  {student.studentNumber}번
                </div>
                <div
                  className={`w-[150px] ${
                    selectedStudent?.studentNumber === student.studentNumber
                      ? 'text-primary-500'
                      : 'text-gray-800'
                  } body-medium`}
                >
                  {student.studentName}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* 도서 보고서 섹션 */}
      <div className="mt-20 ml-4 flex-col justify-start items-center gap-3 inline-flex">
        <Table
          headers={TABLE_HEADER}
          datas={reports.map((item, index) => {
            return (
              <TeacherAllReportListTile
                title={item.bookTitle}
                studentNumber={item.studentNumber}
                name={item.studentName}
                isApproved={item.bookReportApproveStatus}
                submitDate={formatDate(item.createdAt)}
                no={currentPage * 10 + index + 1}
                onClick={() =>
                  goReportDetail(
                    item.bookReportId,
                    item.studentNumber,
                    item.studentName
                  )
                }
              />
            );
          })}
        />
        <PaginationButton
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      </div>
    </div>
  );
};

export default TeacherReportList;

// import React, { useState } from 'react';
// import './TeacherReportList.css'; // 별도의 CSS 파일을 만들어 스타일을 정의합니다

// const TeacherReportList = () => {
//   const [selectedYear, setSelectedYear] = useState(null);
//   const [selectedClass, setSelectedClass] = useState(null);
//   const [selectedStudent, setSelectedStudent] = useState(null);

//   const yearData = [
//     {
//       year: '2025년도',
//       classes: [
//         { name: '성룡초 1학년 2반', students: ['김보민', '김융융', '김현우'] },
//         { name: '성천초 1학년 7반', students: ['이철수', '박영희', '최지민'] },
//       ]
//     },
//     {
//       year: '2024년도',
//       classes: [
//         { name: '성룡초 1학년 2반', students: ['강동원', '송혜교', '이병헌'] },
//       ]
//     },
//     {
//       year: '2023년도',
//       classes: [
//         { name: '성룡초 1학년 2반', students: ['정우성', '손예진', '공유'] },
//       ]
//     },
//   ];

//   const bookReports = [
//     { bookTitle: '별이 빛나는 밤에', studentNumber: '8번', studentName: '김현우', submitDate: '5월 1일', approvalStatus: '완료' },
//     { bookTitle: '해리포터', studentNumber: '3번', studentName: '이철수', submitDate: '5월 2일', approvalStatus: '미완료' },
//   ];

//   return (
//     <div className="teacher-report-list">
//       <div className="sidebar">
//         <div className="search-section">
//           <input type="text" placeholder="도서를 검색해주세요" />
//           <button>검색</button>
//         </div>
//         <div className="year-list">
//           {yearData.map((yearItem) => (
//             <div key={yearItem.year} className="year-item">
//               <h3 onClick={() => setSelectedYear(yearItem.year)}>{yearItem.year}</h3>
//               {selectedYear === yearItem.year && (
//                 <ul className="class-list">
//                   {yearItem.classes.map((classItem) => (
//                     <li
//                       key={classItem.name}
//                       onClick={() => setSelectedClass(classItem)}
//                       className={selectedClass === classItem ? 'selected' : ''}
//                     >
//                       {classItem.name}
//                     </li>
//                   ))}
//                 </ul>
//               )}
//             </div>
//           ))}
//         </div>
//       </div>

//       <div className="student-list">
//         <h2>학생별 보기</h2>
//         {selectedClass && (
//           <ul>
//             {selectedClass.students.map((student) => (
//               <li
//                 key={student}
//                 onClick={() => setSelectedStudent(student)}
//                 className={selectedStudent === student ? 'selected' : ''}
//               >
//                 {student}
//               </li>
//             ))}
//           </ul>
//         )}
//       </div>

//       <div className="book-report-list">
//         <h2>도서 보고서</h2>
//         {selectedStudent && (
//           <table>
//             <thead>
//               <tr>
//                 <th>도서명</th>
//                 <th>번호</th>
//                 <th>학생 이름</th>
//                 <th>제출 날짜</th>
//                 <th>승인 여부</th>
//               </tr>
//             </thead>
//             <tbody>
//               {bookReports.filter(report => report.studentName === selectedStudent).map((report, index) => (
//                 <tr key={index}>
//                   <td>{report.bookTitle}</td>
//                   <td>{report.studentNumber}</td>
//                   <td>{report.studentName}</td>
//                   <td>{report.submitDate}</td>
//                   <td className={`status ${report.approvalStatus === '완료' ? 'completed' : 'pending'}`}>
//                     {report.approvalStatus}
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         )}
//       </div>
//     </div>
//   );
// };

// export default TeacherReportList;
