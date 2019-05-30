/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Level6TestModule } from '../../../test.module';
import { ComputeGroupUpdateComponent } from 'app/entities/compute-group/compute-group-update.component';
import { ComputeGroupService } from 'app/entities/compute-group/compute-group.service';
import { ComputeGroup } from 'app/shared/model/compute-group.model';

describe('Component Tests', () => {
  describe('ComputeGroup Management Update Component', () => {
    let comp: ComputeGroupUpdateComponent;
    let fixture: ComponentFixture<ComputeGroupUpdateComponent>;
    let service: ComputeGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Level6TestModule],
        declarations: [ComputeGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComputeGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComputeGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComputeGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComputeGroup('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComputeGroup();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
