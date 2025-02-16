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
    label: '등록일',
  },
  {
    label: '숙제 제출',
  },
];

import { useEffect, useState } from 'react';
import {
  getStudentHomeworkBooksApi,
  HomeworkItem,
  PaginatedHomeworks,
} from '../../services/booksService';
import { PlusButton } from '../atoms/PlusButton';
import { StudentHomeworkListTile } from '../atoms/StudentHomeworkListTile';
import { Table, TableAlignType } from '../atoms/Table';
import { useNavigate } from 'react-router-dom';

export const StudentMainHomeworkListSection = () => {
  const param = {
    page: 0,
    size: 3,
    title: '',
    author: '',
    publisher: '',
  };
  const [homeworkList, setHomeworkList] = useState<HomeworkItem[]>([]);
  const [paginationInfo, setPaginationInfo] = useState<Omit<
    PaginatedHomeworks,
    'content'
  > | null>(null);

  const fetchHomeworkList = async () => {
    try {
      const response = await getStudentHomeworkBooksApi(param);
      setHomeworkList(response.homeworks.content);
      const { content, ...paginationData } = response.homeworks;
      console.log('숙제 목록:', response.homeworks);

      setPaginationInfo(paginationData);
    } catch (error) {
      console.error('숙제 목록 가져오기 실패:', error);
    }
  };

  useEffect(() => {
    fetchHomeworkList();
  }, []);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}월 ${date.getDate()}일`;
  };

  return (
    <div className="w-[1064px] h-[277px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div className="text-gray-800 headline-medium">숙제</div>
        <PlusButton onClick={() => {}} />
      </div>
      <Table
        headers={TABLE_HEADER}
        datas={homeworkList.map((homework, index) => (
          <StudentHomeworkListTile
            homeworkId={homework.homeworkId}
            key={homework.homeworkId}
            no={index + 1}
            title={homework.book.title}
            author={homework.book.author}
            publisher={homework.book.publisher}
            date={formatDate(homework.createdAt)}
            status={homework.submitStatus}
          />
        ))}
      />
    </div>
  );
};
