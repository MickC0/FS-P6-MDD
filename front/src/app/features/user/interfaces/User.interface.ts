export interface User {
  id: number;
  name: string;
  email: string;
  roles: string;
  subscriptions: Topic[];
}
