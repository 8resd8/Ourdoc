import { atom } from 'recoil';

// 사용자 로그인 상태
export const userState = atom({
  key: 'userState',
  default: {
    id: '',
    name: '',
    isLoggedIn: false,
  },
});
