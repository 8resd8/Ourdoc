import { Route, Routes } from 'react-router-dom';
import Layout from '../layouts/Layout';
import SignIn from '../components/pages/common/SignIn';
import DebateBoard from '../components/pages/common/DebateBoard';
import DebateRoom from '../components/pages/common/DebateRoom';

// Student 컴포넌트
import StudentSetUp from '../components/pages/student/StudentSetUp';
import StudentSignUp from '../components/pages/student/StudentSignUp';
import StudentMain from '../components/pages/student/StudentMain';
import StudentMyPage from '../components/pages/student/StudentMyPage';
import StudentStatistics from '../components/pages/student/StudentStatistics';
import StudentReportWrite from '../components/pages/student/StudentReportWrite';
import StudentReportDetail from '../components/pages/student/StudentReportDetail';
import StudentAllReportList from '../components/pages/student/StudentAllReportList';
import StudentNoti from '../components/pages/student/StudentNoti';
import StudentBookCategory from '../components/pages/student/StudentBookCategory';
import StudentBookList from '../components/pages/student/StudentBookList';
import StudentBookSearch from '../components/pages/student/StudentBookSearch';
import StudentHomeWorkReportList from '../components/pages/student/StudentHomeWorkReportList';

// Teacher 컴포넌트
import TeacherBookCategory from '../components/pages/teacher/TeacherBookCategory';
import TeacherBookList from '../components/pages/teacher/TeacherBookList';
import TeacherBookSearch from '../components/pages/teacher/TeacherBookSearch';
import TeacherClassAuth from '../components/pages/teacher/TeacherClassAuth';
import TeacherClassInfo from '../components/pages/teacher/TeacherClassInfo';
import TeacherHomeWorkReportList from '../components/pages/teacher/TeacherHomeWorkReportList';
import TeacherMain from '../components/pages/teacher/TeacherMain';
import TeacherMyPage from '../components/pages/teacher/TeacherMyPage';
import TeacherNoti from '../components/pages/teacher/TeacherNoti';
import TeacherProfileUpdate from '../components/pages/teacher/TeacherProfileUpdate';
import TeacherReportDetail from '../components/pages/teacher/TeacherReportDetail';
import TeacherReportList from '../components/pages/teacher/TeacherReportList';
import TeacherSignUp from '../components/pages/teacher/TeacherSignUp';
import TeacherStatistics from '../components/pages/teacher/TeacherStatistics';
import TeacherStudentInfo from '../components/pages/teacher/TeacherStudentInfo';
import AuthLayout from '../layouts/AuthLayout';
import Pending from '../components/molecules/Pending';
import WebRtcBoard from '../components/pages/common/WebRtcBoard';
import StudentHeader from '../components/commons/StudentHeader';
import TeacherHeader from '../components/commons/TeacherHeader';

const Router = () => {
  return (
    <Routes>
      {/* 로그인 및 회원가입 페이지 (헤더 & 푸터 X) */}
      <Route element={<AuthLayout />}>
        <Route path="/" element={<SignIn />} />
        <Route path="/student/setup" element={<StudentSetUp />} />
        <Route path="/student/signup" element={<StudentSignUp />} />
        <Route path="/teacher/signup" element={<TeacherSignUp />} />
        <Route path="/pending" element={<Pending />} />
      </Route>
      <Route element={<Layout />}>
        {/* 선생 학생 공통 라우트 */}
        {/* <Route path="/debate/board" element={<DebateBoard />} /> */}
        <Route path="/debate/board" element={<WebRtcBoard />} />
        <Route path="/debate/room" element={<DebateRoom />} />

        {/* 학생 전용 라우트 */}
        <Route path="/student/*" element={<StudentHeader />}>
          <Route path="main" element={<StudentMain />} />
          <Route path="signup" element={<StudentSignUp />} />
          <Route path="mypage" element={<StudentMyPage />} />
          <Route path="statistics" element={<StudentStatistics />} />
          <Route path="report/write" element={<StudentReportWrite />} />
          <Route path="report/detail/:id" element={<StudentReportDetail />} />
          <Route path="report/list" element={<StudentAllReportList />} />
          <Route path="noti" element={<StudentNoti />} />
          <Route path="book/category" element={<StudentBookCategory />} />
          <Route path="book/list" element={<StudentBookList />} />
          <Route path="book/search" element={<StudentBookSearch />} />
          <Route path="homework/list" element={<StudentHomeWorkReportList />} />
        </Route>

        {/* 교사 전용 라우트 */}
        <Route path="/teacher/*" element={<TeacherHeader />}>
          <Route path="main" element={<TeacherMain />} />
          <Route path="signup" element={<TeacherSignUp />} />
          <Route path="mypage" element={<TeacherMyPage />} />
          <Route path="profile-update" element={<TeacherProfileUpdate />} />
          <Route path="statistics" element={<TeacherStatistics />} />
          <Route path="reports" element={<TeacherReportList />} />
          <Route path="report/:id" element={<TeacherReportDetail />} />
          <Route
            path="homework-reports"
            element={<TeacherHomeWorkReportList />}
          />
          <Route path="students" element={<TeacherStudentInfo />} />
          <Route path="noti" element={<TeacherNoti />} />
          <Route path="class-auth" element={<TeacherClassAuth />} />
          <Route path="class-info" element={<TeacherClassInfo />} />
          <Route path="book/category" element={<TeacherBookCategory />} />
          <Route path="book/list" element={<TeacherBookList />} />
          <Route path="book/search" element={<TeacherBookSearch />} />
        </Route>
      </Route>
    </Routes>
  );
};

export default Router;
