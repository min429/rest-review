import AnswerItem from "./AnswerItem";
import answerAPI from "@/api/answerAPI";
import { Answer } from "@/types/answer";

interface AnswerListProps {
  answers: Answer[];
  setAnswers: (answers: Answer[]) => void;
}

const AnswerList = ({ answers, setAnswers }: AnswerListProps) => {
  const handleDelete = async (answerId: number) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;

    try {
      await answerAPI.deleteAnswer(answerId);
      setAnswers(answers.filter((answer) => answer.id !== answerId));
    } catch (err) {
      console.error("답변 삭제 중 오류 발생:", err);
    }
  };

  return (
    <div>
      {answers.length === 0 ? (
        <p className="text-gray-500">아직 답변이 없습니다.</p>
      ) : (
        answers.map((answer) => (
          <AnswerItem key={answer.id} answer={answer} onDelete={handleDelete} />
        ))
      )}
    </div>
  );
};

export default AnswerList;
