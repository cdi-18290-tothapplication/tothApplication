import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICCP } from 'app/shared/model/ccp.model';

@Component({
  selector: 'jhi-ccp-detail',
  templateUrl: './ccp-detail.component.html'
})
export class CCPDetailComponent implements OnInit {
  cCP: ICCP;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cCP }) => {
      this.cCP = cCP;
    });
  }

  previousState() {
    window.history.back();
  }
}
