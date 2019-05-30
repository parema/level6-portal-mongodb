/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Level6TestModule } from '../../../test.module';
import { ComputeGroupDetailComponent } from 'app/entities/compute-group/compute-group-detail.component';
import { ComputeGroup } from 'app/shared/model/compute-group.model';

describe('Component Tests', () => {
  describe('ComputeGroup Management Detail Component', () => {
    let comp: ComputeGroupDetailComponent;
    let fixture: ComponentFixture<ComputeGroupDetailComponent>;
    const route = ({ data: of({ computeGroup: new ComputeGroup('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Level6TestModule],
        declarations: [ComputeGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComputeGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComputeGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.computeGroup).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
