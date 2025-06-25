import {Injectable, inject, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {LocalstorageManagerService} from "../localstorageManager/localstorage-manager.service";
import {UserInterface} from '../../interface/user-interface/user-interface';

interface LoginResponse {
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private url = "http://localhost:8080/api/auth";
  private localstorageManagerService = inject(LocalstorageManagerService);
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:4200',
    'Access-Control-Allow-Methods':'POST,PATCH,OPTIONS'
  });
  public isConnected = signal(false);

  createUser(object: UserInterface) {
    return this.http.post(`${this.url}/register`,
      object,
      {headers: this.headers});
  }

  loginUser(object: UserInterface) {
    return this.http.post<LoginResponse>(
      `${this.url}/login`,
      object,
      {headers: this.headers, withCredentials: true}
    ).subscribe({
      next: (response: LoginResponse) => {
        this.localstorageManagerService.addUserId(response.userId);
        this.isConnected.set(true);
      },
      error: (error) => {
        console.error('Login failed:', error);
      }
    });
  }

}
