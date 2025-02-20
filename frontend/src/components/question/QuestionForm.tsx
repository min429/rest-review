import { useState } from "react";
import Button from "@/components/ui/Button";

interface QuestionFormProps {
  question?: { subject: string; content: string };
  onSubmit: (data: { subject: string; content: string }) => void;
}

const QuestionForm = ({ question, onSubmit }: QuestionFormProps) => {
  const [subject, setSubject] = useState(question?.subject || "");
  const [content, setContent] = useState(question?.content || "");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ subject, content });
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
      <div>
        <label className="block font-bold">제목</label>
        <input
          type="text"
          value={subject}
          onChange={(e) => setSubject(e.target.value)}
          className="w-full p-2 border rounded"
          placeholder="제목을 입력하세요"
        />
      </div>
      <div>
        <label className="block font-bold">내용</label>
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          className="w-full p-2 border rounded"
          placeholder="내용을 입력하세요"
        />
      </div>
      <Button text="저장" type="submit" />
    </form>
  );
};

export default QuestionForm;
