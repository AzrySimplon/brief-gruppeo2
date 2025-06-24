import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalstorageManagerService {
  private userIdStorageName: string = "userId";

  addUserId(id: number): void {
    localStorage.setItem(this.userIdStorageName, id.toString());
  }

  getUserId(): number {
    return parseInt(localStorage.getItem(this.userIdStorageName) || "0");
  }
}
