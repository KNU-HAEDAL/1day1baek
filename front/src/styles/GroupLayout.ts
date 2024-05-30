import styled from '@emotion/styled';

export const TitleLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
  width: 100%;
  height: 45px;
  border-bottom: 1px solid var(--color-gray);
  margin-bottom: 10px;
`;

export const PublicToken = styled.div`
  width: 50px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  border: 1px solid var(--color-black);
  background-color: var(--color-white);
  margin-left: 10px;
`;

export const ContentLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  height: calc(100% - 45px);
`;
