import { useEffect, useState } from 'react';
import {
  getAdminTeacherVerifyListApi,
  verifyAdminTeacher,
} from '../../../services/usersService';
import { Table, TableAlignType } from '../../atoms/Table';
import { AdminListTile } from '../../atoms/AdminListTile';

const Admin = () => {
  const [list, setList] = useState<any[]>([]);
  const adminList = async () => {
    try {
      const response = await getAdminTeacherVerifyListApi();
      setList(response.content);
    } catch (error) {}
  };

  const verify = async (isApproved: any, teacherId: any) => {
    try {
      const param = {
        isApproved: isApproved,
        teacherId: teacherId,
      };
      const response = await verifyAdminTeacher(param);
    } catch (error) {}
  };

  useEffect(() => {
    adminList();
  }, []);
  return (
    <div>
      <Table
        headers={TABLE_HEADER}
        datas={[
          list &&
            list.map((data: any, index: any) => (
              <div key={index}>
                <AdminListTile
                  no={index + 1}
                  teacherId={data.teacherId}
                  loginId={data.loginId}
                  name={data.name}
                  email={data.email}
                  phone={data.phone}
                  certificateImageUrl={data.certificateImageUrl}
                  onClick={verify}
                />
              </div>
            )),
        ]}
      />
    </div>
  );
};

export default Admin;
const TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '교사ID',
    width: 332,
    align: TableAlignType.left,
  },
  {
    label: '로그인ID',
  },
  {
    label: '이름',
  },
  {
    label: '이메일',
  },
  {
    label: '전화번호',
  },
  {
    label: '재직증명서',
  },
  {
    label: '승인',
  },
];
