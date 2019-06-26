import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFormationSession } from 'app/shared/model/formation-session.model';

type EntityResponseType = HttpResponse<IFormationSession>;
type EntityArrayResponseType = HttpResponse<IFormationSession[]>;

@Injectable({ providedIn: 'root' })
export class FormationSessionService {
  public resourceUrl = SERVER_API_URL + 'api/formation-sessions';

  constructor(protected http: HttpClient) {}

  create(formationSession: IFormationSession): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formationSession);
    return this.http
      .post<IFormationSession>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formationSession: IFormationSession): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formationSession);
    return this.http
      .put<IFormationSession>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormationSession>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormationSession[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(formationSession: IFormationSession): IFormationSession {
    const copy: IFormationSession = Object.assign({}, formationSession, {
      begin: formationSession.begin != null && formationSession.begin.isValid() ? formationSession.begin.format(DATE_FORMAT) : null,
      end: formationSession.end != null && formationSession.end.isValid() ? formationSession.end.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.begin = res.body.begin != null ? moment(res.body.begin) : null;
      res.body.end = res.body.end != null ? moment(res.body.end) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((formationSession: IFormationSession) => {
        formationSession.begin = formationSession.begin != null ? moment(formationSession.begin) : null;
        formationSession.end = formationSession.end != null ? moment(formationSession.end) : null;
      });
    }
    return res;
  }
}
