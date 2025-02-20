import { User } from "./user";

export interface Answer {
  id: number;
  content: string;
  createDate: string;
  modifyDate?: string;
  author: User;
  voters: User[];
}
