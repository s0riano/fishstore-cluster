import { Component } from '@angular/core';
import {UntilDestroy} from "@ngneat/until-destroy";
import {ApiClient} from "../../shared/services/api-client/api-client";

/*@UntilDestroy*/
@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.scss']
})
export class StatsComponent {
  isLoading: boolean;
  customerCount: number;
  userCount: number;

  constructor(private apiClient: ApiClient) {}

  async ngOnInit(): Promise<void> {
    try {
      this.isLoading = true;
      let customerStats = await this.apiClient.get<any>(
        `/api/store`
      );

    } finally {
      this.isLoading = false;
    }
  }

  ngOnDestroy(): void {}
}
