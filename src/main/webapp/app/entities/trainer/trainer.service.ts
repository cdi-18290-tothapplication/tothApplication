import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrainer } from 'app/shared/model/trainer.model';

type EntityResponseType = HttpResponse<ITrainer>;
type EntityArrayResponseType = HttpResponse<ITrainer[]>;

@Injectable({ providedIn: 'root' })
export class TrainerService {
  public resourceUrl = SERVER_API_URL + 'api/trainers';

  constructor(protected http: HttpClient) {}

  create(trainer: ITrainer): Observable<EntityResponseType> {
    return this.http.post<ITrainer>(this.resourceUrl, trainer, { observe: 'response' });
  }

  update(trainer: ITrainer): Observable<EntityResponseType> {
    return this.http.put<ITrainer>(this.resourceUrl, trainer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
