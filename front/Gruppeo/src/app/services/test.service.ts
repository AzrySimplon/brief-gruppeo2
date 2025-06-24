import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private http = inject(HttpClient);
  private url = "http://localhost:8080/api/auth";

  createUser(object:{ username: string, password: string}) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'http://localhost:4200',
      'Access-Control-Allow-Methods':'POST,PATCH,OPTIONS'
    });

    return this.http.post(`${this.url}/register`,
      {username: object.username, password: object.password},
      {headers: headers});
  }



  constructor() { }
}
