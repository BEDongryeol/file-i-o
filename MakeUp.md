## Feedback 반영

### 1. RequestLogRepository

- 데이터가 저장되고 로직이 정상적으로 수행되면 Boolean.TRUE
- or IOException

### 2. 데이터 사이즈 불러오기

- ByteLengthProvider에 각 바이트 크기를 static하게 초기화시켜서 이를 사용
- 문제점
    - 데이터 필드 변경에 의한 오류 발생 가능
        1. DataLength를 담는 field추가 (메타데이터)
            - 기존에 메타데이터를 가지지 않을 필드에 접근 시
                - 버전 별로 데이터를 관리 (파일 이름으로 분류?)
                    - ex) file name에 `ver.2`가 있으면 version 2의 DataLength 읽어오기

### 3. Controller - Validation

### 4. 파일의 분할

- 시간, agentId에 따라 파일을 분할하는데 어떠한 파일을 읽어올지 선택
    1. request에 시간이 long으로 들어오면 LocalDate로 받아오기
    2. 파일 base name을 저장해놓은 클래스에서 날짜 값 추가

### 5. 데이터 저장 시 로깅 추가

- AOP로 해결하면 될 듯
    - url을 어떻게 묶을 것인지 (**Interceptor, Filter**)
    - 클래스를 어떻게 묶을 것인지 (CRUD 작업이 있는 Controller에서 save만 executive)

### 6. Exception 처리

- RandomAccessFile 등 IO 클래스에서 exception이 발생하면 controller까지 전달
- 로그 파일의 rolling 주기를 하루로 가정하고, 시간을 통해 저장할 때 파일이 없어서 FileNotFound 발생 시 파일 생성 시도 (최대 5회)
    - FileRepository의 createAndGetFile(String fileName)
        + 인덱스, 순서 등으로 불러올 때는 `원하는 날짜`의 몇 번째인지 알수 있는 날짜 값을 받아서 보완하면 좋을 거 같다

---

## Feedback 미반영

### 1. 대량의 데이터 조회 중 Exception

- 여러 데이터를 불러오다가 실패하면 어디까지 조회할 것인지
    - 범위로 불러오다가 실패하면 특정 범위, 빈 값
    - OS에서 읽을 때
        - EOF 시 마지막까지 읽기
    - 객체로 변환할 때
        - continue

### 2. 파일에 대한 동시 접근

- FileLock
- 어디까지 영향이 있을지

### 3. index 관리

- IndexFIle에 대한 파일 관리 등

파일 처리, 데이터 여러 개 불러오다가 실패 시

