# PAYHERE
기간 : 2023.04.11 ~ 2023.04.16
<hr>


### 🏗 프로젝트 아키텍쳐   
![Payhere 아키텍쳐 이미지](https://user-images.githubusercontent.com/81751267/232327900-44563bdc-937c-4083-b252-b75a37f77159.png)
- JWT Refresh Token 관리를 위해 Redis를 활용하였습니다.
- 로그인, JWT 활용 편의성을 위해 Spring Security를 적용하였습니다.
- 서버 : Java 1.8 / Spring Boot 2.7.10
- Data : MySQL 5.7 / Redis
- JPA (Hibernate), Spring Security, JWT 등으로 개발하였습니다.
<hr>

### ▶ 실행 방법
- 프로젝트 Root 디렉토리 명령 프롬프트(터미널)에서 다음 커맨드를 입력합니다. 
```java
docker-compose up --build -d
```

<br>
<hr>

### ⚙ ERD
![payhere](https://user-images.githubusercontent.com/81751267/232324349-95900ed1-af6e-4a36-8386-51c3ef6a04d9.png)
#### ERD 링크 : [ERDcloud](https://www.erdcloud.com/p/wjhHtxyw3y8Ae7dSk)

### ⚙ ERD 설계 코멘트

- **상품 테이블 초성 이름 필드**   
    상품 등록, 수정 시 해당 상품의 초성을 분리하여 저장하는 필드입니다.
    초성 검색을 위해 JPA 함수를 2가지로 분리하여 초성 검색인 경우 초성 이름 필드에서, 단어 검색인 경우 이름을 기준으로 탐색합니다.
    또한, SQL쿼리를 사용하여 매 쿼리 마다 함수를 적용하는 법을 고려할 수 있습니다.
<br>

- **회원과 상품 테이블의 구성**   
    요구사항 상으로는 식별관계로 지정하여 PK를 활용함으로서 데이터 정합성과 인덱스의 이점을 가질 수 있습니다.   
    하지만 서비스의 확장 시 사업장, 주문 등의 테이블이 회원과 상품 테이블 사이에 위치할 수 있고, 프렌차이즈 운영이나   
    공동 사장의 구현 등 시스템의 변경이 필요한 상황에 테이블 확장의 가능성이 아주 높아서 필수적 비식별 1 : N 관계로 구성하였습니다.
<br>

- **각 테이블의 기본 키로 INT와 BIGINT 타입을 사용**   
    상품 데이터가 많이 쌓일 것이라 판단하여 BIGINT로 설정하였습니다.   
    보안이나 데이터베이스 분산 시 고유성을 고려하여 회원과 상품 데이터의 기본 키를 UUID로 지정하는 방법도 있습니다.   
    하지만 현재 요구사항을 기준으로 서비스의 환경이나 처리속도와 id 노출 시 보안 위험 사이의 경중을 판단하기 어려워 보류하였습니다.   
<br>

- **Member Entity 구분**   
    Member와 Owner를 상속관계로 두어 다양한 유형의 회원을 만들 수 있도록 하였습니다.  
<br>

- **크기 지정 및 기타**   
    - 회원타입(DTYPE)은 JPA 사용 시 Entity 상속관계로 파생된 필드입니다.
    - 회원 ROLE 테이블은 Spring Security로 인하여 파생된 테이블입니다. Role과 Member의 관계를 맵핑합니다.
    - 상품의 바코드의 형식이 명시되어 있지 않기 때문에, 일반적인 규약에 따라 공용 숫자 13자리 혹은 14자리를 기준으로 설정하였습니다.
    - 유통기한이 앞으로 남은 N일을 의미하는지, XX월 XX일 XX시 XX분을 의미하는지 확실하지 않아 정확한 기한을 표시할 수 있는 후자를 택하였습니다.
    - 사이즈는 요구 명세에 small or large이므로 true/false로 db에 저장하는 것을 고려했으나, 사이즈 추가로 변경이 필요할 수 있어 VARCHAR(5)로 설정하였습니다.
    - 요구사항이 특정되지 않은 문자열 필드는 VARCHAR의 짧게 나타낼 수 있는 크기 중 가장 큰 255를 설정합니다.   
    - 상품 설명은 1000자로 가정하였습니다.   
<br>

<hr>

### 🗝 API 및 테스트 케이스   
- **d**   
<br>

<hr>

### 🧪 고민해 볼 점 및 후기   
- **d**   
<br>

<hr>

### 💧 프로젝트 디렉토리   
```java
├─gradle
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─spamallday
    │  │          └─payhere
    │  │              ├─auth
    │  │              ├─configuration
    │  │              ├─controller
    │  │              ├─dto
    │  │              ├─entity
    │  │              ├─enums
    │  │              ├─exception
    │  │              ├─repository
    │  │              ├─service
    │  │              └─util
    │  └─resources
    └─test
        └─java
            └─com
                └─spamallday
                    └─payhere
                        └─domain
```
<br>
