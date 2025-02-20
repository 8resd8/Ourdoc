import { useLocation, useNavigate } from 'react-router-dom';
import { DateFormat } from '../../../utils/DateFormat';
import { useEffect, useState } from 'react';
import Modal from '../../commons/Modal';
import {
  AwardDetail,
  getTeacherStudentAwardsApi,
} from '../../../services/awardsService';
import { getRecoil } from 'recoil-nexus';
import { currentUserState } from '../../../recoil';
import { TrophyAwardItem } from '../../molecules/TrophyAwardSection';

const TeacherStudentInfo = () => {
  const navigate = useNavigate();
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
  const date = DateFormat(certificateTime, '');
  const [modal, setModal] = useState(false);
  const [modalText, setModalText] = useState('');
  const [awards, setawards] = useState<AwardDetail[]>([]);
  const classId = getRecoil(currentUserState).classId!;

  useEffect(() => {
    const fetchData = async () => {
      const response = await getTeacherStudentAwardsApi({
        studentLoginId: loginId,
        classId: classId,
      });
      setawards(response);
    };

    fetchData();
  }, []);

  const navigateToStudentReport = () => {
    navigate('/teacher/reports', { state: { name, studentNumber } });
  };

  return (
    <>
      <Modal
        isOpen={modal}
        title={'요청 완료'}
        body={<div className="body-medium">{modalText}</div>}
        confirmText={'확인'}
        cancelText={'닫기'}
        onConfirm={function (): void {
          setModal(false);
        }}
        onCancel={function (): void {
          setModal(false);
        }}
      />
      <div className="flex  items-center flex-col  p-4 mt-[80px] ">
        <div className="flex w-[1100px] justify-items-center">
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
              <div className="mb-3 border-b border-gray-700">
                <label className="block text-gray-600 body-small">아이디</label>
                <div className="mt-[8px] h-[32px] body-medium">{loginId}</div>
              </div>
              <div className="mb-3 border-b border-gray-700">
                <label className="block text-gray-600 body-small">
                  생년월일
                </label>
                <div className="mt-[8px] h-[32px] body-medium  border-b-gray-200">
                  {birth}
                </div>
              </div>
              <div className="mb-7 border-b border-gray-700">
                <label className="block text-gray-600 body-small">가입일</label>
                <div className="mt-[8px] h-[32px] body-medium ">{date}</div>
              </div>
              <button
                onClick={() => {
                  setModalText(
                    '비밀번호 초기화 요청이 완료되었습니다. 관리자의 승인 후 진행됩니다.'
                  );
                  setModal(true);
                }}
                className="w-[414px] h-[56px] border border-gray-500 body-medium text-gray-500 py-2 rounded-lg mb-4 hover:bg-gray-200 cursor-pointer"
              >
                비밀번호 초기화
              </button>
              <button
                onClick={() => {
                  setModalText(
                    '방출하기 요청이 완료되었습니다. 관리자의 승인 후 진행됩니다.'
                  );
                  setModal(true);
                }}
                className="w-full h-[56px] text-system-danger border py-2  border-system-danger rounded-lg hover:bg-primary-200  cursor-pointer"
              >
                방출하기
              </button>
            </div>
          </div>

          <div>
            <div className="w-[630px] h-[518px] bg-gray-0 rounded-xl shadow-xsmall p-6">
              <h2 className="headline-medium font-bold mb-3">상장</h2>
              <div className="grid grid-cols-4 gap-3">
                {awards.length == 0 ? (
                  <div className="text-gray-500 ">상장이 없습니다.</div>
                ) : (
                  awards.map((award, index) => (
                    <TrophyAwardItem key={index} award={award} />
                  ))
                )}
              </div>
            </div>
            <div>
              <div className="w-full mt-6 flex-col ">
                <button
                  onClick={() => {
                    navigateToStudentReport();
                  }}
                  className="w-full h-[56px] bg-primary-500 text-gray-0 py-3 rounded-lg hover:bg-primary-400 cursor-pointer"
                >
                  독서 기록 조회하기
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default TeacherStudentInfo;
