import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@nestjs/config';
import { PaymentController } from './payment/payment.controller';
import { PaymentService } from './payment/payment.service';
import { PaymentModule } from './payment/payment.module';
import {VippsService} from "./vipps/vipps.service";

@Module({
  imports: [ConfigModule.forRoot({
    isGlobal: true, // Make ConfigModule available throughout your app
  }),
    PaymentModule
  ],
  controllers: [AppController, PaymentController],
  providers: [AppService, VippsService, PaymentService],
})
export class AppModule {}
