import {Component} from '@angular/core';
import {ProductsService} from '../../products.service';
import {Product} from '../../_models/product';
import {Category} from '../../_models/category';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'products-create',
    templateUrl: './create.html',
})

export class Create {

  category:any;
  product = new Product();

  constructor(
    private _productsService: ProductsService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
     this._productsService.getCategoryData()
        .subscribe(
        data => {
            this.category = data;
        },
        error => {
            console.log(error);
        });
	}

	onSubmit() {
		this._productsService.create(this.product)
    	.subscribe(
        data => {
            this.router.navigateByUrl('/pages/products');
        },
        error => {
            console.log(error);
        });
	}

}
