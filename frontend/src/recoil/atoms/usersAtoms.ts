import { atom } from 'recoil';
import { LoginResponse } from '../../services/usersService';

// 현재 로그인한 사용자 상태
export const currentUserState = atom<LoginResponse | null>({
  key: 'currentUserState',
  default: null,
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
