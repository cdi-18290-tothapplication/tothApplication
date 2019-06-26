import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEvaluation, Evaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from './evaluation.service';
import { IStudient } from 'app/shared/model/studient.model';
import { StudientService } from 'app/entities/studient';
import { ITrainer } from 'app/shared/model/trainer.model';
import { TrainerService } from 'app/entities/trainer';
import { IFormationSession } from 'app/shared/model/formation-session.model';
import { FormationSessionService } from 'app/entities/formation-session';

@Component({
  selector: 'jhi-evaluation-update',
  templateUrl: './evaluation-update.component.html'
})
export class EvaluationUpdateComponent implements OnInit {
  isSaving: boolean;

  studients: IStudient[];

  trainers: ITrainer[];

  formationsessions: IFormationSession[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    note: [null, [Validators.required]],
    commentary: [],
    studientId: [],
    trainerId: [],
    formationSessionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected evaluationService: EvaluationService,
    protected studientService: StudientService,
    protected trainerService: TrainerService,
    protected formationSessionService: FormationSessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evaluation }) => {
      this.updateForm(evaluation);
    });
    this.studientService
      .query({ filter: 'evaluation-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IStudient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudient[]>) => response.body)
      )
      .subscribe(
        (res: IStudient[]) => {
          if (!!this.editForm.get('studientId').value) {
            this.studients = res;
          } else {
            this.studientService
              .find(this.editForm.get('studientId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IStudient>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IStudient>) => subResponse.body)
              )
              .subscribe(
                (subRes: IStudient) => (this.studients = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.trainerService
      .query({ filter: 'evaluation-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITrainer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrainer[]>) => response.body)
      )
      .subscribe(
        (res: ITrainer[]) => {
          if (!!this.editForm.get('trainerId').value) {
            this.trainers = res;
          } else {
            this.trainerService
              .find(this.editForm.get('trainerId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITrainer>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITrainer>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITrainer) => (this.trainers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.formationSessionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFormationSession[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFormationSession[]>) => response.body)
      )
      .subscribe((res: IFormationSession[]) => (this.formationsessions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(evaluation: IEvaluation) {
    this.editForm.patchValue({
      id: evaluation.id,
      date: evaluation.date,
      note: evaluation.note,
      commentary: evaluation.commentary,
      studientId: evaluation.studientId,
      trainerId: evaluation.trainerId,
      formationSessionId: evaluation.formationSessionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const evaluation = this.createFromForm();
    if (evaluation.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationService.update(evaluation));
    } else {
      this.subscribeToSaveResponse(this.evaluationService.create(evaluation));
    }
  }

  private createFromForm(): IEvaluation {
    return {
      ...new Evaluation(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      note: this.editForm.get(['note']).value,
      commentary: this.editForm.get(['commentary']).value,
      studientId: this.editForm.get(['studientId']).value,
      trainerId: this.editForm.get(['trainerId']).value,
      formationSessionId: this.editForm.get(['formationSessionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluation>>) {
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

  trackTrainerById(index: number, item: ITrainer) {
    return item.id;
  }

  trackFormationSessionById(index: number, item: IFormationSession) {
    return item.id;
  }
}
