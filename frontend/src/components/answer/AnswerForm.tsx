import { useState } from "react";
import answerAPI from "@/api/answerAPI";
import { useParams } from "react-router-dom";
import InputField from "@/components/ui/InputField";
import Button from "@/components/ui/Button";

const AnswerForm = () => {
  const { id } = useParams<{ id: string }>();
  const [content, setContent] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;

    try {
      await answerAPI.createAnswer(Number(id), content);
      window.location.reload();
    } catch (error) {
      console.error("답변 생성 중 오류 발생:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-4 rounded-md shadow">
      <InputField
        label="답변"
        type="text"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        fullWidth
      />
      <Button text="답변 등록" type="submit" fullWidth className="mt-2" />
    </form>
  );
};

export default AnswerForm;
