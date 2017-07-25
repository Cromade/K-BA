import {Component} from '@angular/core';
import {CategoriesService} from '../../categories.service';
import {Category} from '../../_models/category';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'categories-create',
    templateUrl: './create.html',
})

export class Create {

  category = new Category();

  constructor(
    private _categoriesService: CategoriesService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
	}

	onSubmit() {
		this._categoriesService.create(this.category)
    	.subscribe(
        data => {
            this.router.navigateByUrl('/pages/categories');
        },
        error => {
            console.log(error);
        });
	}

}
