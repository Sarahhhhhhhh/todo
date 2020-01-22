import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material';

import {MatSort} from '@angular/material/sort';
import {UserService} from '../service/user.service';
import {IUserResponse} from '../interface/user.response';

@Component({
  selector: 'register-app-root',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  title = 'task-frontend';
  user: IUserResponse = {};
  constructor(private userService: UserService) {}

  async ngOnInit() {

  }

  async register(){
    try{
      await this.userService.register(this.user);
    }catch(err){
      alert("Error creating user");
    }
  }
}
