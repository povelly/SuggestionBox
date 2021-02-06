import { TestBed } from '@angular/core/testing';

import { GuardeGuard } from './guarde.guard';

describe('GuardeGuard', () => {
  let guard: GuardeGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GuardeGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
