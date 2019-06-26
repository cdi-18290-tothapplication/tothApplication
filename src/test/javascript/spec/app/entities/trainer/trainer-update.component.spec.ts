/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { TrainerUpdateComponent } from 'app/entities/trainer/trainer-update.component';
import { TrainerService } from 'app/entities/trainer/trainer.service';
import { Trainer } from 'app/shared/model/trainer.model';

describe('Component Tests', () => {
  describe('Trainer Management Update Component', () => {
    let comp: TrainerUpdateComponent;
    let fixture: ComponentFixture<TrainerUpdateComponent>;
    let service: TrainerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [TrainerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trainer(123);
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
        const entity = new Trainer();
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
