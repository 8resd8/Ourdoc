import { useState } from 'react';
import { Clipboard, Check } from 'lucide-react';

interface CopyUrlButtonProps {
  url: string;
}

const CopyUrlButton = ({ url }: CopyUrlButtonProps) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(url);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch (error) {
      console.error('URL 복사 실패:', error);
    }
  };

  return (
    <button
      onClick={handleCopy}
      className="flex items-center px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition-all"
    >
      {copied ? (
        <>
          <Check className="w-5 h-5 mr-2 text-green-300" /> 복사 완료!
        </>
      ) : (
        <>
          <Clipboard className="w-5 h-5 mr-2" /> URL 복사
        </>
      )}
    </button>
  );
};

export default CopyUrlButton;
