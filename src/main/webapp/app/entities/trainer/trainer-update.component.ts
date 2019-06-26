import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITrainer, Trainer } from 'app/shared/model/trainer.model';
import { TrainerService } from './trainer.service';
import { IUser, UserService } from 'app/core';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation';

@Component({
  selector: 'jhi-trainer-update',
  templateUrl: './trainer-update.component.html'
})
export class TrainerUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  evaluations: IEvaluation[];

  editForm = this.fb.group({
    id: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected trainerService: TrainerService,
    protected userService: UserService,
    protected evaluationService: EvaluationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trainer }) => {
      this.updateForm(trainer);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.evaluationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvaluation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvaluation[]>) => response.body)
      )
      .subscribe((res: IEvaluation[]) => (this.evaluations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(trainer: ITrainer) {
    this.editForm.patchValue({
      id: trainer.id,
      userId: trainer.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trainer = this.createFromForm();
    if (trainer.id !== undefined) {
      this.subscribeToSaveResponse(this.trainerService.update(trainer));
    } else {
      this.subscribeToSaveResponse(this.trainerService.create(trainer));
    }
  }

  private createFromForm(): ITrainer {
    return {
      ...new Trainer(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainer>>) {
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

  trackEvaluationById(index: number, item: IEvaluation) {
    return item.id;
  }
}
