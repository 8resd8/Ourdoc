import React from 'react';
import { useRecoilValue } from 'recoil';
import { Link } from 'react-router-dom';
import { currentUserState } from '../../recoil';
import Layout from '../../layouts/Layout';

const StudentHeader: React.FC = () => {
  const userName = useRecoilValue(currentUserState)?.name;

  return (
    <>
      <header className="bg-gray-0 shadow-xxsmall">
        <div className="container mx-auto flex justify-between items-center py-4 px-6">
          <div className="flex items-center space-x-4">
            <img
              src="/assets/images/logo1.png"
              alt="로고"
              className="h-[70px]"
            />
          </div>

          {/* 네비게이션 */}
          <nav className="flex space-x-6">
            <Link
              to="/student/main"
              className="body-medium headline-medium text-primary-500 hover:underline"
            >
              메인
            </Link>
            <Link
              to="/student/study"
              className="body-medium  headline-medium text-gray-800 "
            >
              도서
            </Link>
            <Link
              to="/student/books"
              className="body-medium  headline-medium text-gray-800 "
            >
              독서록
            </Link>
            <Link
              to="/student/records"
              className="body-medium  headline-medium text-gray-800 "
            >
              내 성취도
            </Link>
          </nav>

          {/* 사용자 정보 */}
          <div className="flex items-center space-x-4">
            <button className="w-8 h-8 rounded-full flex items-center justify-center hover:bg-gray-300">
              <img
                src="/assets/images/profile.png" // 사용자 아이콘 경로
                alt="사용자 아이콘"
                className="w-6 h-6"
              />
            </button>
            <span className="body-medium text-gray-800">{userName} 님</span>
          </div>
        </div>
      </header>
      <Layout />
    </>
  );
};

export default StudentHeader;
