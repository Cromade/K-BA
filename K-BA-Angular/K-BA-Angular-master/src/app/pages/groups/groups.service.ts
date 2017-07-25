import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { Server } from './_models/index';
import { Product } from './_models/index';

@Injectable()
export class GroupsService {
    constructor(private http: Http) { }


    getAll() {
        return this.http.get('http://51.255.196.182:3000/group', this.jwt()).map((response: Response) => response.json());
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