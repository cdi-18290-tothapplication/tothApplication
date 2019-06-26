import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudient } from 'app/shared/model/studient.model';

type EntityResponseType = HttpResponse<IStudient>;
type EntityArrayResponseType = HttpResponse<IStudient[]>;

@Injectable({ providedIn: 'root' })
export class StudientService {
  public resourceUrl = SERVER_API_URL + 'api/studients';

  constructor(protected http: HttpClient) {}

  create(studient: IStudient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studient);
    return this.http
      .post<IStudient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studient: IStudient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studient);
    return this.http
      .put<IStudient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(studient: IStudient): IStudient {
    const copy: IStudient = Object.assign({}, studient, {
      birthdate: studient.birthdate != null && studient.birthdate.isValid() ? studient.birthdate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthdate = res.body.birthdate != null ? moment(res.body.birthdate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studient: IStudient) => {
        studient.birthdate = studient.birthdate != null ? moment(studient.birthdate) : null;
      });
    }
    return res;
  }
}
