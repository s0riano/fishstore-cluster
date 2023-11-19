import { Test, TestingModule } from '@nestjs/testing';
import { VippsService } from './vipps.service';

describe('VippsService', () => {
  let service: VippsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [VippsService],
    }).compile();

    service = module.get<VippsService>(VippsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
