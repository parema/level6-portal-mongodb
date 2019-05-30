/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Level6TestModule } from '../../../test.module';
import { ComputeGroupDeleteDialogComponent } from 'app/entities/compute-group/compute-group-delete-dialog.component';
import { ComputeGroupService } from 'app/entities/compute-group/compute-group.service';

describe('Component Tests', () => {
  describe('ComputeGroup Management Delete Component', () => {
    let comp: ComputeGroupDeleteDialogComponent;
    let fixture: ComponentFixture<ComputeGroupDeleteDialogComponent>;
    let service: ComputeGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Level6TestModule],
        declarations: [ComputeGroupDeleteDialogComponent]
      })
        .overrideTemplate(ComputeGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComputeGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComputeGroupService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
