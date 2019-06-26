import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CCP } from 'app/shared/model/ccp.model';
import { CCPService } from './ccp.service';
import { CCPComponent } from './ccp.component';
import { CCPDetailComponent } from './ccp-detail.component';
import { CCPUpdateComponent } from './ccp-update.component';
import { CCPDeletePopupComponent } from './ccp-delete-dialog.component';
import { ICCP } from 'app/shared/model/ccp.model';

@Injectable({ providedIn: 'root' })
export class CCPResolve implements Resolve<ICCP> {
  constructor(private service: CCPService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICCP> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CCP>) => response.ok),
        map((cCP: HttpResponse<CCP>) => cCP.body)
      );
    }
    return of(new CCP());
  }
}

export const cCPRoute: Routes = [
  {
    path: '',
    component: CCPComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tothApplicationApp.cCP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CCPDetailComponent,
    resolve: {
      cCP: CCPResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.cCP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CCPUpdateComponent,
    resolve: {
      cCP: CCPResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.cCP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CCPUpdateComponent,
    resolve: {
      cCP: CCPResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.cCP.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cCPPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CCPDeletePopupComponent,
    resolve: {
      cCP: CCPResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tothApplicationApp.cCP.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
