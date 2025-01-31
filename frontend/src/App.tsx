import { useState } from 'react';

import './App.css';

function App() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <div className="bg-system-success">Pretendard 폰트 적용됨</div>
      <div className="bg-primary-300">Primary-500 background</div>
      <h1 className="font-size-(--display-xlarge) leading-(--display-xlarge-line) font-semibold font-pretendard">
        Display XLarge Text
      </h1>

      <p className="font-size-(--display-xlarge) leading-(--body-medium-line) font-regular font-pretendard">
        Body Medium Text
      </p>

      <div className="shadow-xl w-40 h-40">Shadow 적용된 박스</div>
    </div>
  );
}

export default App;
