import { atom } from 'recoil';
import { LoginResponse } from '../../services/usersService';
import secureLocalStorage from 'react-secure-storage';
import { recoilPersist } from 'recoil-persist';

const secureStorage = {
  getItem: (key: string) => {
    const item = secureLocalStorage.getItem(key);

    return typeof item === 'string' ? item : null;
  },
  setItem: (key: string, value: string) =>
    secureLocalStorage.setItem(key, value),
  removeItem: (key: string) => secureLocalStorage.removeItem(key),
};

const { persistAtom } = recoilPersist({
  key: 'accessTokenState',
  storage: secureStorage,
});

// 현재 로그인한 사용자 상태
export const currentUserState = atom<LoginResponse>({
  key: 'currentUserState',
  default: {
    id: '0',
    name: '김미소',
    role: '교사사',
    profileImagePath: '/assets/images/profile.png',
  },
  effects_UNSTABLE: [persistAtom],
});

// 사용자 인증 상태
export const isAuthenticatedState = atom<boolean>({
  key: 'isAuthenticatedState',
  default: false,
});

// 비밀번호 확인 상태
export const isPasswordVerifiedState = atom<boolean>({
  key: 'isPasswordVerifiedState',
  default: false,
});

// 이메일 인증 상태
export const isEmailVerifiedState = atom<boolean>({
  key: 'isEmailVerifiedState',
  default: false,
});

// 회원가입 성공 여부
export const isSignupSuccessfulState = atom<boolean>({
  key: 'isSignupSuccessfulState',
  default: false,
});

// accessToken 상태
export const accessTokenState = atom<string | null>({
  key: 'accessTokenState',
  default: null,
  effects_UNSTABLE: [persistAtom],
});
