import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICCP, CCP } from 'app/shared/model/ccp.model';
import { CCPService } from './ccp.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';

@Component({
  selector: 'jhi-ccp-update',
  templateUrl: './ccp-update.component.html'
})
export class CCPUpdateComponent implements OnInit {
  isSaving: boolean;

  documents: IDocument[];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    documents: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cCPService: CCPService,
    protected documentService: DocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cCP }) => {
      this.updateForm(cCP);
    });
    this.documentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocument[]>) => response.body)
      )
      .subscribe((res: IDocument[]) => (this.documents = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cCP: ICCP) {
    this.editForm.patchValue({
      id: cCP.id,
      title: cCP.title,
      documents: cCP.documents
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cCP = this.createFromForm();
    if (cCP.id !== undefined) {
      this.subscribeToSaveResponse(this.cCPService.update(cCP));
    } else {
      this.subscribeToSaveResponse(this.cCPService.create(cCP));
    }
  }

  private createFromForm(): ICCP {
    return {
      ...new CCP(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      documents: this.editForm.get(['documents']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICCP>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
