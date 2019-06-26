import { Moment } from 'moment';

export interface IIntervention {
  id?: number;
  begin?: Moment;
  end?: Moment;
  trainerId?: number;
  formationSessionId?: number;
}

export class Intervention implements IIntervention {
  constructor(
    public id?: number,
    public begin?: Moment,
    public end?: Moment,
    public trainerId?: number,
    public formationSessionId?: number
  ) {}
}
