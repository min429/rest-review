import { ReactNode } from "react";
import Navbar from "./Navbar";

const Layout = ({ children }: { children: ReactNode }) => {
  return (
    <div className="min-h-screen w-full flex flex-col bg-gray-100">
      <Navbar />
      <main className="flex-1 p-6 mt-16">{children}</main>
    </div>
  );
};

export default Layout;
