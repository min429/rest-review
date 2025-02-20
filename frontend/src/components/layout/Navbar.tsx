import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "@/context/AuthContext";
import authAPI from "@/api/authAPI";

const Navbar = () => {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await authAPI.logout();
      auth?.setUser(null);
      navigate("/login");
    } catch (error) {
      console.error("로그아웃 실패:", error);
    }
  };

  return (
    <nav className="bg-white shadow-md py-4 px-6 flex justify-between items-center sticky top-0 w-full z-50">
      <Link to="/question/list" className="text-xl font-bold text-black">
        SBB
      </Link>
      <div className="flex space-x-4">
        {auth?.user ? (
          <button onClick={handleLogout} className="text-gray-700">
            로그아웃
          </button>
        ) : (
          <Link to="/login" className="text-gray-700">
            로그인
          </Link>
        )}
        <Link to="/question/list" className="text-gray-700">
          질문 목록
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
