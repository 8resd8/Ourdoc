import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import {
  searchSchoolsApi,
  updateTeacherProfileApi,
} from '../../../services/teachersService';
import Modal from '../../commons/Modal';
import AddressSearchModal from '../../commons/AddressSearchModal';

const TeacherProfileUpdate = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const schoolName = searchParams.get('schoolName');
  const profileImage = searchParams.get('profileImage') || undefined;
  console.log(profileImage);

  const [formData, setFormData] = useState({
    name: searchParams.get('name') || '',
    loginId: searchParams.get('loginId') || '',
    email: searchParams.get('email') || '',
    phone: searchParams.get('phone') || '',
    schoolId: Number(searchParams.get('schoolId')) || 0,
    year: Number(new Date().getFullYear().toString()) || 0,
    grade: Number(searchParams.get('grade')) || 0,
    classNumber: Number(searchParams.get('classNumber')) || 0,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSelectSchool = (selectedSchool: {
    schoolName: string;
    schoolId: number;
  }) => {
    setFormData((prevData) => ({
      ...prevData,
      schoolId: selectedSchool.schoolId,
    }));
    setmodal(false);
  };

  const updateProfile = async () => {
    const response = await updateTeacherProfileApi(formData);
  };

  const [modal, setmodal] = useState(false);
  return (
    <div className="flex flex-col justify-center items-center min-h-screen">
      <img
        className="w-[196px] h-[196px] rounded-full border border-gray-200"
        src={
          profileImage == '' ? '/assets/images/tmpProfile.png' : profileImage
        }
        alt="프로필"
      />
      <div className="text-center text-gray-800 body-medium font-semibold mt-4">
        {formData.name} 님
      </div>
      <div className="flex flex-col justify-start items-center gap-[20px] mt-6">
        <div className="flex flex-col justify-start items-center gap-8">
          {[
            {
              label: '아이디',
              name: 'loginId',
              placeholder: formData?.loginId,
              disabled: true,
            },
            {
              label: '이메일',
              name: 'email',
              placeholder: '이메일을 입력해주세요.',
            },
            {
              label: '전화번호',
              name: 'phone',
              placeholder: '전화번호를 입력해주세요.',
            },
            {
              label: '소속학교',
              name: 'schoolName',
              placeholder: '소속 학교를 입력해주세요.',
            },
          ].map((item, idx) => (
            <div key={idx} className="w-[414px]">
              <label className="text-gray-800 body-small">{item.label}</label>
              <input
                onClick={
                  item.name === 'schoolName' ? () => setmodal(true) : undefined
                }
                type="text"
                name={item.name}
                value={
                  item.name == 'schoolName'
                    ? schoolName || ''
                    : formData[item.name as keyof typeof formData]
                }
                onChange={handleChange}
                placeholder={item.placeholder}
                disabled={item.disabled}
                className="w-full h-10 py-2 bg-gray-0 border-b border-gray-200 text-gray-800 body-small focus:outline-none cursor-pointer"
              />
            </div>
          ))}
          <div className="flex justify-between w-[414px] gap-4">
            <div className="flex-1">
              <label className="text-gray-800 body-small">년도</label>
              <input
                type="text"
                name="year"
                value={formData.year}
                onChange={handleChange}
                disabled
                className="w-full h-10 py-2 border-b border-gray-200 text-gray-800 body-small focus:outline-none"
              />
            </div>
            {[
              { label: '학년', name: 'grade' },
              { label: '반', name: 'classNumber' },
            ].map((item, idx) => (
              <div key={idx} className="flex-1">
                <label className="text-gray-800 body-small">{item.label}</label>
                <input
                  type="text"
                  name={item.name}
                  value={formData[item.name as keyof typeof formData]}
                  onChange={handleChange}
                  placeholder={item.label}
                  className="w-full h-10 py-2 border-b border-gray-200 text-gray-800 body-small focus:outline-none"
                />
              </div>
            ))}
          </div>
        </div>
        <button className="w-[414px] px-[140px] py-4 bg-primary-500 rounded-[10px] body-medium text-gray-0 mb-2">
          저장하기
        </button>
      </div>
      {modal && (
        <AddressSearchModal
          onClose={() => setmodal(false)}
          open={modal}
          onSelectSchool={handleSelectSchool}
        />
      )}
    </div>
  );
};

export default TeacherProfileUpdate;
