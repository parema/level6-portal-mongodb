import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Level6SharedModule } from 'app/shared';
import {
  ComputeGroupComponent,
  ComputeGroupDetailComponent,
  ComputeGroupUpdateComponent,
  ComputeGroupDeletePopupComponent,
  ComputeGroupDeleteDialogComponent,
  computeGroupRoute,
  computeGroupPopupRoute
} from './';

const ENTITY_STATES = [...computeGroupRoute, ...computeGroupPopupRoute];

@NgModule({
  imports: [Level6SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ComputeGroupComponent,
    ComputeGroupDetailComponent,
    ComputeGroupUpdateComponent,
    ComputeGroupDeleteDialogComponent,
    ComputeGroupDeletePopupComponent
  ],
  entryComponents: [
    ComputeGroupComponent,
    ComputeGroupUpdateComponent,
    ComputeGroupDeleteDialogComponent,
    ComputeGroupDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Level6ComputeGroupModule {}
