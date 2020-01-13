
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
}
