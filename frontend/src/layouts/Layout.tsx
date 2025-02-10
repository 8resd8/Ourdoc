import { Outlet } from 'react-router-dom';
import Header from '../components/commons/Header';
import Footer from '../components/commons/Footer';

const Layout = () => {
  return (
    <div>
      <Header userName="zz" />
      <Outlet />
    </div>
  );
};

export default Layout;
