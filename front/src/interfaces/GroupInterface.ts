export interface IGroupProps {
  id: number;
  name: string;
  isPrivate: boolean;
  isMember: boolean;
}

export interface ICreateGroupModalProps {
  name: string;
  isPrivate: boolean;
  password?: string;
  goalSolveCount: number;
  fine: number;
}
