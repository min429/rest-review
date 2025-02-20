import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authAPI from "@/api/authAPI";
import InputField from "@/components/ui/InputField";
import Button from "@/components/ui/Button";

const SignupForm = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await authAPI.signup(email, password);
      navigate("/login");
    } catch (err) {
      setError("회원가입에 실패했습니다.");
    }
  };

  return (
    <div className="w-full h-full flex items-center justify-center bg-gray-100">
      <div className="max-w-[400px] w-full bg-white shadow-2xl rounded-xl p-8">
        <h2 className="text-3xl font-bold text-center text-gray-800">
          회원가입
        </h2>
        <form onSubmit={handleSubmit} className="mt-6 w-full">
          <InputField
            label="이메일"
            type="text"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="이메일을 입력하세요"
            fullWidth
          />
          <InputField
            label="비밀번호"
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호를 입력하세요"
            fullWidth
          />
          <Button text="회원가입" type="submit" fullWidth />
        </form>
        {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
      </div>
    </div>
  );
};

export default SignupForm;
