import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LocalstorageManagerService} from '../localstorageManager/localstorage-manager.service';
import {Observable} from 'rxjs';
import {PersonInterface} from '../../interface/person-interface/person-interface';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private http = inject(HttpClient);
  private url = "http://localhost:8080/person";
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  getAllPersons(): Observable<PersonInterface[]>{
    return this.http.get<PersonInterface[]>(`${this.url}`,
      {headers: this.headers, withCredentials: true});
  }
}
