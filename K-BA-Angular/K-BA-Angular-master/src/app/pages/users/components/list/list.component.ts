import {Component} from '@angular/core';
import {UsersService} from '../../users.service';

@Component({
  selector: 'users-list',
  templateUrl: './list.html',
})

export class List {

  users:any;

  constructor(private _usersService: UsersService) {
  }

  // TODO angular-async-local-storage

  ngOnInit() {
    this._usersService.getAll()
     .subscribe(
        data => {
            this.users = data;
        },
        error => {
            console.log(error);
        });
	}

 

}
