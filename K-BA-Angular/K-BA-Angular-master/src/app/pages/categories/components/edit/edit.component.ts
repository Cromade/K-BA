import {Component} from '@angular/core';
import {CategoriesService} from '../../categories.service';
import {Category} from '../../_models/category';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'categories-edit',
    templateUrl: './edit.html',
})

export class Edit {

  category = new Category();
  productId:any;

  constructor(
    private _categoriesService: CategoriesService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.productId = params['id'];
      this._categoriesService.getById(this.productId)
      .subscribe(
        data => {
            this.category = data;
        },
        error => {
            console.log(error);
      });
      this._categoriesService.getById(this.productId)
        .subscribe(
          data => {
              this.category = data;
          },
          error => {
              console.log(error);
      });
    });
	}

	onSubmit() {
		this._categoriesService.update(this.category)
    	.subscribe(
        data => {
            this.router.navigateByUrl('/pages/categories');
        },
        error => {
            console.log(error);
        });
	}

}
