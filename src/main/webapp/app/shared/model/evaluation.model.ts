import { Moment } from 'moment';

export interface IEvaluation {
  id?: number;
  date?: Moment;
  note?: number;
  commentary?: string;
  studientId?: number;
  trainerId?: number;
  formationSessionId?: number;
}

export class Evaluation implements IEvaluation {
  constructor(
    public id?: number,
    public date?: Moment,
    public note?: number,
    public commentary?: string,
    public studientId?: number,
    public trainerId?: number,
    public formationSessionId?: number
  ) {}
}
