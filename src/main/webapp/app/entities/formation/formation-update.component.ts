import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFormation, Formation } from 'app/shared/model/formation.model';
import { FormationService } from './formation.service';
import { ICCP } from 'app/shared/model/ccp.model';
import { CCPService } from 'app/entities/ccp';

@Component({
  selector: 'jhi-formation-update',
  templateUrl: './formation-update.component.html'
})
export class FormationUpdateComponent implements OnInit {
  isSaving: boolean;

  ccps: ICCP[];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    desc: [],
    ccps: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected formationService: FormationService,
    protected cCPService: CCPService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.updateForm(formation);
    });
    this.cCPService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICCP[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICCP[]>) => response.body)
      )
      .subscribe((res: ICCP[]) => (this.ccps = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(formation: IFormation) {
    this.editForm.patchValue({
      id: formation.id,
      label: formation.label,
      desc: formation.desc,
      ccps: formation.ccps
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const formation = this.createFromForm();
    if (formation.id !== undefined) {
      this.subscribeToSaveResponse(this.formationService.update(formation));
    } else {
      this.subscribeToSaveResponse(this.formationService.create(formation));
    }
  }

  private createFromForm(): IFormation {
    return {
      ...new Formation(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      desc: this.editForm.get(['desc']).value,
      ccps: this.editForm.get(['ccps']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>) {
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

  trackCCPById(index: number, item: ICCP) {
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
