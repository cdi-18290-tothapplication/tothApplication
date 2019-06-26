import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainer } from 'app/shared/model/trainer.model';

@Component({
  selector: 'jhi-trainer-detail',
  templateUrl: './trainer-detail.component.html'
})
export class TrainerDetailComponent implements OnInit {
  trainer: ITrainer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trainer }) => {
      this.trainer = trainer;
    });
  }

  previousState() {
    window.history.back();
  }
}
