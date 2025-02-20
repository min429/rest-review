import { Link } from "react-router-dom";
import { Question } from "@/types/question";

interface QuestionItemProps {
  question: Question;
  index: number;
}

const QuestionItem = ({ question, index }: QuestionItemProps) => {
  return (
    <tr className="text-center border-b border-gray-300">
      <td className="p-2 border border-gray-300 text-black">{index + 1}</td>
      <td className="p-2 border border-gray-300 text-left">
        <Link
          to={`/question/${question.id}`}
          className="text-black hover:underline"
        >
          {question.subject}
        </Link>
        {question.answers.length > 0 && (
          <span className="text-red-500 text-sm ml-2">
            [{question.answers.length}]
          </span>
        )}
      </td>
      <td className="p-2 border border-gray-300 text-black">
        {question.author?.username || "익명"}
      </td>
      <td className="p-2 border border-gray-300 text-black">
        {new Date(question.createDate).toLocaleString()}
      </td>
    </tr>
  );
};

export default QuestionItem;
