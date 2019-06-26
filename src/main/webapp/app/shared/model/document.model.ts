export const enum TypeDocument {
  COURSE = 'COURSE',
  EXERCISE = 'EXERCISE',
  ABSENCE = 'ABSENCE',
  CV = 'CV',
  PHOTO = 'PHOTO'
}

export interface IDocument {
  id?: number;
  title?: string;
  type?: TypeDocument;
  filename?: string;
}

export class Document implements IDocument {
  constructor(public id?: number, public title?: string, public type?: TypeDocument, public filename?: string) {}
}
