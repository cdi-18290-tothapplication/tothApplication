import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FormationSession } from 'app/shared/model/formation-session.model';
import { FormationSessionService } from './formation-session.service';
import { FormationSessionComponent } from './formation-session.component';
import { FormationSessionDetailComponent } from './formation-session-detail.component';
import { FormationSessionUpdateComponent } from './formation-session-update.component';
import { FormationSessionDeletePopupComponent } from './formation-session-delete-dialog.component';
import { IFormationSession } from 'app/shared/model/formation-session.model';

@Injectable({ providedIn: 'root' })
export class FormationSessionResolve implements Resolve<IFormationSession> {
  constructor(private service: FormationSessionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFormationSession> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FormationSession>) => response.ok),
        map((formationSession: HttpResponse<FormationSession>) => formationSession.body)
      );
    }
    return of(new FormationSession());
  }
}

export const formationSessionRoute: Routes = [
  {
    path: '',
    component: FormationSessionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tothApplicationApp.formationSession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormationSessionDetailComponent,
    resolve: {
      formationSession: FormationSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.formationSession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormationSessionUpdateComponent,
    resolve: {
      formationSession: FormationSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.formationSession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormationSessionUpdateComponent,
    resolve: {
      formationSession: FormationSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.formationSession.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const formationSessionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FormationSessionDeletePopupComponent,
    resolve: {
      formationSession: FormationSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.formationSession.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
