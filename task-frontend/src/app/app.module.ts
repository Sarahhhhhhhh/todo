import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import {AppComponent} from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule, MatInputModule, MatTableModule} from '@angular/material';
import {TodoService} from './service/todo.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {UserService} from './service/user.service';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSortModule} from '@angular/material/sort';
import {RegisterComponent} from './register/register.component';
import {DeleteTodoDialog, MainComponent, TodoDialog, UpdateListDialog} from './main/main.component';

@NgModule({
  declarations: [
    AppComponent,
    TodoDialog,
    DeleteTodoDialog,
    UpdateListDialog,
    RegisterComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatDialogModule,
    MatSortModule,
  ],
  providers: [TodoService, UserService],
  bootstrap: [AppComponent],
  entryComponents: [TodoDialog, DeleteTodoDialog, UpdateListDialog]
})
export class AppModule { }
