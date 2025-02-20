import { useState, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { StatusCodes } from "http-status-codes";
import SearchBar from "@/components/ui/SearchBar";
import Button from "@/components/ui/Button";
import QuestionTable from "@/components/question/QuestionTable";
import Pagination from "@/components/ui/Pagination";
import questionAPI from "@/api/questionAPI";
import { Question } from "@/types/question";

const QuestionListPage = () => {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(0);
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const initialQuery = searchParams.get("kw") || "";
    const page = parseInt(searchParams.get("page") || "0", 10);
    setSearchQuery(initialQuery);
    fetchQuestions(initialQuery, page);
  }, [searchParams]);

  const fetchQuestions = async (query: string, page: number) => {
    setLoading(true);
    setError(null);
    try {
      const response = query
        ? await questionAPI.searchQuestions(query, page)
        : await questionAPI.fetchQuestions(page);
      setQuestions(response.content);
      setTotalPages(response.totalPages);
      setCurrentPage(response.number);
    } catch (error: any) {
      const status = error.response?.status;
      if (
        status === StatusCodes.UNAUTHORIZED ||
        status === StatusCodes.FORBIDDEN
      ) {
        navigate("/login", { replace: true });
      } else {
        setError("질문 데이터를 불러오는 중 오류가 발생했습니다.");
      }
    } finally {
      setLoading(false);
    }
  };

  const toCreateForm = () => {
    try {
      navigate("/question/create");
    } catch (error: any) {
      const status = error.response?.status;
      if (
        status === StatusCodes.UNAUTHORIZED ||
        status === StatusCodes.FORBIDDEN
      ) {
        navigate("/login");
      }
    }
  };

  return (
    <div className="container mx-auto p-6 overflow-auto">
      <div className="flex justify-between mb-4">
        <Button text="질문 등록하기" onClick={toCreateForm} className="px-3" />
        <SearchBar
          value={searchQuery}
          onChange={setSearchQuery}
          onSearch={() => setSearchParams({ kw: searchQuery })}
        />
      </div>

      {error && <p className="text-red-500">{error}</p>}
      {loading ? (
        <p>불러오는 중...</p>
      ) : (
        <>
          <QuestionTable questions={questions} />
          <Pagination pageNumber={currentPage} totalPages={totalPages} />
        </>
      )}
    </div>
  );
};

export default QuestionListPage;
