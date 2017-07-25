import {Component} from '@angular/core';
import {UsersService} from '../../users.service';
import {User} from '../../_models/user';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'users-edit',
    templateUrl: './edit.html',
})

export class Edit {

  user = new User();
  userId:any;

  constructor(
    private _usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
      this._usersService.getOne()
        .then(
          data => {
              this.user = data;
      });
    });
	}

	onSubmit() {
		this._usersService.update(this.user)
    	.subscribe(
        data => {
            this.router.navigateByUrl('/pages/users');
        },
        error => {
            console.log(error);
        });
	}

}
