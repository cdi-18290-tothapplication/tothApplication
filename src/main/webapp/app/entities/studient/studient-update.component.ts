import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStudient, Studient } from 'app/shared/model/studient.model';
import { StudientService } from './studient.service';
import { IUser, UserService } from 'app/core';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation';

@Component({
  selector: 'jhi-studient-update',
  templateUrl: './studient-update.component.html'
})
export class StudientUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  photos: IDocument[];

  evaluations: IEvaluation[];
  birthdateDp: any;

  editForm = this.fb.group({
    id: [],
    birthdate: [],
    userId: [],
    photoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected studientService: StudientService,
    protected userService: UserService,
    protected documentService: DocumentService,
    protected evaluationService: EvaluationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ studient }) => {
      this.updateForm(studient);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentService
      .query({ filter: 'studient-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocument[]>) => response.body)
      )
      .subscribe(
        (res: IDocument[]) => {
          if (!!this.editForm.get('photoId').value) {
            this.photos = res;
          } else {
            this.documentService
              .find(this.editForm.get('photoId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IDocument>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IDocument>) => subResponse.body)
              )
              .subscribe(
                (subRes: IDocument) => (this.photos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.evaluationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvaluation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvaluation[]>) => response.body)
      )
      .subscribe((res: IEvaluation[]) => (this.evaluations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(studient: IStudient) {
    this.editForm.patchValue({
      id: studient.id,
      birthdate: studient.birthdate,
      userId: studient.userId,
      photoId: studient.photoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const studient = this.createFromForm();
    if (studient.id !== undefined) {
      this.subscribeToSaveResponse(this.studientService.update(studient));
    } else {
      this.subscribeToSaveResponse(this.studientService.create(studient));
    }
  }

  private createFromForm(): IStudient {
    return {
      ...new Studient(),
      id: this.editForm.get(['id']).value,
      birthdate: this.editForm.get(['birthdate']).value,
      userId: this.editForm.get(['userId']).value,
      photoId: this.editForm.get(['photoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudient>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  trackEvaluationById(index: number, item: IEvaluation) {
    return item.id;
  }
}
