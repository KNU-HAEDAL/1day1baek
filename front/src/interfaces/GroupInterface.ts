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

export interface ISelectGroupProps {
  id: number;
  name: string;
  isPrivate: boolean;
  inviteCode: string;
  goalSolveCount: number;
  fine: number;
  isMember: boolean;
  users: ISelectUsersProps[];
  owner: ISelectUsersProps;
}

export interface ISelectUsersProps {
  loginId: string;
  name: string;
  profileUrl: string;
}

export interface IGroupTeamProps {
  problems: IGroupProblemProps[];
  username: string;
  userId: number;
}

export interface IGroupProblemProps {
  solvedDate: string;
  commitUrl: string;
  username: string;
  title: string;
  rank: string;
}
