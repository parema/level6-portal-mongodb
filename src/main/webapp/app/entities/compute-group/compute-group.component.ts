import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComputeGroup } from 'app/shared/model/compute-group.model';
import { AccountService } from 'app/core';
import { ComputeGroupService } from './compute-group.service';

@Component({
  selector: 'jhi-compute-group',
  templateUrl: './compute-group.component.html'
})
export class ComputeGroupComponent implements OnInit, OnDestroy {
  computeGroups: IComputeGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected computeGroupService: ComputeGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.computeGroupService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IComputeGroup[]>) => res.ok),
          map((res: HttpResponse<IComputeGroup[]>) => res.body)
        )
        .subscribe((res: IComputeGroup[]) => (this.computeGroups = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.computeGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<IComputeGroup[]>) => res.ok),
        map((res: HttpResponse<IComputeGroup[]>) => res.body)
      )
      .subscribe(
        (res: IComputeGroup[]) => {
          this.computeGroups = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInComputeGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComputeGroup) {
    return item.id;
  }

  registerChangeInComputeGroups() {
    this.eventSubscriber = this.eventManager.subscribe('computeGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
