import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocument } from 'app/shared/model/document.model';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IDocument>;
type EntityArrayResponseType = HttpResponse<IDocument[]>;

@Injectable({ providedIn: 'root' })
export class DocumentService {
  public resourceUrl = SERVER_API_URL + 'api/documents';

  constructor(protected http: HttpClient) {}

  create(document: IDocument, data: File): Observable<EntityResponseType> {
    const formData = new FormData();
    formData.append('file', data);

    const doc = this.http.post(this.resourceUrl, document, { observe: 'response' }).subscribe(next => {
      next.body;
    });

    console.log(doc);

    return null;
  }

  update(document: IDocument): Observable<EntityResponseType> {
    return this.http.put<IDocument>(this.resourceUrl, document, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  download(id: number): Observable<HttpResponse<any>> {
    return this.http.get(`${this.resourceUrl}/${id}/download`, { responseType: 'blob', observe: 'response' });
  }

  upload(id: number, data: FormData) {
    return this.http
      .post<any>(`${this.resourceUrl}/${id}/upload`, data, {
        reportProgress: true,
        observe: 'events'
      })
      .pipe(
        map(event => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              const progress = Math.round((100 * event.loaded) / event.total);
              return { status: 'progress', message: progress };

            case HttpEventType.Response:
              return event.body;
            default:
              return `Unhandled event: ${event.type}`;
          }
        })
      );
  }
}
