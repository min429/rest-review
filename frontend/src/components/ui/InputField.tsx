interface InputFieldProps {
  label: string;
  type?: "text" | "email" | "password";
  id?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  fullWidth?: boolean;
  className?: string;
}

const InputField: React.FC<InputFieldProps> = ({
  label,
  type = "text",
  id,
  value,
  onChange,
  placeholder = "",
  fullWidth = false,
  className = "",
}) => {
  return (
    <div className={`mb-4 ${fullWidth ? "w-full" : "w-auto"} ${className}`}>
      <label className="block text-gray-700 font-medium mb-1" htmlFor={id}>
        {label}
      </label>
      <input
        type={type}
        id={id}
        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required
      />
    </div>
  );
};

export default InputField;
