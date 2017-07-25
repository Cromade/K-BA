import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Category} from '../../_models/category';

import {ProductsService} from '../../products.service';
import {CategoriesService} from '../../../categories/categories.service';



@Component({
  selector: 'categories-list',
  templateUrl: './categories.html',
})

export class Categories {

  product:any;
  categories:any;
  allCategories:any;
  productId:any;
  categoryId:any;
  category :any;
  selectedCategory:any;

  constructor(private route: ActivatedRoute,
  private router: Router,
  private _productsService: ProductsService,
  private _categoriesService: CategoriesService) {
  }

  // TODO angular-async-local-storage

  ngOnInit() {
  this.route.params.subscribe(params => {
   this.productId = params['id'];
   //this.categoryId = params['category'];
    this._productsService.getById(this.productId)
     .subscribe(
        data => {
            this.categories = data.categories;
        },
        error => {
            console.log(error);
        });
    });
    this._categoriesService.getAll()
      .subscribe(
        data => {
          this.allCategories = data;
        },
        error => {
            console.log(error);
        });
	}

  onSubmit() {
    this._productsService.addCategory(this.productId, this.selectedCategory)
      .subscribe(
        data => {
            this.router.navigateByUrl('/pages/item/categories/'+this.productId);
        },
        error => {
            console.log(error);
        });
  }

 

}
