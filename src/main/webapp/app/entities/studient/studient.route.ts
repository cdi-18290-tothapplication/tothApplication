import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Studient } from 'app/shared/model/studient.model';
import { StudientService } from './studient.service';
import { StudientComponent } from './studient.component';
import { StudientDetailComponent } from './studient-detail.component';
import { StudientUpdateComponent } from './studient-update.component';
import { StudientDeletePopupComponent } from './studient-delete-dialog.component';
import { IStudient } from 'app/shared/model/studient.model';

@Injectable({ providedIn: 'root' })
export class StudientResolve implements Resolve<IStudient> {
  constructor(private service: StudientService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudient> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Studient>) => response.ok),
        map((studient: HttpResponse<Studient>) => studient.body)
      );
    }
    return of(new Studient());
  }
}

export const studientRoute: Routes = [
  {
    path: '',
    component: StudientComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_STUDIENT', 'ROLE_TRAINER'],
      defaultSort: 'id,asc',
      pageTitle: 'tothApplicationApp.studient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudientDetailComponent,
    resolve: {
      studient: StudientResolve
    },
    data: {
      authorities: ['ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_STUDIENT', 'ROLE_TRAINER'],
      pageTitle: 'tothApplicationApp.studient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudientUpdateComponent,
    resolve: {
      studient: StudientResolve
    },
    data: {
      authorities: ['ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_STUDIENT', 'ROLE_TRAINER'],
      pageTitle: 'tothApplicationApp.studient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudientUpdateComponent,
    resolve: {
      studient: StudientResolve
    },
    data: {
      authorities: ['ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_STUDIENT', 'ROLE_TRAINER'],
      pageTitle: 'tothApplicationApp.studient.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const studientPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StudientDeletePopupComponent,
    resolve: {
      studient: StudientResolve
    },
    data: {
      authorities: ['ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_STUDIENT', 'ROLE_TRAINER'],
      pageTitle: 'tothApplicationApp.studient.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
