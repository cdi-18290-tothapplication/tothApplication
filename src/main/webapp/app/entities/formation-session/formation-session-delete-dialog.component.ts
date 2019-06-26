import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormationSession } from 'app/shared/model/formation-session.model';
import { FormationSessionService } from './formation-session.service';

@Component({
  selector: 'jhi-formation-session-delete-dialog',
  templateUrl: './formation-session-delete-dialog.component.html'
})
export class FormationSessionDeleteDialogComponent {
  formationSession: IFormationSession;

  constructor(
    protected formationSessionService: FormationSessionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.formationSessionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'formationSessionListModification',
        content: 'Deleted an formationSession'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-formation-session-delete-popup',
  template: ''
})
export class FormationSessionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formationSession }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FormationSessionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.formationSession = formationSession;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/formation-session', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/formation-session', { outlets: { popup: null } }]);
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
