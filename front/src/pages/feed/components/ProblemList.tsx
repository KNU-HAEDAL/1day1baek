import { ButtonWrapper } from '@components/button/ButtonWrapper';
import { DefaultButton } from '@components/button/DefaultButton';

import styled from '@emotion/styled';

interface IProblem {
  date: string;
  problem: string;
  difficulty: string;
}

const ProblemContainer = styled.div`
  background: linear-gradient(
    135deg,
    var(--color-space-primary),
    var(--color-space-secondary),
    var(--color-space-tertiary)
  );
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  width: 470px;
  max-width: 100%;
  color: white;
`;

const ProblemButton = styled(DefaultButton)`
  display: block;
  width: 100%;
  background: var(--color-space-secondary);
  color: white;
  font-size: 14px;
  padding: 8px;
  margin-top: 10px;
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

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background: var(--color-white);

  th,
  td {
    padding: 8px;
    border: 1px solid #dee2e6;
    font-size: var(--size-xs);
  }
  th {
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

const ProblemList = ({
  problems,
  formattedDate,
}: {
  problems: IProblem[];
  formattedDate: string;
}) => {
  return (
    <ProblemContainer>
      <Table>
        <thead>
          <tr>
            <th>문제</th>
            <th>난이도</th>
            <th>코드 보기</th>
          </tr>
        </thead>
        <tbody>
          {problems
            .filter((problem) => problem.date === formattedDate)
            .map((problem, index) => (
              <tr key={index}>
                <td>{problem.problem}</td>
                <td>{problem.difficulty}</td>
                <td>
                  <ButtonWrapper display='flex' justifyContent='center'>
                    <ProblemButton
                      onClick={() => alert('click')}
                      color='white'
                      backgroundColor='blue'
                    >
                      해결한 코드 보러가기
                    </ProblemButton>
                  </ButtonWrapper>
                </td>
              </tr>
            ))}
        </tbody>
      </Table>
    </ProblemContainer>
  );
};

export default ProblemList;
