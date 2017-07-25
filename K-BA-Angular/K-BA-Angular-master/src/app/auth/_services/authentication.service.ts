import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
    constructor(private http: Http) { }

    login(username: string, password: string) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post('http://51.255.196.182:3000/auth/login', JSON.stringify({ email: username, password: password }), { headers: headers })
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                let user = response.json();
                if (user && response.status == 200 && response.headers.get('token')) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    user.token = response.headers.get('token');
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}