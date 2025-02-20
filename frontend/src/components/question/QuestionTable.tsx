import QuestionItem from "./QuestionItem";
import { Question } from "@/types/question";

interface QuestionTableProps {
  questions: Question[];
}

const QuestionTable = ({ questions }: QuestionTableProps) => {
  return (
    <table className="w-full border-collapse border border-gray-300">
      <thead>
        <tr className="bg-gray-400">
          <th className="border p-2">번호</th>
          <th className="border p-2">제목</th>
          <th className="border p-2">글쓴이</th>
          <th className="border p-2">작성일시</th>
        </tr>
      </thead>
      <tbody>
        {questions.map((question, index) => (
          <QuestionItem key={question.id} question={question} index={index} />
        ))}
      </tbody>
    </table>
  );
};

export default QuestionTable;
