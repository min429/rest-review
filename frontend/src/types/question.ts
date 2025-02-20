import { User } from "./user";
import { Answer } from "./answer";

export interface Question {
  id: number;
  subject: string;
  content: string;
  createDate: string;
  modifyDate?: string;
  author: User;
  answers: Answer[];
  voters: User[];
}
