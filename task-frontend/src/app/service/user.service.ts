
import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {IListResponse} from '../interface/list.response';
import {environment} from '../../environments/environment';
import {ITodoResponse} from '../interface/todo.response';
import {ILoginRequest} from '../interface/login.request';

@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http : HttpClient)  {}

  public login(request: ILoginRequest): Promise<void> {
    return this.http.post(environment.userService + 'login', request).toPromise().then((resp: any) => {
      return;
    });
  }
}
