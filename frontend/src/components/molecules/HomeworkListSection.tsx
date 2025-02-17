import { useNavigate } from 'react-router-dom';
import { HomeworkListTile } from '../atoms/HomeworkListTile';
import { PlusButton } from '../atoms/PlusButton';
import { Table, TableAlignType } from '../atoms/Table';
import { useEffect, useState } from 'react';
import {
  getTeacherHomeworkBooksApi,
  HomeworkContent,
} from '../../services/booksService';
import { DateFormat } from '../../utils/DateFormat';

export const HomeworkListSection = () => {
  const [studentCount, setStudentCount] = useState(0);
  const [homework, setHomework] = useState<HomeworkContent[]>([]);
  const fetchData = async () => {
    const teacherHomework = await getTeacherHomeworkBooksApi();
    setStudentCount(teacherHomework.studentCount);
    setHomework(teacherHomework.homeworks.content);
  };
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, []);
  return (
    <div className="w-[1064px] h-[277px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div className="text-gray-800 headline-medium">숙제 현황</div>
        <PlusButton
          onClick={() => {
            navigate('/teacher/book/category');
          }}
        />
      </div>
      <Table
        headers={TABLE_HEADER}
        datas={[
          homework.map((data, index) => (
            <div key={index}>
              <HomeworkListTile
                no={index + 1}
                title={data.book.title}
                author={data.book.author}
                publisher={data.book.publisher}
                publishYear={data.book.publishYear}
                date={DateFormat(data.book.createdAt, '')}
                status={data.homeworkSubmitCount}
                studentCount={studentCount}
                onClick={() => {
                  navigate(
                    `/teacher/homework/list/?homeworkId=${data.homeworkId}`
                  );
                }}
              />
            </div>
          )),
        ]}
      />
    </div>
  );
};

const TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '제목',
    width: 332,
    align: TableAlignType.left,
  },
  {
    label: '지은이',
  },
  {
    label: '출판사',
  },
  {
    label: '출판 년도',
  },
  {
    label: '제출 현황',
  },
];
