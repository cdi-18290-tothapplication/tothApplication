import { IFormationSession } from 'app/shared/model/formation-session.model';
import { ICCP } from 'app/shared/model/ccp.model';

export interface IFormation {
  id?: number;
  label?: string;
  desc?: string;
  sessions?: IFormationSession[];
  ccps?: ICCP[];
}

export class Formation implements IFormation {
  constructor(
    public id?: number,
    public label?: string,
    public desc?: string,
    public sessions?: IFormationSession[],
    public ccps?: ICCP[]
  ) {}
}
