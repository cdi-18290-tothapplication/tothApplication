import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document/document.service';

@Component({
  selector: 'jhi-document-detail',
  templateUrl: './document-detail.component.html'
})
export class DocumentDetailComponent implements OnInit {
  document: IDocument;

  constructor(protected activatedRoute: ActivatedRoute, protected documentService: DocumentService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ document }) => {
      this.document = document;
    });
  }

  previousState() {
    window.history.back();
  }

  download() {
    this.documentService.download(this.document.id).subscribe(next => {
      const blob = new Blob([next.body], { type: next.headers.get('content-type') });
      const url = window.URL.createObjectURL(blob);
      const downloadLink = document.createElement('a');
      downloadLink.download = 'a';
      downloadLink.href = url;
      downloadLink.setAttribute('download', this.getFileNameFromHttpResponse(next));
      document.body.appendChild(downloadLink);
      downloadLink.click();
    });
  }

  private getFileNameFromHttpResponse(httpResponse) {
    const contentDispositionHeader = httpResponse.headers.get('Content-Disposition');
    const result = contentDispositionHeader
      .split(';')[1]
      .trim()
      .split('=')[1];
    return result.replace(/"/g, '');
  }
}
