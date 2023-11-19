import { Injectable } from '@nestjs/common';

@Injectable()
export class PaymentService {
  private fakeTransactions = [
    { id: 1, amount: 100 },
    { id: 2, amount: 200 },
  ];

  getTransactions() {
    return this.fakeTransactions;
  }
}
