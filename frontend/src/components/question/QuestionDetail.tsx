import { useState } from "react";
import { Question } from "@/types/question";
import AnswerList from "@/components/answer/AnswerList";
import Button from "@/components/ui/Button";
import Textarea from "@/components/ui/Textarea";
import questionAPI from "@/api/questionAPI";
import answerAPI from "@/api/answerAPI";
import { useNavigate } from "react-router-dom";
import { StatusCodes } from "http-status-codes";

interface QuestionDetailProps {
  question: Question;
}

const QuestionDetail = ({ question }: QuestionDetailProps) => {
  const [answers, setAnswers] = useState(question.answers ?? []);
  const [error, setError] = useState<string | null>(null);
  const [isAddingAnswer, setIsAddingAnswer] = useState(false);
  const [newAnswer, setNewAnswer] = useState("");
  const navigate = useNavigate();

  const voteQuestion = (id: number) => {
    try {
      questionAPI.voteQuestion(id);
      navigate(0);
    } catch (error: any) {
      const status = error.response?.status;
      if (
        status === StatusCodes.UNAUTHORIZED ||
        status === StatusCodes.FORBIDDEN
      ) {
        navigate("/login");
      }
    }
  };

  const deleteQuestion = async (id: number) => {
    try {
      await questionAPI.deleteQuestion(id);
      navigate("/question/list");
    } catch (error: any) {
      setError("삭제하는 도중 에러가 발생했습니다.");
    }
  };

  const handleAddAnswer = async () => {
    if (!newAnswer.trim()) return;
    try {
      const response = await answerAPI.createAnswer(question.id, newAnswer);
      setAnswers([...answers, response]);
      setNewAnswer("");
      setIsAddingAnswer(false);
    } catch (error) {
      setError("답변 등록 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="flex-column">
      <h2 className="text-2xl font-bold mb-4">{question.subject}</h2>
      <p className="mb-4">{question.content}</p>
      <div className="text-gray-600 text-sm justify-self-end">
        생성일: {new Date(question.createDate).toLocaleString()}
      </div>
      {question.modifyDate && (
        <div className="text-gray-600 text-sm justify-self-end">
          수정일: {new Date(question.modifyDate).toLocaleString()}
        </div>
      )}
      <div className="flex space-x-2">
        <Button
          text={`추천 ${question.voters.length}`}
          onClick={() => voteQuestion(question.id)}
        />
        <Button
          text="수정"
          onClick={() => navigate(`/question/${question.id}/edit`)}
        />
        <Button text="삭제" onClick={() => deleteQuestion(question.id)} />
      </div>

      {error && <p className="text-red-500 mt-2">{error}</p>}

      {isAddingAnswer ? (
        <div className="border p-4 my-3 rounded-md border-gray-300">
          <Textarea
            value={newAnswer}
            onChange={(e) => setNewAnswer(e.target.value)}
            placeholder="답변을 입력하세요"
          />
          <div className="flex space-x-2 mt-2">
            <Button text="저장" onClick={handleAddAnswer} />
            <Button text="취소" onClick={() => setIsAddingAnswer(false)} />
          </div>
        </div>
      ) : (
        <Button
          text="답변 등록하기"
          className="my-3"
          onClick={() => setIsAddingAnswer(true)}
        />
      )}

      <AnswerList answers={answers} setAnswers={setAnswers} />
    </div>
  );
};

export default QuestionDetail;
