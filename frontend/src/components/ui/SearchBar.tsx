import Button from "@/components/ui/Button";

interface SearchBarProps {
  value: string;
  onChange: (value: string) => void;
  onSearch: () => void;
}

const SearchBar = ({ value, onChange, onSearch }: SearchBarProps) => {
  return (
    <div className="flex gap-2">
      <input
        type="text"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder="검색어 입력"
        className="border px-3 py-2 rounded border-gray-300"
      />
      <Button
        text="찾기"
        onClick={onSearch}
        className="bg-gray-500 text-white px-4 py-2 rounded"
      />
    </div>
  );
};

export default SearchBar;
