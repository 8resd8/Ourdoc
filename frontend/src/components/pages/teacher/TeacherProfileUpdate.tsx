import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { updateTeacherProfileApi } from '../../../services/teachersService';
import AddressSearchModal from '../../commons/AddressSearchModal';
import UploadModal from '../../commons/UploadModal';
import { CameraIcon } from 'lucide-react';

const TeacherProfileUpdate = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const schoolName = searchParams.get('schoolName');
  const profileImage = searchParams.get('profileImage') || undefined;
  const name = searchParams.get('name');
  const loginId = searchParams.get('loginId');
  const email = searchParams.get('email');
  const phone = searchParams.get('phone');
  const year = new Date().getFullYear();
  const grade = searchParams.get('grade');
  const schoolId = Number(searchParams.get('schoolId'));
  const classNumber = searchParams.get('classNumber');
  const formatPhoneNumber = (value: string) => {
    const numericValue = value.replace(/\D/g, '');
    if (numericValue.length <= 3) {
      return numericValue;
    } else if (numericValue.length <= 7) {
      return `${numericValue.slice(0, 3)}-${numericValue.slice(3)}`;
    } else {
      return `${numericValue.slice(0, 3)}-${numericValue.slice(3, 7)}-${numericValue.slice(7, 11)}`;
    }
  };
  const [certificateFile, setCertificateFile] = useState<File | null>(null);
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    name: name,
    loginId: loginId,
    email: email,
    phone: formatPhoneNumber(phone || ''),
    schoolId: schoolId,
    year: year,
    grade: grade,
    classNumber: classNumber,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (name === 'phone') {
      const formattedValue = formatPhoneNumber(value);
      setFormData((prevData) => ({
        ...prevData,
        [name]: formattedValue,
      }));
    } else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  const [school, setSchool] = useState<{ schoolName: string }>({
    schoolName: schoolName || '',
  });
  const handleSelectSchool = (selectedSchool: {
    schoolName: string;
    id: number;
  }) => {
    setSchool((prevData) => ({
      ...prevData,
      schoolName: selectedSchool.schoolName,
    }));
    setFormData((prevData) => ({
      ...prevData,
      schoolId: selectedSchool.id,
    }));
    setmodal(false);
  };
  const navigate = useNavigate();
  const updateProfile = async () => {
    try {
      const requestData = {
        name: formData.name || '',
        loginId: formData.loginId || '',
        email: formData.email || '',
        phone: formData.phone || '',
        schoolId: formData.schoolId || 0,
        year: Number(formData.year) || 0,
        grade: Number(formData.grade) || 0,
        classNumber: Number(formData.classNumber) || 0,
      };
      const response = await updateTeacherProfileApi(
        requestData,
        certificateFile
      );
      navigate('/teacher/mypage');
    } catch (error) {
      console.log(error);
    }
  };
  console.log(formData);

  const handleUploadConfirm = (file: File | null) => {
    if (file) {
      setCertificateFile(file);
    }
    setIsUploadModalOpen(false);
  };

  const handleUploadClick = () => {
    setIsUploadModalOpen(true);
  };
  const handleUploadCancel = () => {
    setIsUploadModalOpen(false);
  };
  const [modal, setmodal] = useState(false);
  return (
    <div className="flex flex-col justify-center items-center min-h-screen">
      <div className="relative">
        <img
          className="w-48 h-48 rounded-full border border-gray-300"
          src={profileImage}
          alt="프로필 이미지"
        />
        <button
          className="absolute bottom-2 right-2 bg-gray-700 border border-gray-200 text-white p-2 rounded-full hover:text-primary-500 hover:bg-gray-0 cursor-pointer"
          onClick={handleUploadClick}
        >
          <CameraIcon className="w-6 h-6" />
        </button>
      </div>
      <div className="text-center text-gray-800 body-medium font-semibold mt-4">
        {name} 님
      </div>
      <div className="flex flex-col justify-start items-center gap-[20px] mt-6">
        <div className="flex flex-col justify-start items-center gap-8">
          {[
            {
              label: '아이디',
              name: 'loginId',
              placeholder: loginId,
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
                  item.name === 'schoolName'
                    ? school.schoolName || ''
                    : (formData[item.name as keyof typeof formData] ?? '')
                }
                onChange={handleChange}
                placeholder={item.placeholder ?? undefined}
                disabled={item.disabled}
                className="w-full h-10 py-2 bg-gray-0 border-b border-gray-200 text-gray-800 body-small focus:outline-none"
              />
            </div>
          ))}

          <div className="flex justify-between w-[414px] gap-4">
            <div className="flex-1">
              <label className="text-gray-800 body-small">년도</label>
              <input
                type="text"
                name="year"
                value={year ?? undefined}
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
                  value={formData[item.name as keyof typeof formData] ?? ''}
                  onChange={handleChange}
                  placeholder={item.label}
                  className="w-full h-10 py-2 border-b border-gray-200 text-gray-800 body-small focus:outline-none"
                />
              </div>
            ))}
          </div>
        </div>
        <button
          onClick={updateProfile}
          className="w-[414px] px-[140px] py-4 bg-primary-500 rounded-[10px] body-medium text-gray-0 mb-2 cursor-pointer"
        >
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
      <UploadModal
        type="profile"
        isOpen={isUploadModalOpen}
        onConfirm={handleUploadConfirm}
        onCancel={handleUploadCancel}
      />
    </div>
  );
};

export default TeacherProfileUpdate;
