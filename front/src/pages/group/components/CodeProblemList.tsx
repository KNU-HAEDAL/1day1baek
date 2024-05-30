import { ButtonWrapper } from '@components/button/ButtonWrapper';
import { DefaultButton } from '@components/button/DefaultButton';

import { IGroupTeamProps } from '@interfaces/GroupInterface';

import styled from '@emotion/styled';

const Table = styled.table`
  border-collapse: collapse;
  margin-top: 20px;
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;

  th,
  td {
    padding: 8px;
    border: 1px solid #dee2e6;
    font-size: var(--size-xxs);
  }
  th {
    font-weight: 600;
    background: var(--color-space-secondary);
    color: var(--color-white);
  }

  td {
    background: #f8f9fa;
    color: #333;
  }

  tr:nth-of-type(even) td {
    background: #e9ecef;
    color: #333;
  }

  tr:hover td {
    background: #dee2e6;
    color: #333;
  }
`;

const CommitUrl = styled.th`
  width: 314px;
  height: inherit;
`;

const CommitData = styled.td`
  width: 314px;
  max-width: 314px;
  height: inherit;
`;

const ProblemButton = styled(DefaultButton)`
  display: block;
  width: 50%;
  background: var(--color-space-secondary);
  color: white;
  font-size: var(--size-xs);
  padding: 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background: var(--color-space-tertiary);
    color: var(--color-white);
  }

  &:focus {
    outline: none;
    background: var(--color-space-tertiary);
  }
`;

const CodeProblemList = ({
  problems,
  formattedDate,
}: {
  problems: IGroupTeamProps[];
  formattedDate: string;
}) => {
  console.log(problems, formattedDate);

  const allProblems = problems.flatMap((user) => user.problems);

  return (
    <Table>
      <thead>
        <tr>
          <th>날짜</th>
          <th>사용자</th>
          <th>문제 제목</th>
          <th>등급</th>
          <CommitUrl>코드 보기</CommitUrl>
        </tr>
      </thead>
      <tbody>
        {allProblems.map((problem, index) => (
          <tr key={index}>
            <td>{problem.solvedDate}</td>
            <td>{problem.username}</td>
            <td>{problem.title}</td>
            <td>{problem.rank}</td>
            <CommitData>
              {/* <TableDataLink
                href={problem.commitUrl}
                target='_blank'
                rel='noopener noreferrer'
              >
                {problem.commitUrl}
              </TableDataLink> */}
              <ButtonWrapper display='flex' justifyContent='center'>
                <ProblemButton
                  onClick={() => window.open(problem.commitUrl)}
                  color='white'
                  backgroundColor='blue'
                >
                  해결한 코드 보러가기
                </ProblemButton>
              </ButtonWrapper>
            </CommitData>
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

export default CodeProblemList;
