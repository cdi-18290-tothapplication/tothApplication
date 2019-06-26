/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TothApplicationTestModule } from '../../../test.module';
import { CCPDeleteDialogComponent } from 'app/entities/ccp/ccp-delete-dialog.component';
import { CCPService } from 'app/entities/ccp/ccp.service';

describe('Component Tests', () => {
  describe('CCP Management Delete Component', () => {
    let comp: CCPDeleteDialogComponent;
    let fixture: ComponentFixture<CCPDeleteDialogComponent>;
    let service: CCPService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [CCPDeleteDialogComponent]
      })
        .overrideTemplate(CCPDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CCPDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CCPService);
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
