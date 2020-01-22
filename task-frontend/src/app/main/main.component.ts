import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material';

import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {MatSort} from '@angular/material/sort';
import {IListResponse} from '../interface/list.response';
import {TodoService} from '../service/todo.service';
import {UserService} from '../service/user.service';
import {ITodoResponse} from '../interface/todo.response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  title = 'task-frontend';
  listDataSource: MatTableDataSource<IListResponse>;
  listDisplayedColumns: string[] = ['id', 'user', 'title', 'todos', 'description', 'action'];
  listElm: IListResponse = {};
  loggedInUser: string;
  deleteListId: number;
  username: string;
  password: string;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  constructor(private todoService: TodoService, private userService: UserService, public dialog: MatDialog, private router: Router){

  }

  async ngOnInit() {
    this.loggedInUser = localStorage.getItem('user');
    await this.getLists();
  }

  async logout() {
    localStorage.removeItem('user');
    this.loggedInUser = false;
  }

  async getLists(){
    this.listDataSource = new MatTableDataSource(await this.todoService.getLists());
    this.listDataSource.sort = this.sort;
  }

  async createList(){
    this.listElm.user = this.loggedInUser;
    await this.todoService.createList(this.listElm);
    await this.getLists();
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
    return todos.map(todo => '[id:' + todo.taskID + ',text:' + todo.text + ']').join();
  }

  public openTodoDialog(todoToList){
      const dialogRef = this.dialog.open(TodoDialog, {
        width: '350px',
        data: {
          todoToList,
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      this.getLists();
    });
  }
  public openDeleteTodoDialog(todoToList){
    const dialogRef = this.dialog.open(DeleteTodoDialog, {
      width: '350px',
      data: {
        todoToList,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getLists();
    });
  }

  public openUpdateListDialog(todoToList){
    const dialogRef = this.dialog.open(UpdateListDialog, {
      width: '350px',
      data: todoToList,
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getLists();
    });
  }
}

@Component({
  selector: 'delete-todo-dialog',
  templateUrl: './delete-todo-dialog.html',
})
export class DeleteTodoDialog {
  deleteTodoId: number;

  constructor(
    public dialogRef: MatDialogRef<DeleteTodoDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any, private todoService: TodoService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  async deleteTodo(){
    try {
      await this.todoService.deleteTodo(this.data.todoToList, this.deleteTodoId);
      this.dialogRef.close();
    } catch(err) {
      alert("Could not delete task from list");
    }
  }
}

@Component({
  selector: 'update-list-dialog',
  templateUrl: './update-list-dialog.html',
})
export class UpdateListDialog {
  listElm: IListResponse = {};

  constructor(
    public dialogRef: MatDialogRef<UpdateListDialog>,
    @Inject(MAT_DIALOG_DATA) public data: IListResponse, private todoService: TodoService) {
      this.listElm = data;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  async updateList(){
    try{
      await this.todoService.updateList(this.listElm.id, this.listElm);
    }catch(err){
      alert('Could not update list');
    }
  }
}

@Component({
  selector: 'todo-dialog',
  templateUrl: './todo-dialog.html',
})
export class TodoDialog {
  todoElm: ITodoResponse = {};

  constructor(
    public dialogRef: MatDialogRef<TodoDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any, private todoService: TodoService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  async createTodo(){
    console.log(this.data);
    await this.todoService.createTask(this.data.todoToList, this.todoElm);
    this.dialogRef.close();
  }

}
