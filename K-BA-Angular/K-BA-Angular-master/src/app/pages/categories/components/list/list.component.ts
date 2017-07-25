import {Component} from '@angular/core';
import {CategoriesService} from '../../categories.service';
import {Router} from '@angular/router';


@Component({
  selector: 'categories-list',
  templateUrl: './list.html',
})

export class List {

  servers:any;
  categories:any;

  constructor(private router: Router,
    private _categoriesService: CategoriesService) {
  }

  // TODO angular-async-local-storage

  ngOnInit() {
    this._categoriesService.getAll()
      .subscribe(
        data => {
          this.categories = data;
        },
        error => {
            console.log(error);
        });
  }

  delete(product_uid) {
    this._categoriesService.delete(product_uid)
      .subscribe(
        data => {
            this.router.navigateByUrl('/pages/categories');
        },
        error => {
            console.log(error);
        });
  }

}
