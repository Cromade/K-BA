import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { Server } from './_models/index';
import { Product } from './_models/index';

@Injectable()
export class ProductsService {
    constructor(private http: Http) { }

    getAll() {
        return this.http.get('http://51.255.196.182:3000/item', this.jwt()).map((response: Response) => response.json());
    }

    getById(id: number) {
        return this.http.get('http://51.255.196.182:3000/item/' + id, this.jwt()).map((response: Response) => response.json());
    }

    getCategories(id: number) {
        return this.http.get('http://51.255.196.182:3000/item/category/' + id, this.jwt()).map((response: Response) => response.json());
    }

    getCategoryData() {
        return this.http.get('http://51.255.196.182:3000/category', this.jwt()).map((response: Response) => response.json());
    }

    addCategory(itemId: number, categoryId: number) {
        return this.http.put('http://51.255.196.182:3000/item/' + itemId + '/category/' + categoryId, this.jwt()).map((response: Response) => response.json());
    }

    create(product: Product) {
        return this.http.post('http://51.255.196.182:3000/item', product, this.jwt()).map((response: Response) => response.json());
    }

    update(product: Product) {
        return this.http.put('http://51.255.196.182:3000/item/' + product.uid, product, this.jwt()).map((response: Response) => response.json());
    }

    delete(id: number) {
        return this.http.delete('http://51.255.196.182:3000/item/' + id, this.jwt()).map((response: Response) => response.json());
    }

    // private helper methods
    private jwt() {
        // create authorization header with jwt token
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new RequestOptions({ headers: headers });
        }
    }
}