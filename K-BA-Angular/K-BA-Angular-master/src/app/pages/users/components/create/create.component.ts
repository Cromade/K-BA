import {Component} from '@angular/core';
import {UsersService} from '../../users.service';
import {User} from '../../_models/user';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'users-create',
    templateUrl: './create.html',
})

export class Create {

  user = new User();
  userId:any;

  constructor(
    private _usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this._usersService.create(this.user)
      .subscribe(
        data => {
            this.router.navigateByUrl('/pages/users');
        },
        error => {
            console.log(error);
        });
  }

}
