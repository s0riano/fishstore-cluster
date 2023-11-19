import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import Vipps from '@vippsno/vipps-sdk';

@Injectable()
export class VippsService {
  private vippsClient: Vipps;
  private _orderId: string;

  constructor(private configService: ConfigService) {
    this.vippsClient = new Vipps({
      pluginName: '',
      pluginVersion: '',
      subscriptionKey: '',
      useTestMode: false,
      clientId: this.configService.get<string>('VIPPS_CLIENT_ID'),
      clientSecret: this.configService.get<string>('VIPPS_CLIENT_SECRET'),
      merchantSerialNumber: this.configService.get<string>(
        'VIPPS_MERCHANT_SERIAL_NUMBER',
      ),
      // Add other necessary configurations here
    });
  }

  // Implement methods to interact with Vipps here
  private _amount: number;
  async createPaymentSession(amount: number, orderId: string) {
    this._amount = amount;
    this._orderId = orderId;
  }
}
