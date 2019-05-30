import { IProject } from 'app/shared/model/project.model';

export interface IComputeGroup {
  id?: string;
  computeGroupName?: string;
  parent?: IProject;
}

export class ComputeGroup implements IComputeGroup {
  constructor(public id?: string, public computeGroupName?: string, public parent?: IProject) {}
}
