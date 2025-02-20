import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import questionAPI from "@/api/questionAPI";
import { Question } from "@/types/question";
import QuestionDetail from "@/components/question/QuestionDetail";

const QuestionDetailPage = () => {
  const { id } = useParams<{ id: string }>();
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

  if (loading) return <p>로딩 중...</p>;
  if (!question) return <p>질문을 찾을 수 없습니다.</p>;

  return (
    <div className="container mx-auto p-6">
      <QuestionDetail question={question} />
    </div>
  );
};

export default QuestionDetailPage;
