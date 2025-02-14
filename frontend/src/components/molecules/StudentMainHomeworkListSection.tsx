import { PlusButton } from '../atoms/PlusButton';
import { StudentHomeworkListTile } from '../atoms/StudentHomeworkListTile';
import { Table, TableAlignType } from '../atoms/Table';

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

export const StudentMainHomeworkListSection = () => {
  return (
    <div className="w-[1064px] h-[277px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div className="text-gray-800 headline-medium">숙제</div>
        <PlusButton onClick={() => {}} />
      </div>
      <Table
        headers={TABLE_HEADER}
        datas={[
          <StudentHomeworkListTile
            no={1}
            title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
            date="5월 1일"
            status={true}
            onClick={() => {}}
          />,
          <StudentHomeworkListTile
            no={2}
            title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
            date="5월 1일"
            status={true}
            onClick={() => {}}
          />,
          <StudentHomeworkListTile
            no={3}
            title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
            date="5월 1일"
            status={false}
            onClick={() => {}}
          />,
        ]}
      />
    </div>
  );
};
