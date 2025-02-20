import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import SignupPage from "@/pages/SignupPage";
import QuestionListPage from "@/pages/QuestionListPage";
import { AuthProvider } from "@/context/AuthContext";
import Layout from "@/components/layout/Layout";
import QuestionDetailPage from "@/pages/QuestionDetailPage";
import QuestionCreatePage from "./pages/QuestionCreatePage";
import QuestionEditPage from "./pages/QuestionEditPage";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Layout>
          <Routes>
            <Route path="/" element={<QuestionListPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path="/question/create" element={<QuestionCreatePage />} />
            <Route path="/question/list" element={<QuestionListPage />} />
            <Route path="/question/:id" element={<QuestionDetailPage />} />{" "}
            <Route path="/question/:id/edit" element={<QuestionEditPage />} />{" "}
          </Routes>
        </Layout>
      </Router>
    </AuthProvider>
  );
}

export default App;
