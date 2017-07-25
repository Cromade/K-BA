import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { User } from './_models/index';

@Injectable()
export class UsersService {
    constructor(private http: Http) { }


    usersData = [{
        'uid': '23554654235325',
        'firstname': 'Firstname 1',
        'lastname': 'Lastname 1',
        'email': 'email1@test.fr',
        'pseudo': "Pseudo 1",
        'birthdate': 2017-12-13,
        'created_at': 2017-12-13,
        'updated_at': 2017-12-13,
        'deleted_at': 2017-12-13
    },{
        'uid': '23554654235325',
        'firstname': 'Firstname 2',
        'lastname': 'Lastname 2',
        'email': 'email2@test.fr',
        'pseudo': "Pseudo 2",
        'birthdate': 2017-12-13,
        'created_at': 2017-12-13,
        'updated_at': 2017-12-13,
        'deleted_at': 2017-12-13
    },{
        'uid': '23554654235325',
        'firstname': 'Firstname 3',
        'lastname': 'Lastname 3',
        'email': 'email3@test.fr',
        'pseudo': "Pseudo 3",
        'birthdate': 2017-12-13,
        'created_at': 2017-12-13,
        'updated_at': 2017-12-13,
        'deleted_at': 2017-12-13
    }];

    getData(): Promise<any> {
        return new Promise((resolve, reject) => {
          setTimeout(() => {
            resolve(this.usersData);
          }, 1);
        });
    }

    getOne(): Promise<any> {
        return new Promise((resolve, reject) => {
          setTimeout(() => {
            resolve(this.usersData[0]);
          }, 1);
        });
    }


    getAll() {
        return this.http.get('http://51.255.196.182:3000/user', this.jwt()).map((response: Response) => response.json());
    }

    getById(id: number) {
        return this.http.get('http://51.255.196.182:3000/user/' + id, this.jwt()).map((response: Response) => response.json());
    }

    refresh(id: string) {
        return this.http.get('http://localhost:4040/api/servers/refresh/' + id, this.jwt()).map((response: Response) => response.json());
    }

    version(id: string) {
        return this.http.get('http://localhost:4040/api/servers/version/' + id, this.jwt()).map((response: Response) => response.json());
    }

    create(user: User) {
        return this.http.post('http://localhost:4040/api/servers', user, this.jwt()).map((response: Response) => response.json());
    }

    update(user: User) {
        return this.http.put('http://51.255.196.182:3000/user/' + user.uid, user, this.jwt()).map((response: Response) => response.json());
    }

    delete(id: number) {
        return this.http.delete('/api/servers/' + id, this.jwt()).map((response: Response) => response.json());
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
