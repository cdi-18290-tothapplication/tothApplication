import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudient } from 'app/shared/model/studient.model';

@Component({
  selector: 'jhi-studient-detail',
  templateUrl: './studient-detail.component.html'
})
export class StudientDetailComponent implements OnInit {
  studient: IStudient;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studient }) => {
      this.studient = studient;
    });
  }

  previousState() {
    window.history.back();
  }
}
