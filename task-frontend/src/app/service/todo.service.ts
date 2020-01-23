
import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {IListResponse} from '../interface/list.response';
import {environment} from '../../environments/environment';
import {ITodoResponse} from '../interface/todo.response';

@Injectable({providedIn: 'root'})
export class TodoService {

  constructor(private http : HttpClient)  {}

  public getLists(): Promise<IListResponse[]> {
    return this.http.get(environment.backendUrl + 'lists').toPromise().then((resp: any) => {
      return resp;
    });
  }

  public createList(request: IListResponse): Promise<void> {
    return this.http.post(environment.backendUrl + 'lists', request).toPromise().then((resp: any) => {
      return;
    });
  }

  public getTasks(listId: number): Promise<ITodoResponse[]> {
    return this.http.get(environment.backendUrl + 'lists/' + listId + '/tasks').toPromise().then((resp: any) => {
      return resp;
    });
  }

  public createTask(listId: number, request: ITodoResponse): Promise<void> {
    return this.http.post(environment.backendUrl + 'lists/' + listId + '/tasks', request).toPromise().then((resp: any) => {
      return;
    });
  }

  public deleteTodo(listId: number, taskId: number): Promise<void> {
    return this.http.delete(environment.backendUrl + 'lists/' + listId + '/tasks/' + taskId).toPromise().then((resp: any) => {
      return;
    });
  }

  public updateList(listId: string, todoList: IListResponse): Promise<void> {
    return this.http.put(environment.backendUrl + 'lists/' + listId, todoList).toPromise().then((resp: any) => {
      return;
    });
  }

  public deleteList(listId: string): Promise<void> {
    return this.http.delete(environment.backendUrl + 'lists/' + listId).toPromise().then((resp: any) => {
      return;
    });
  }

  public updateTodo(listId: string, todo: ITodoResponse): Promise<void> {
    return this.http.put(environment.backendUrl + 'lists/' + listId + '/tasks/' + todo.taskID, todo).toPromise().then((resp: any) => {
      return;
    });
  }
}
