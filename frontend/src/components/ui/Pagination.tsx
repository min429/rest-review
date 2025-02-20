import { useNavigate, useSearchParams } from "react-router-dom";

interface PaginationProps {
  pageNumber: number;
  totalPages: number;
}

const Pagination = ({ pageNumber, totalPages }: PaginationProps) => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const goToPage = (page: number) => {
    searchParams.set("page", page.toString());
    navigate(`?${searchParams.toString()}`);
  };

  return (
    <div className="flex justify-center">
      <div className="inline-flex border border-gray-300 rounded-md mt-4">
        <button
          onClick={() => goToPage(pageNumber - 1)}
          disabled={pageNumber === 0}
          className={`px-3 py-1 border-r border-gray-300 rounded-l-md ${
            pageNumber === 0
              ? "bg-gray-400 cursor-not-allowed"
              : "bg-white hover:bg-gray-100"
          }`}
        >
          이전
        </button>

        {Array.from({ length: totalPages }).map((_, index) => (
          <button
            key={index}
            onClick={() => goToPage(index)}
            className={`px-3 py-1 border-r border-gray-300 ${
              pageNumber === index
                ? "bg-gray-300 text-white"
                : "bg-white hover:bg-gray-100"
            }`}
          >
            {index}
          </button>
        ))}

        <button
          onClick={() => goToPage(pageNumber + 1)}
          disabled={pageNumber === totalPages - 1}
          className={`px-3 py-1 rounded-r-md ${
            pageNumber === totalPages - 1
              ? "bg-gray-400 cursor-not-allowed"
              : "bg-white hover:bg-gray-100"
          }`}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default Pagination;
