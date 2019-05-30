import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComputeGroup } from 'app/shared/model/compute-group.model';
import { ComputeGroupService } from './compute-group.service';

@Component({
  selector: 'jhi-compute-group-delete-dialog',
  templateUrl: './compute-group-delete-dialog.component.html'
})
export class ComputeGroupDeleteDialogComponent {
  computeGroup: IComputeGroup;

  constructor(
    protected computeGroupService: ComputeGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.computeGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'computeGroupListModification',
        content: 'Deleted an computeGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compute-group-delete-popup',
  template: ''
})
export class ComputeGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ computeGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComputeGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.computeGroup = computeGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compute-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compute-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
