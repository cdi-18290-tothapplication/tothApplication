import { Moment } from 'moment';

export interface IStudient {
  id?: number;
  birthdate?: Moment;
  userId?: number;
  photoId?: number;
  evaluationId?: number;
}

export class Studient implements IStudient {
  constructor(
    public id?: number,
    public birthdate?: Moment,
    public userId?: number,
    public photoId?: number,
    public evaluationId?: number
  ) {}
}
