import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IFormationSession, FormationSession } from 'app/shared/model/formation-session.model';
import { FormationSessionService } from './formation-session.service';
import { IStudient } from 'app/shared/model/studient.model';
import { StudientService } from 'app/entities/studient';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IFormation } from 'app/shared/model/formation.model';
import { FormationService } from 'app/entities/formation';

@Component({
  selector: 'jhi-formation-session-update',
  templateUrl: './formation-session-update.component.html'
})
export class FormationSessionUpdateComponent implements OnInit {
  isSaving: boolean;

  studients: IStudient[];

  documents: IDocument[];

  formations: IFormation[];
  beginDp: any;
  endDp: any;

  editForm = this.fb.group({
    id: [],
    begin: [null, [Validators.required]],
    end: [null, [Validators.required]],
    studients: [],
    documents: [],
    formationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected formationSessionService: FormationSessionService,
    protected studientService: StudientService,
    protected documentService: DocumentService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formationSession }) => {
      this.updateForm(formationSession);
    });
    this.studientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudient[]>) => response.body)
      )
      .subscribe((res: IStudient[]) => (this.studients = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocument[]>) => response.body)
      )
      .subscribe((res: IDocument[]) => (this.documents = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.formationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFormation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFormation[]>) => response.body)
      )
      .subscribe((res: IFormation[]) => (this.formations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(formationSession: IFormationSession) {
    this.editForm.patchValue({
      id: formationSession.id,
      begin: formationSession.begin,
      end: formationSession.end,
      studients: formationSession.studients,
      documents: formationSession.documents,
      formationId: formationSession.formationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const formationSession = this.createFromForm();
    if (formationSession.id !== undefined) {
      this.subscribeToSaveResponse(this.formationSessionService.update(formationSession));
    } else {
      this.subscribeToSaveResponse(this.formationSessionService.create(formationSession));
    }
  }

  private createFromForm(): IFormationSession {
    return {
      ...new FormationSession(),
      id: this.editForm.get(['id']).value,
      begin: this.editForm.get(['begin']).value,
      end: this.editForm.get(['end']).value,
      studients: this.editForm.get(['studients']).value,
      documents: this.editForm.get(['documents']).value,
      formationId: this.editForm.get(['formationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormationSession>>) {
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

  trackStudientById(index: number, item: IStudient) {
    return item.id;
  }

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  trackFormationById(index: number, item: IFormation) {
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
