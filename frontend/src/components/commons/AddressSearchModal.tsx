import { useState } from 'react';
import { searchSchoolsApi } from '../../services/teachersService';

interface AddressSearchModalProps {
  open: boolean;
  onClose: () => void;
  onSelectSchool: (school: { schoolName: string; schoolId: number }) => void;
}

const AddressSearchModal = ({
  open,
  onClose,
  onSelectSchool,
}: AddressSearchModalProps) => {
  const [school, setSchool] = useState<
    { schoolName: string; schoolId: number; address: string }[]
  >([]);
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = async (schoolName: string, page = 0, size = 10) => {
    try {
      const response = await searchSchoolsApi(schoolName, page, size);
      setSchool(response.content);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleSelect = (selectedSchool: {
    schoolName: string;
    schoolId: number;
  }) => {
    onSelectSchool(selectedSchool); // 부모로 데이터 전달
    onClose(); // 모달 닫기
  };

  return (
    open && (
      <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
        <div className="bg-white rounded-2xl shadow-lg w-96 p-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">
            학교 찾기
          </h2>
          <div className="flex items-center gap-2">
            <input
              type="text"
              placeholder="소속 학교명을 검색하세요"
              className="w-[260px] border border-gray-300 rounded-md px-3 py-2 focus:ring-2 focus:ring-primary-500"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <button
              className="bg-primary-500 text-white px-4 py-2 rounded-md hover:bg-primary-600 transition w-full"
              onClick={() => handleSearch(searchQuery)}
            >
              검색
            </button>
          </div>

          <ul className="mt-4 max-h-60 overflow-y-auto border-t border-gray-200">
            {school.map((data, idx) => (
              <li
                key={idx}
                className="p-3 cursor-pointer hover:bg-gray-100"
                onClick={() =>
                  handleSelect({
                    schoolName: data.schoolName,
                    schoolId: data.schoolId,
                  })
                }
              >
                <div className="caption-large">{data.schoolName}</div>
                <div className="caption-medium">{data.address}</div>
              </li>
            ))}
          </ul>

          <div className="flex justify-end mt-4">
            <button
              className="text-gray-600 hover:text-gray-800 px-4 py-2 cursor-pointer"
              onClick={onClose}
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    )
  );
};

export default AddressSearchModal;
