import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {IListResponse} from './interface/list.response';
import {ITodoResponse} from './interface/todo.response';
import {TodoService} from './service/todo.service';
import {UserService} from './service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'task-frontend';
  listDataSource: MatTableDataSource<IListResponse>;
  listDisplayedColumns: string[] = ['id', 'user', 'title', 'todos', 'description'];
  listElm: IListResponse = {};
  todoElm: ITodoResponse = {};
  todoToList: number;
  loggedInUser: string;
  username: string;
  password: string;
  constructor(private todoService: TodoService, private userService: UserService){

  }

  async ngOnInit(){
    this.loggedInUser = localStorage.getItem('user');
    this.listDataSource = new MatTableDataSource(await this.todoService.getLists());
  }

  async createList(){
    this.listElm.user = this.loggedInUser;
    await this.todoService.createList(this.listElm);
  }

  async createTodo(){
    await this.todoService.createTask(this.todoToList, this.todoElm);
  }

  async login(){
    try {
      await this.userService.login({
        email: this.username,
        password: this.password,
      });
      localStorage.setItem('user', this.username);
      this.loggedInUser = this.username;
    }catch(err){
      alert("Authentication failed. Wrong username or password");
    }
  }

  displayTodos(todos: ITodoResponse[]): string {
    return todos.map(todo => todo.text).join();
  }
}
