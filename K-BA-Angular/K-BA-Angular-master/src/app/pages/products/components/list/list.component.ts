import {Component} from '@angular/core';
import {ProductsService} from '../../products.service';
import {Router} from '@angular/router';


@Component({
  selector: 'products-list',
  templateUrl: './list.html',
})

export class List {

  servers:any;
  products:any;

  constructor(private router: Router,
    private _serversService: ProductsService) {
  }

  // TODO angular-async-local-storage

  ngOnInit() {
    this._serversService.getAll()
      .subscribe(
        data => {
            this.products = data;
        },
        error => {
            console.log(error);
        });
  }

  delete(product_uid) {
    this._serversService.delete(product_uid)
      .subscribe(
        data => {
            this.router.navigateByUrl('/pages/products');
        },
        error => {
            console.log(error);
        });
  }

}
