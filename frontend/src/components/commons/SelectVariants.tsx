import { useState } from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

interface StyledSelectProps {
  onCategoryChange: (category: string) => void;
}

export default function StyledSelect({ onCategoryChange }: StyledSelectProps) {
  const [category, setCategory] = useState('도서명');

  const handleChange = (event: SelectChangeEvent) => {
    const newCategory = event.target.value;
    setCategory(newCategory);
    onCategoryChange(newCategory);
  };

  return (
    <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
      <InputLabel
        id="demo-simple-select-standard-label"
        sx={{ fontSize: '14px', color: '#333' }}
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
          fontSize: '15px',
          color: '#555',
          '& .MuiSelect-icon': { color: '#888' },
          '& .MuiOutlinedInput-notchedOutline': { borderColor: '#ccc' },
          '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: '#888' },
        }}
      >
        <MenuItem value="도서명">도서명</MenuItem>
        <MenuItem value="저자">저자</MenuItem>
        <MenuItem value="출판사">출판사</MenuItem>
      </Select>
    </FormControl>
  );
}
