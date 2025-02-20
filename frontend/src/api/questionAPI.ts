import axiosInstance from "../lib/axiosInstance";
import { Question } from "@/types/question";

const questionAPI = {
  fetchQuestions: async (page = 0) => {
    const { data } = await axiosInstance.get<{
      content: Question[];
      totalPages: number;
      number: number;
    }>(`/question/list?page=${page}`);
    return data;
  },

  searchQuestions: async (query: string, page = 0) => {
    const { data } = await axiosInstance.get<{
      content: Question[];
      totalPages: number;
      number: number;
    }>(`/question/list?kw=${query}&page=${page}`);
    return data;
  },

  fetchQuestionDetail: async (id: number) => {
    const { data } = await axiosInstance.get<Question>(
      `/question/detail/${id}`
    );
    return data;
  },

  createQuestion: async (subject: string, content: string) => {
    return axiosInstance.post("/question/create", { subject, content });
  },

  modifyQuestion: async (id: number, subject: string, content: string) => {
    return axiosInstance.post(`/question/modify/${id}`, { subject, content });
  },

  deleteQuestion: async (id: number) => {
    return axiosInstance.delete(`/question/delete/${id}`);
  },

  voteQuestion: async (id: number) => {
    return axiosInstance.post(`/question/vote/${id}`);
  },
};

export default questionAPI;
