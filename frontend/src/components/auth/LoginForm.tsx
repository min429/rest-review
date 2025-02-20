import { useState } from "react";
import InputField from "@/components/ui/InputField";
import Button from "@/components/ui/Button";

type LoginFormProps = {
  onLogin: (email: string, password: string) => Promise<void>;
  error: string;
};

const LoginForm = ({ onLogin, error }: LoginFormProps) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onLogin(email, password);
  };

  return (
    <div className="w-full max-w-md bg-white shadow-2xl rounded-xl p-8">
      <h2 className="text-3xl font-bold text-center text-gray-800">로그인</h2>
      <form onSubmit={handleSubmit} className="mt-6 w-full">
        <InputField
          label="사용자 ID"
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
        <Button text="로그인" type="submit" fullWidth />
      </form>
      {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
    </div>
  );
};

export default LoginForm;
