import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainer } from 'app/shared/model/trainer.model';
import { TrainerService } from './trainer.service';

@Component({
  selector: 'jhi-trainer-delete-dialog',
  templateUrl: './trainer-delete-dialog.component.html'
})
export class TrainerDeleteDialogComponent {
  trainer: ITrainer;

  constructor(protected trainerService: TrainerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.trainerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'trainerListModification',
        content: 'Deleted an trainer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-trainer-delete-popup',
  template: ''
})
export class TrainerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trainer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TrainerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.trainer = trainer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/trainer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/trainer', { outlets: { popup: null } }]);
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
