import { useState } from 'react';
import { AwardDetail } from '../../services/awardsService';
import Modal from '../commons/Modal';

export const TrophyAwardSection = ({ awards }: { awards: AwardDetail[] }) => {
  return (
    <div
      className={`${awards.length == 0 ? 'h-[222px]' : ''} p-6 self-stretch bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex`}
    >
      <div className="text-gray-800 headline-medium">상장</div>
      <div className="self-stretch justify-start items-center gap-6 inline-flex">
        {awards.map((award, index) => (
          <TrophyAwardItem key={index} award={award} />
        ))}
      </div>
    </div>
  );
};

export const TrophyAwardItem = ({ award }: { award: AwardDetail }) => {
  const [modal, setModal] = useState(false);

  return (
    <>
      <Modal
        isOpen={modal}
        title={award.title}
        body={<img src={award.imagePath} />}
        confirmText={'확인'}
        cancelText={'닫기'}
        onConfirm={function (): void {
          setModal(false);
        }}
        onCancel={function (): void {
          setModal(false);
        }}
      />
      <div
        onClick={() => {
          setModal(true);
        }}
        className="w-32 flex-col justify-start items-center gap-3 inline-flex group cursor-pointer"
      >
        <img
          className="w-[100px] h-[100px]"
          src={'/assets/images/Medal.png'}
          alt={award.title}
        />
        <div
          className={`self-stretch text-center body-medium group-hover:text-primary-500 text-gray-800`}
        >
          {award.title}
        </div>
      </div>
    </>
  );
};
