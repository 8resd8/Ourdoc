import { useRecoilValue } from 'recoil';
import { loadingState } from '../../recoil/atoms/loadingAtoms';

const Loader = () => {
  const isLoading = useRecoilValue(loadingState);

  if (!isLoading) return null;

  return (
    <div className="fixed flex-col gap-4 top-0 left-0 w-full z-50 h-full flex items-center justify-center bg-transparent opacity-100">
      <img src="/assets/images/loading.gif" alt="Loading..." />
      <div className="headline-medium text-gray-700">로딩 중입니다.</div>
    </div>
  );
};

export default Loader;
