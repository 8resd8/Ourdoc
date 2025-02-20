export function diff(text1: string, text2: string) {
  const diff = [];
  let i = 0,
    j = 0;

  while (i < text1.length && j < text2.length) {
    if (text1[i] !== text2[j]) {
      let diffStart = i;
      while (i < text1.length && j < text2.length && text1[i] !== text2[j]) {
        i++;
        j++;
      }
      diff.push({ start: diffStart, end: i });
    } else {
      i++;
      j++;
    }
  }

  return diff;
}
