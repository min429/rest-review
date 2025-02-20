import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import authAPI from "@/api/authAPI";
import { AuthContext } from "@/context/AuthContext";
import LoginForm from "@/components/auth/LoginForm";

const LoginPage = () => {
  const navigate = useNavigate();
  const auth = useContext(AuthContext);
  const [error, setError] = useState("");

  const handleLogin = async (email: string, password: string) => {
    try {
      await authAPI.login(email, password);
      auth?.setUser({ email });
      navigate("/question/list");
    } catch {
      setError("사용자 ID 또는 비밀번호를 확인해 주세요.");
    }
  };

  return (
    <div className="w-full h-screen flex items-center justify-center bg-gray-100">
      <LoginForm onLogin={handleLogin} error={error} />
    </div>
  );
};

export default LoginPage;
