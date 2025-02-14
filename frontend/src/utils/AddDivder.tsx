import React, { ReactNode } from 'react';

export const AddDivider = ({ itemList }: { itemList: ReactNode[] }) => {
  return (
    <div className="self-stretch flex flex-col">
      {itemList.map((item, index) => (
        <React.Fragment key={index}>
          {item}
          {index < itemList.length - 1 && (
            <hr className="border-gray-200" key={`divider-${index}`} />
          )}
        </React.Fragment>
      ))}
    </div>
  );
};
