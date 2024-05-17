# 1day 1baek
1일 1백준을 위한 그룹 스터디 서비스

# 개발 인원
| [<img src="https://github.com/KimKyuHoi.png">](https://github.com/KimKyuHoi) | [<img src="https://github.com/gidskql6671.png">](https://github.com/gidskql6671) |
| :-----: | :-----: |
| [김규회](https://github.com/KimKyuHoi) | [김동환](https://github.com/gidskql6671) |


# Git Convention
## Issue Convention
개발 작업을 하기 전 이슈를 먼저 등록한다. 이는 아래 브렌치 전략과 엮어서 개발을 진행하기 위함이다.  
이슈는 지정된 이슈 템플릿을 사용하여 작성한다.

## Branch Strategy
- main : 배포용 브렌치
- feat/{이슈_번호}-{작업명} : 피쳐 개발 브렌치

배포는 main 브렌치를 사용하여 진행된다.  
기능 개발은 feature 브렌치를 파서 진행하며, 네이밍 규칙은 상기된 것과 같이 이슈 번호와 작업명을 조합한다.  
개발이 완료되면, PR을 통해 main에 머지한다.

## PR Convention
각 기능의 개발은 feature 브렌치를 통해 진행되며, 이후 PR을 통해 main에 머지된다.  
PR에 대한 설명은 지정된 PR 템플릿을 사용하여 작성한다.

# 배포 전략
미정