import { useNavigate } from "react-router-dom";
import questionAPI from "@/api/questionAPI";
import QuestionForm from "@/components/question/QuestionForm";

const QuestionCreatePage = () => {
  const navigate = useNavigate();

  const handleCreate = async (data: { subject: string; content: string }) => {
    try {
      await questionAPI.createQuestion(data.subject, data.content);
      navigate("/question/list");
    } catch (error) {
      console.error("질문 생성 중 오류 발생:", error);
    }
  };

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">질문 등록</h2>
      <QuestionForm onSubmit={handleCreate} />
    </div>
  );
};

export default QuestionCreatePage;
