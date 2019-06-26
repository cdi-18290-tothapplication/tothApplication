import { IDocument } from 'app/shared/model/document.model';

export interface ICCP {
  id?: number;
  title?: string;
  documents?: IDocument[];
}

export class CCP implements ICCP {
  constructor(public id?: number, public title?: string, public documents?: IDocument[]) {}
}
