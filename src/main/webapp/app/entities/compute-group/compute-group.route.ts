import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ComputeGroup } from 'app/shared/model/compute-group.model';
import { ComputeGroupService } from './compute-group.service';
import { ComputeGroupComponent } from './compute-group.component';
import { ComputeGroupDetailComponent } from './compute-group-detail.component';
import { ComputeGroupUpdateComponent } from './compute-group-update.component';
import { ComputeGroupDeletePopupComponent } from './compute-group-delete-dialog.component';
import { IComputeGroup } from 'app/shared/model/compute-group.model';

@Injectable({ providedIn: 'root' })
export class ComputeGroupResolve implements Resolve<IComputeGroup> {
  constructor(private service: ComputeGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComputeGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ComputeGroup>) => response.ok),
        map((computeGroup: HttpResponse<ComputeGroup>) => computeGroup.body)
      );
    }
    return of(new ComputeGroup());
  }
}

export const computeGroupRoute: Routes = [
  {
    path: '',
    component: ComputeGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ComputeGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComputeGroupDetailComponent,
    resolve: {
      computeGroup: ComputeGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ComputeGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComputeGroupUpdateComponent,
    resolve: {
      computeGroup: ComputeGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ComputeGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComputeGroupUpdateComponent,
    resolve: {
      computeGroup: ComputeGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ComputeGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const computeGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComputeGroupDeletePopupComponent,
    resolve: {
      computeGroup: ComputeGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ComputeGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
