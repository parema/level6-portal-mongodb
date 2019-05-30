import { IComputeGroup } from 'app/shared/model/compute-group.model';

export interface IProject {
  id?: string;
  projectName?: string;
  computeGroups?: IComputeGroup[];
}

export class Project implements IProject {
  constructor(public id?: string, public projectName?: string, public computeGroups?: IComputeGroup[]) {}
}
