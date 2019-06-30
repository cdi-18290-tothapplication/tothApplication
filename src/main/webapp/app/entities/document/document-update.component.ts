import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
  fileToUpload: File = null;

  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    type: [null, [Validators.required]],
    filename: [null, [Validators.required]]
  });

  constructor(protected documentService: DocumentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);
    });
  }

  updateForm(document: IDocument) {
    this.editForm.patchValue({
      id: document.id,
      title: document.title,
      type: document.type,
      filename: document.filename
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document, this.fileToUpload));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      type: this.editForm.get(['type']).value,
      filename: this.editForm.get(['filename']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  getFile(event) {
    this.fileToUpload = event.target.files[0];
    this.editForm.get('filename').setValue(this.fileToUpload.name);
  }
}
