import { TestBed } from '@angular/core/testing';

import { LocalstorageManagerService } from './localstorage-manager.service';

describe('LocalstorageManagerService', () => {
  let service: LocalstorageManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocalstorageManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
