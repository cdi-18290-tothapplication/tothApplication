/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { CCPUpdateComponent } from 'app/entities/ccp/ccp-update.component';
import { CCPService } from 'app/entities/ccp/ccp.service';
import { CCP } from 'app/shared/model/ccp.model';

describe('Component Tests', () => {
  describe('CCP Management Update Component', () => {
    let comp: CCPUpdateComponent;
    let fixture: ComponentFixture<CCPUpdateComponent>;
    let service: CCPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [CCPUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CCPUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CCPUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CCPService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CCP(123);
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
        const entity = new CCP();
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
