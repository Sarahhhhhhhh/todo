import {ITodoResponse} from './todo.response';

export interface IListResponse {
  id?: number;
  user?: string;
  title?: string;
  todos?: ITodoResponse[];
  description?: string;
}
