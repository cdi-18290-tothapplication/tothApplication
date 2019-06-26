/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { StudientDetailComponent } from 'app/entities/studient/studient-detail.component';
import { Studient } from 'app/shared/model/studient.model';

describe('Component Tests', () => {
  describe('Studient Management Detail Component', () => {
    let comp: StudientDetailComponent;
    let fixture: ComponentFixture<StudientDetailComponent>;
    const route = ({ data: of({ studient: new Studient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [StudientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StudientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
