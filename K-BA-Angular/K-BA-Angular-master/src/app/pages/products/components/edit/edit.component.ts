import {Component} from '@angular/core';
import {ProductsService} from '../../products.service';
import {Product} from '../../_models/product';
import {Category} from '../../_models/category';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'products-edit',
    templateUrl: './edit.html',
})

export class Edit {

  product = new Product();
  category:any;
  productId:any;

  constructor(
    private _productsService: ProductsService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.productId = params['id'];
      this._productsService.getById(this.productId)
      .subscribe(
        data => {
            this.product = data;
        },
        error => {
            console.log(error);
      });
      this._productsService.getCategoryData()
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
		this._productsService.update(this.product)
    	.subscribe(
        data => {
            this.router.navigateByUrl('/pages/products');
        },
        error => {
            console.log(error);
        });
	}

}
