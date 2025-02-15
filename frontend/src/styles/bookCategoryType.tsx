export enum BookCategoryType {
  HomeWork = 'Homework',
  Class = 'Class',
  Favorite = 'Favorite',
  Grade = 'Grade',
}

export const BookCategoryExtension: Record<
  BookCategoryType,
  Record<string, string>
> = {
  [BookCategoryType.HomeWork]: {
    korean: '숙제',
    imgSrc: '/assets/images/homework.svg',
  },
  [BookCategoryType.Class]: {
    korean: '학급',
    imgSrc: '/assets/images/class.svg',
  },
  [BookCategoryType.Favorite]: {
    korean: '관심',
    imgSrc: '/assets/images/favorite.svg',
  },
  [BookCategoryType.Grade]: {
    korean: '학년',
    imgSrc: '/assets/images/grade.svg',
  },
};
