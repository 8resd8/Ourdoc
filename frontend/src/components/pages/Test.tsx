import { useEffect, useState } from 'react';
import { getClassStudentListApi } from '../../services/teachersService';

const Test = () => {
  console.log('Test');

  const testApi = async () => {
    try {
      const response = await getClassStudentListApi();
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    testApi();
  }, []);

  return (
    <div className="text-center text-9xl">
      <h1>콘솔을 찍어서 확인하세요</h1>
    </div>
  );
};
export default Test;
