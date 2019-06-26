import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudient } from 'app/shared/model/studient.model';
import { StudientService } from './studient.service';

@Component({
  selector: 'jhi-studient-delete-dialog',
  templateUrl: './studient-delete-dialog.component.html'
})
export class StudientDeleteDialogComponent {
  studient: IStudient;

  constructor(protected studientService: StudientService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.studientService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'studientListModification',
        content: 'Deleted an studient'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-studient-delete-popup',
  template: ''
})
export class StudientDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studient }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StudientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.studient = studient;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/studient', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/studient', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
