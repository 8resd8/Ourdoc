import { diffWords } from 'diff';

export function HighlightText({ beforeContent, afterContent }: any) {
  const differences = diffWords(beforeContent, afterContent);

  return (
    <div>
      {differences.map((part, index) =>
        part.added || (!part.added && !part.removed) ? (
          <span key={index} className={part.added ? 'bg-primary-200' : ''}>
            {part.value}
          </span>
        ) : null
      )}
    </div>
  );
}
