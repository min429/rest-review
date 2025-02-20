import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import questionAPI from "@/api/questionAPI";
import { Question } from "@/types/question";
import QuestionForm from "@/components/question/QuestionForm";

const QuestionEditPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [question, setQuestion] = useState<Question | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!id) return;
    questionAPI
      .fetchQuestionDetail(Number(id))
      .then(setQuestion)
      .catch(() => console.error("질문을 불러오는 중 오류 발생"))
      .finally(() => setLoading(false));
  }, [id]);

  const handleEdit = async (updatedData: {
    subject: string;
    content: string;
  }) => {
    if (!id) return;
    try {
      await questionAPI.modifyQuestion(
        Number(id),
        updatedData.subject,
        updatedData.content
      );
      navigate(`/question/${id}`);
    } catch (error) {
      console.error("질문 수정 중 오류 발생:", error);
    }
  };

  if (loading) return <p>로딩 중...</p>;
  if (!question) return <p>질문을 찾을 수 없습니다.</p>;

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">질문 수정</h2>
      <QuestionForm question={question} onSubmit={handleEdit} />
    </div>
  );
};

export default QuestionEditPage;
