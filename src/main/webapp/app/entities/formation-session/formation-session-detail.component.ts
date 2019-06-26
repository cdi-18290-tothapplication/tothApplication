import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormationSession } from 'app/shared/model/formation-session.model';

@Component({
  selector: 'jhi-formation-session-detail',
  templateUrl: './formation-session-detail.component.html'
})
export class FormationSessionDetailComponent implements OnInit {
  formationSession: IFormationSession;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formationSession }) => {
      this.formationSession = formationSession;
    });
  }

  previousState() {
    window.history.back();
  }
}
