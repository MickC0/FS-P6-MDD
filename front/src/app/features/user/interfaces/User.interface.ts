import {Topic} from '../../topics/interfaces/Topic.interface';

export interface User {
  id: number;
  name: string;
  email: string;
  roles: string;
  subscriptions: Topic[];
}
