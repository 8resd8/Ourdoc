interface ReportBookCardProps {
  imageUrl: string;
  title: string;
  author: string;
  publisher: string;
  genre: string;
  publishYear: string;
}
export const ReportBookCard = (props: ReportBookCardProps) => {
  return (
    <div className="flex bg-gray-0 shadow-xxsmall rounded-lg p-6 w-[630px] h-[340px]">
      <img
        src={props.imageUrl}
        alt={props.title}
        className="w-[190px] h-[282px] object-cover rounded-md mr-6"
      />
      <div className="mt-12">
        <h2 className="headline-medium font-bold mb-4">{props.title}</h2>
        <p className="body-medium text-gray-700 mb-4">저자: {props.author}</p>
        <p className="body-medium text-gray-700 mb-4">
          출판사: {props.publisher}
        </p>
        <p className="body-medium text-gray-700 mb-4">장르: {props.genre}</p>
        <p className="body-medium text-gray-700">
          출판년도: {props.publishYear}
        </p>
      </div>
    </div>
  );
};
