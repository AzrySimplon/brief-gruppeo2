import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LocalstorageManagerService} from '../localstorageManager/localstorage-manager.service';
import {Observable, tap} from 'rxjs';
import {ListInterface} from '../../interface/list-interface/list-interface';

@Injectable({
  providedIn: 'root'
})
export class ListService {
  private http = inject(HttpClient);
  private url = "http://localhost:8080/person-list";
  private localstorageManagerService = inject(LocalstorageManagerService);
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:4200',
    'Access-Control-Allow-Methods':'POST,PATCH,OPTIONS'
  });

  getListByUserId(): Observable<ListInterface[]> {
    // @ts-ignore
    return this.http.get(`${this.url}/${this.localstorageManagerService.getUserId()}`,
      {headers: this.headers, withCredentials: true});
  }

}
