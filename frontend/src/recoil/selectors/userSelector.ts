import { selector } from 'recoil';
import { userState } from '../atoms';

// 로그인 여부 체크
export const isUserLoggedInSelector = selector({
  key: 'isUserLoggedInSelector',
  get: ({ get }) => {
    const user = get(userState);
    return user.isLoggedIn;
  },
});
