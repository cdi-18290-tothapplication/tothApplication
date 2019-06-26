/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { StudientUpdateComponent } from 'app/entities/studient/studient-update.component';
import { StudientService } from 'app/entities/studient/studient.service';
import { Studient } from 'app/shared/model/studient.model';

describe('Component Tests', () => {
  describe('Studient Management Update Component', () => {
    let comp: StudientUpdateComponent;
    let fixture: ComponentFixture<StudientUpdateComponent>;
    let service: StudientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [StudientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Studient(123);
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
        const entity = new Studient();
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
