import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IComputeGroup, ComputeGroup } from 'app/shared/model/compute-group.model';
import { ComputeGroupService } from './compute-group.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project';

@Component({
  selector: 'jhi-compute-group-update',
  templateUrl: './compute-group-update.component.html'
})
export class ComputeGroupUpdateComponent implements OnInit {
  computeGroup: IComputeGroup;
  isSaving: boolean;

  projects: IProject[];

  editForm = this.fb.group({
    id: [],
    computeGroupName: [],
    parent: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected computeGroupService: ComputeGroupService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ computeGroup }) => {
      this.updateForm(computeGroup);
      this.computeGroup = computeGroup;
    });
    this.projectService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProject[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProject[]>) => response.body)
      )
      .subscribe((res: IProject[]) => (this.projects = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(computeGroup: IComputeGroup) {
    this.editForm.patchValue({
      id: computeGroup.id,
      computeGroupName: computeGroup.computeGroupName,
      parent: computeGroup.parent
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const computeGroup = this.createFromForm();
    if (computeGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.computeGroupService.update(computeGroup));
    } else {
      this.subscribeToSaveResponse(this.computeGroupService.create(computeGroup));
    }
  }

  private createFromForm(): IComputeGroup {
    const entity = {
      ...new ComputeGroup(),
      id: this.editForm.get(['id']).value,
      computeGroupName: this.editForm.get(['computeGroupName']).value,
      parent: this.editForm.get(['parent']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComputeGroup>>) {
    result.subscribe((res: HttpResponse<IComputeGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProjectById(index: number, item: IProject) {
    return item.id;
  }
}
