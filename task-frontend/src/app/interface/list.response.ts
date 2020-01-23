import {ITodoResponse} from './todo.response';

export interface IListResponse {
  id?: string;
  user?: string;
  title?: string;
  todos?: ITodoResponse[];
  description?: string;
}
