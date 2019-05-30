/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Level6TestModule } from '../../../test.module';
import { ComputeGroupComponent } from 'app/entities/compute-group/compute-group.component';
import { ComputeGroupService } from 'app/entities/compute-group/compute-group.service';
import { ComputeGroup } from 'app/shared/model/compute-group.model';

describe('Component Tests', () => {
  describe('ComputeGroup Management Component', () => {
    let comp: ComputeGroupComponent;
    let fixture: ComponentFixture<ComputeGroupComponent>;
    let service: ComputeGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Level6TestModule],
        declarations: [ComputeGroupComponent],
        providers: []
      })
        .overrideTemplate(ComputeGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComputeGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComputeGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ComputeGroup('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.computeGroups[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
