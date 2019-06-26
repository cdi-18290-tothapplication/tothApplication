import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IIntervention, Intervention } from 'app/shared/model/intervention.model';
import { InterventionService } from './intervention.service';
import { ITrainer } from 'app/shared/model/trainer.model';
import { TrainerService } from 'app/entities/trainer';
import { IFormationSession } from 'app/shared/model/formation-session.model';
import { FormationSessionService } from 'app/entities/formation-session';

@Component({
  selector: 'jhi-intervention-update',
  templateUrl: './intervention-update.component.html'
})
export class InterventionUpdateComponent implements OnInit {
  isSaving: boolean;

  trainers: ITrainer[];

  formationsessions: IFormationSession[];
  beginDp: any;
  endDp: any;

  editForm = this.fb.group({
    id: [],
    begin: [null, [Validators.required]],
    end: [null, [Validators.required]],
    trainerId: [],
    formationSessionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected interventionService: InterventionService,
    protected trainerService: TrainerService,
    protected formationSessionService: FormationSessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ intervention }) => {
      this.updateForm(intervention);
    });
    this.trainerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITrainer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrainer[]>) => response.body)
      )
      .subscribe((res: ITrainer[]) => (this.trainers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.formationSessionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFormationSession[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFormationSession[]>) => response.body)
      )
      .subscribe((res: IFormationSession[]) => (this.formationsessions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(intervention: IIntervention) {
    this.editForm.patchValue({
      id: intervention.id,
      begin: intervention.begin,
      end: intervention.end,
      trainerId: intervention.trainerId,
      formationSessionId: intervention.formationSessionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const intervention = this.createFromForm();
    if (intervention.id !== undefined) {
      this.subscribeToSaveResponse(this.interventionService.update(intervention));
    } else {
      this.subscribeToSaveResponse(this.interventionService.create(intervention));
    }
  }

  private createFromForm(): IIntervention {
    return {
      ...new Intervention(),
      id: this.editForm.get(['id']).value,
      begin: this.editForm.get(['begin']).value,
      end: this.editForm.get(['end']).value,
      trainerId: this.editForm.get(['trainerId']).value,
      formationSessionId: this.editForm.get(['formationSessionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntervention>>) {
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

  trackTrainerById(index: number, item: ITrainer) {
    return item.id;
  }

  trackFormationSessionById(index: number, item: IFormationSession) {
    return item.id;
  }
}
