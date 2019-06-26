import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICCP } from 'app/shared/model/ccp.model';

type EntityResponseType = HttpResponse<ICCP>;
type EntityArrayResponseType = HttpResponse<ICCP[]>;

@Injectable({ providedIn: 'root' })
export class CCPService {
  public resourceUrl = SERVER_API_URL + 'api/ccps';

  constructor(protected http: HttpClient) {}

  create(cCP: ICCP): Observable<EntityResponseType> {
    return this.http.post<ICCP>(this.resourceUrl, cCP, { observe: 'response' });
  }

  update(cCP: ICCP): Observable<EntityResponseType> {
    return this.http.put<ICCP>(this.resourceUrl, cCP, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICCP>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICCP[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
