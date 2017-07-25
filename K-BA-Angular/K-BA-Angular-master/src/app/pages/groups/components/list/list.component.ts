import {Component} from '@angular/core';
import {GroupsService} from '../../groups.service';
import {Router} from '@angular/router';


@Component({
  selector: 'groups-list',
  templateUrl: './list.html',
})

export class List {

  groups:any;

  constructor(private router: Router,
    private _groupsService: GroupsService) {
  }

  ngOnInit() {
    this._groupsService.getAll()
      .subscribe(
        data => {
          this.groups = data;
        },
        error => {
            console.log(error);
        });
  }

}
