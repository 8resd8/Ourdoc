import { selector } from 'recoil';
import {
  currentUserState,
  isAuthenticatedState,
  isPasswordVerifiedState,
  isEmailVerifiedState,
  isSignupSuccessfulState,
} from '../atoms/usersAtoms';
import { LoginResponse } from '../../services/usersService';

// 사용자 상태와 인증 여부 반환
export const userStatusSelector = selector<{
  user: LoginResponse | null;
  isAuthenticated: boolean;
}>({
  key: 'userStatusSelector',
  get: ({ get }) => {
    return {
      user: get(currentUserState),
      isAuthenticated: get(isAuthenticatedState),
    };
  },
});

// 비밀번호 확인 상태 반환
export const passwordVerificationSelector = selector<boolean>({
  key: 'passwordVerificationSelector',
  get: ({ get }) => get(isPasswordVerifiedState),
});

// 이메일 인증 상태 반환
export const emailVerificationSelector = selector<boolean>({
  key: 'emailVerificationSelector',
  get: ({ get }) => get(isEmailVerifiedState),
});

// 회원가입 성공 여부 반환
export const signupStatusSelector = selector<boolean>({
  key: 'signupStatusSelector',
  get: ({ get }) => get(isSignupSuccessfulState),
});
