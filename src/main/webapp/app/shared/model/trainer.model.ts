import { IIntervention } from 'app/shared/model/intervention.model';

export interface ITrainer {
  id?: number;
  userId?: number;
  evaluationId?: number;
  interventions?: IIntervention[];
}

export class Trainer implements ITrainer {
  constructor(public id?: number, public userId?: number, public evaluationId?: number, public interventions?: IIntervention[]) {}
}
