enum CircleSizeType {
  big,
  medium,
  small,
}

export const StudentClassAverageSection = ({
  title,
  otherCount,
  myCount,
  isClass,
}: {
  title: string;
  otherCount: number;
  myCount: number;
  isClass?: boolean;
}) => {
  let otherType = CircleSizeType.medium;
  let myType = CircleSizeType.medium;

  if (otherCount - myCount > 0) {
    otherType = CircleSizeType.big;
    myType = CircleSizeType.small;
  } else if (otherCount - myCount == 0) {
    otherType = CircleSizeType.medium;
    myType = CircleSizeType.medium;
  } else {
    otherType = CircleSizeType.small;
    myType = CircleSizeType.big;
  }

  return (
    <div className="w-[522px] h-[464px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">{title}</div>
      <div className="h-full px-4 self-stretch justify-between items-center flex flex-row">
        <Circle count={otherCount} type={otherType} isClass={isClass} />
        <Circle count={myCount} type={myType} isMine />
      </div>
    </div>
  );
};

const Circle = ({
  count,
  type,
  isMine,
  isClass,
}: {
  count: number;
  type: CircleSizeType;
  isMine?: boolean;
  isClass?: boolean;
}) => {
  let size = '';

  switch (type) {
    case CircleSizeType.big:
      size = 'w-[240px] h-[240px]';
      break;

    case CircleSizeType.medium:
      size = 'w-[200px] h-[200px]';
      break;

    case CircleSizeType.small:
      size = 'w-[160px] h-[160px]';
      break;
  }

  return (
    <div className="flex flex-col items-center gap-2">
      <div
        className={`${size} rounded-[120px] ${isMine ? 'border-2 border-primary-500' : 'border-1 border-gray-700'} justify-center items-center inline-flex `}
      >
        <div className={`${isMine ? 'text-primary-500' : 'text-gray-700'}`}>
          <span className="display-medium">{count}</span>
          <span className="body-medium">권</span>
        </div>
      </div>
      <div
        className={`${isMine ? 'text-primary-500' : 'text-gray-800'} body-medium`}
      >
        {isMine ? '나' : isClass ? '학급 평균' : '반 1등'}
      </div>
    </div>
  );
};
