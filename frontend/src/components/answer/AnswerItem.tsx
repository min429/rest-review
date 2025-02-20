import { useState } from "react";
import answerAPI from "@/api/answerAPI";
import { Answer } from "@/types/answer";
import Button from "@/components/ui/Button";
import Textarea from "@/components/ui/Textarea";
import { useNavigate } from "react-router-dom";

interface AnswerItemProps {
  answer: Answer;
  onDelete: (answerId: number) => void;
}

const AnswerItem = ({ answer, onDelete }: AnswerItemProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(answer.content);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleEdit = async () => {
    setLoading(true);
    setError(null);
    try {
      await answerAPI.modifyAnswer(answer.id, content);
      setIsEditing(false);
    } catch (err) {
      setError("답변 수정 중 오류가 발생했습니다.");
      console.error("답변 수정 오류:", err);
    } finally {
      setLoading(false);
    }
  };

  const answerVote = (id: number) => {
    try {
      answerAPI.voteAnswer(id);
      navigate(0);
    } catch (err) {
      setError("추천 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="border p-4 my-2 rounded-md border-gray-300">
      {isEditing ? (
        <Textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      ) : (
        <p>{content}</p>
      )}

      <div className="text-sm text-gray-600 justify-self-end">
        생성일: {new Date(answer.createDate).toLocaleString()}
      </div>
      {answer.modifyDate && (
        <div className="text-gray-600 text-sm justify-self-end">
          수정일: {new Date(answer.modifyDate).toLocaleString()}
        </div>
      )}

      {error && <p className="text-red-500 text-sm">{error}</p>}

      <div className="mt-2 flex space-x-2">
        {isEditing ? (
          <>
            <Button
              text="저장"
              onClick={handleEdit}
              disabled={loading}
              className="text-sm"
            />
            <Button
              text="취소"
              onClick={() => setIsEditing(false)}
              disabled={loading}
              className="text-sm"
            />
          </>
        ) : (
          <>
            <Button
              text={`추천 ${answer.voters.length}`}
              onClick={() => answerVote(answer.id)}
              disabled={loading}
              className="text-sm"
            />
            <Button
              text="수정"
              onClick={() => setIsEditing(true)}
              className="text-sm"
            />
            <Button
              text="삭제"
              onClick={() => onDelete(answer.id)}
              className="text-sm"
            />
          </>
        )}
      </div>
    </div>
  );
};

export default AnswerItem;
