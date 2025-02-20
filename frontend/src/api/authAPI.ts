import axiosInstance from "@/lib/axiosInstance";

const authAPI = {
  login: async (email: string, password: string) => {
    return axiosInstance.post("/auth/login", { email, password });
  },
  signup: async (email: string, password: string) => {
    return axiosInstance.post("/auth/signup", { email, password });
  },
  logout: async () => {
    return axiosInstance.post("/auth/logout", {}, { withCredentials: true });
  },
};

export default authAPI;
