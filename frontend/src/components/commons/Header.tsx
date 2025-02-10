interface HeaderProps {
  userName: string;
}

const Header: React.FC<HeaderProps> = ({ userName }) => {
  return (
    <header className="bg-white shadow-md">
      <div className="container mx-auto flex justify-between items-center py-4 px-6">
        <div className="flex items-center space-x-4">
          <img src="/assets/images/logo1.png" alt="로고" className="h-[70px]" />
        </div>

        {/* 네비게이션 */}
        <nav className="flex space-x-6">
          <a
            href="/main"
            className="text-sm headline-medium text-primary-500 hover:underline"
          >
            메인
          </a>
          <a href="/study" className="text-sm  headline-medium text-gray-800 ">
            학급
          </a>
          <a href="/books" className="text-sm  headline-medium text-gray-800 ">
            도서
          </a>
          <a
            href="/records"
            className="text-sm  headline-medium text-gray-800 "
          >
            독서록
          </a>
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
          <span className="text-sm text-gray-800">{userName} 님</span>
        </div>
      </div>
    </header>
  );
};

export default Header;
