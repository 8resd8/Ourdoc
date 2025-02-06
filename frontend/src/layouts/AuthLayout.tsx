import { Outlet } from 'react-router-dom';
import Footer from '../components/commons/Footer';

const AuthLayout = () => {
  return (
    <div>
      <Outlet />
    </div>
  );
};

export default AuthLayout;
