import { Moment } from 'moment';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { IStudient } from 'app/shared/model/studient.model';
import { IDocument } from 'app/shared/model/document.model';
import { IIntervention } from 'app/shared/model/intervention.model';

export interface IFormationSession {
  id?: number;
  begin?: Moment;
  end?: Moment;
  evaluations?: IEvaluation[];
  studients?: IStudient[];
  documents?: IDocument[];
  formationId?: number;
  interventions?: IIntervention[];
}

export class FormationSession implements IFormationSession {
  constructor(
    public id?: number,
    public begin?: Moment,
    public end?: Moment,
    public evaluations?: IEvaluation[],
    public studients?: IStudient[],
    public documents?: IDocument[],
    public formationId?: number,
    public interventions?: IIntervention[]
  ) {}
}
