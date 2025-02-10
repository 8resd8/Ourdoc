import { useState } from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

export default function StyledSelect() {
  const [category, setCategory] = useState('도서명'); // 기본값: 도서명

  const handleChange = (event: SelectChangeEvent) => {
    setCategory(event.target.value);
  };

  return (
    <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
      <InputLabel
        id="demo-simple-select-standard-label"
        sx={{
          fontSize: '14px', // 라벨 글씨 크기
          color: '#333', // 라벨 색상
        }}
      >
        분류
      </InputLabel>
      <Select
        labelId="demo-simple-select-standard-label"
        id="demo-simple-select-standard"
        value={category}
        onChange={handleChange}
        label="분류"
        sx={{
          fontSize: '16px', // 드롭다운 글씨 크기
          color: '#555', // 드롭다운 텍스트 색상
          '& .MuiSelect-icon': {
            color: '#888', // 드롭다운 아이콘 색상
          },
          '& .MuiOutlinedInput-notchedOutline': {
            borderColor: '#ccc', // 테두리 색상
          },
          '&:hover .MuiOutlinedInput-notchedOutline': {
            borderColor: '#888', // 호버 시 테두리 색상
          },
        }}
      >
        <MenuItem value="도서명">도서명</MenuItem>
        <MenuItem value="저자">저자</MenuItem>
        <MenuItem value="출판사">출판사</MenuItem>
      </Select>
    </FormControl>
  );
}
