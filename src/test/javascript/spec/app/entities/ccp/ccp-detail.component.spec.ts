/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { CCPDetailComponent } from 'app/entities/ccp/ccp-detail.component';
import { CCP } from 'app/shared/model/ccp.model';

describe('Component Tests', () => {
  describe('CCP Management Detail Component', () => {
    let comp: CCPDetailComponent;
    let fixture: ComponentFixture<CCPDetailComponent>;
    const route = ({ data: of({ cCP: new CCP(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [CCPDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CCPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CCPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cCP).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
