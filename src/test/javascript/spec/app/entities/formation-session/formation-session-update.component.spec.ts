/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { FormationSessionUpdateComponent } from 'app/entities/formation-session/formation-session-update.component';
import { FormationSessionService } from 'app/entities/formation-session/formation-session.service';
import { FormationSession } from 'app/shared/model/formation-session.model';

describe('Component Tests', () => {
  describe('FormationSession Management Update Component', () => {
    let comp: FormationSessionUpdateComponent;
    let fixture: ComponentFixture<FormationSessionUpdateComponent>;
    let service: FormationSessionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [FormationSessionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormationSessionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormationSessionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormationSessionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormationSession(123);
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
        const entity = new FormationSession();
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
