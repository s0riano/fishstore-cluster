import { Body, Controller, Injectable, Post } from '@nestjs/common';
import { VippsService } from '../vipps/vipps.service';
import { CreatePaymentDto } from '../dtos/CreatePaymentDto';


@Controller('payment')
export class PaymentController {
  constructor(
    private readonly vippsService: VippsService,
    private readonly paymentService: PaymentService,
  ) {}

  @Get('transactions')
  getTransactions() {
    return this.paymentService.getTransactions();
  }

  @Post('create')
  async createPayment(@Body() paymentDto: CreatePaymentDto) {
    // Extract necessary data from paymentDto, like amount and orderId
    return await this.vippsService.createPaymentSession(
      paymentDto.amount,
      paymentDto.orderId,
    );
  }

  @Post('process')
  async processPayment(@Body() paymentDto: PaymentDto) {
    return this.paymentService.processPayment(paymentDto);
  }

  // Additional endpoints for handling callbacks, status checks, etc.
}

@Injectable()
export class PaymentService {
  // Define methods like processPayment here
}
