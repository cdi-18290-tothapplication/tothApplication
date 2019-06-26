import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICCP } from 'app/shared/model/ccp.model';
import { CCPService } from './ccp.service';

@Component({
  selector: 'jhi-ccp-delete-dialog',
  templateUrl: './ccp-delete-dialog.component.html'
})
export class CCPDeleteDialogComponent {
  cCP: ICCP;

  constructor(protected cCPService: CCPService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cCPService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cCPListModification',
        content: 'Deleted an cCP'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ccp-delete-popup',
  template: ''
})
export class CCPDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cCP }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CCPDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cCP = cCP;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ccp', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ccp', { outlets: { popup: null } }]);
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
