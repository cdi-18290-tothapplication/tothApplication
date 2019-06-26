/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { FormationSessionDetailComponent } from 'app/entities/formation-session/formation-session-detail.component';
import { FormationSession } from 'app/shared/model/formation-session.model';

describe('Component Tests', () => {
  describe('FormationSession Management Detail Component', () => {
    let comp: FormationSessionDetailComponent;
    let fixture: ComponentFixture<FormationSessionDetailComponent>;
    const route = ({ data: of({ formationSession: new FormationSession(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [FormationSessionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormationSessionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormationSessionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formationSession).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
