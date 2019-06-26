/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TothApplicationTestModule } from '../../../test.module';
import { TrainerDeleteDialogComponent } from 'app/entities/trainer/trainer-delete-dialog.component';
import { TrainerService } from 'app/entities/trainer/trainer.service';

describe('Component Tests', () => {
  describe('Trainer Management Delete Component', () => {
    let comp: TrainerDeleteDialogComponent;
    let fixture: ComponentFixture<TrainerDeleteDialogComponent>;
    let service: TrainerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [TrainerDeleteDialogComponent]
      })
        .overrideTemplate(TrainerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainerService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
