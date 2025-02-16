import { useRecoilValue } from 'recoil';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { currentUserState } from '../../recoil';
import Layout from '../../layouts/Layout';
import { HeaderProfileButton } from './HeaderProfileButton';
import { DropdownMenu } from '../molecules/DropdownMenu';
import { signoutApi } from '../../services/usersService';
import Modal from './Modal';
const DEFAULT_PROFILE_IMAGE_PATH = '/assets/images/profile.png';

const HeaderRouter = ({
  path,
  pageName,
  imagePath,
}: {
  path: string;
  pageName: string;
  imagePath: string;
}) => {
  const location = useLocation();
  const isActive = location.pathname === path;

  return (
    <li>
      <Link
        to={path}
        className={`flex flex-col body-medium items-center pb-1 border-b-2 hover:border-primary-400 headline-medium hover:text-primary-400 hover:focus:text-primary-400 ${
          isActive
            ? 'border-primary-500 text-primary-500'
            : 'border-transparent text-gray-700'
        }`}
      >
        <img src={imagePath} className=" mb-3 w-12 h-12" />
        {pageName}
      </Link>
    </li>
  );
};

const StudentHeader = () => {
  const user = useRecoilValue(currentUserState);
  const navigate = useNavigate();
  const userName = user.name;
  const userImage =
    user.profileImagePath == '' || user.profileImagePath == null
      ? DEFAULT_PROFILE_IMAGE_PATH
      : user.profileImagePath;
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const logout = async () => {
    try {
      await signoutApi();
      navigate('/');
    } catch (error) {
      console.error('로그아웃 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <>
      <Modal
        isOpen={isModalOpen}
        onConfirm={() => {
          logout();
          setIsModalOpen(false);
        }}
        onCancel={() => {
          setIsModalOpen(false);
        }}
        title={'정말 로그아웃 할까요?'}
        body={''}
        confirmText={'네'}
        cancelText={'아니요'}
      />
      <header>
        <nav>
          <div className="h-[110px] bg-gray-0 shadow-xxsmall flex flex-wrap justify-between items-center px-[80px]">
            <Link to="/student/main">
              <img
                src="/assets/images/logo1.png"
                alt="로고"
                className="w-[140px]"
              />
            </Link>
            <div className="flex items-center order-2 static">
              <HeaderProfileButton
                onClick={toggleDropdown}
                name={userName}
                imagePath={userImage}
              />
              {isDropdownOpen && (
                <div className="absolute top-[90px] right-[80px] z-200">
                  <DropdownMenu
                    list={[
                      {
                        text: '프로필 보기',
                        onClick: () => {
                          navigate('/student/mypage');
                          setIsDropdownOpen(false);
                        },
                      },
                      {
                        text: '로그아웃',
                        onClick: () => {
                          setIsModalOpen(true);
                          setIsDropdownOpen(false);
                        },
                      },
                    ]}
                  />
                </div>
              )}
            </div>
            <div className="h-full">
              <ul className="flex flex-row h-full items-end space-x-[10px] lg:space-x-[60px] order-1">
                <HeaderRouter
                  path="/student/main"
                  pageName="메인"
                  imagePath="/assets/images/Star.png"
                />
                <HeaderRouter
                  path="/student/book/category"
                  pageName="도서"
                  imagePath="/assets/images/Books.png"
                />
                <HeaderRouter
                  path="/student/reports"
                  pageName="독서록"
                  imagePath="/assets/images/Report.png"
                />
                <HeaderRouter
                  path="/student/trophy"
                  pageName="내 성취도"
                  imagePath="/assets/images/Trophy.png"
                />
              </ul>
            </div>
          </div>
        </nav>
      </header>
      <Layout />
    </>
  );
};

export default StudentHeader;
