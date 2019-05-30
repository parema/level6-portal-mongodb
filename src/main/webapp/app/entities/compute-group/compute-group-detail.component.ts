import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComputeGroup } from 'app/shared/model/compute-group.model';

@Component({
  selector: 'jhi-compute-group-detail',
  templateUrl: './compute-group-detail.component.html'
})
export class ComputeGroupDetailComponent implements OnInit {
  computeGroup: IComputeGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ computeGroup }) => {
      this.computeGroup = computeGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
