interface ButtonProps {
  text: string;
  onClick?: () => void;
  type?: "button" | "submit";
  fullWidth?: boolean;
  className?: string;
  disabled?: boolean;
}

const Button: React.FC<ButtonProps> = ({
  text,
  onClick,
  type = "button",
  fullWidth = false,
  className = "",
  disabled = false,
}) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`p-2 rounded-lg font-semibold bg-gray-400 text-white hover:bg-gray-600 transition duration-200 
        ${fullWidth ? "w-full" : "w-auto"} ${className}`}
      disabled={disabled}
    >
      {text}
    </button>
  );
};

export default Button;
