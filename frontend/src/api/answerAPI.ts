import { Answer } from "@/types/answer";
import axiosInstance from "@/lib/axiosInstance";

const answerAPI = {
  createAnswer: async (
    questionId: number,
    content: string
  ): Promise<Answer> => {
    const { data } = await axiosInstance.post<Answer>(`/answer/create`, {
      questionId,
      content,
    });
    return data;
  },

  modifyAnswer: async (answerId: number, content: string) => {
    return axiosInstance.post(`/answer/modify/${answerId}`, { content });
  },

  deleteAnswer: async (answerId: number) => {
    return axiosInstance.delete(`/answer/delete/${answerId}`);
  },

  voteAnswer: async (answerId: number) => {
    return axiosInstance.post(`/answer/vote/${answerId}`);
  },
};

export default answerAPI;
