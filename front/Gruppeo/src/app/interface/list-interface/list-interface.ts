import {GroupInterface} from '../group-interface/group-interface';
import {PersonInterface} from '../person-interface/person-interface';

export interface ListInterface {
  id?: number;
  name: string;
  number_of_members: number;
  members?: PersonInterface[];
  groups?: GroupInterface[];
}
