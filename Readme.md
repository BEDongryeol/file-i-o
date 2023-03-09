<details>
<summary>Step 0</summary>
<div markdown="1">

**기본 지식**

- DataInput/DataOutput
  - Java에서 primitive type, String을 바이트 스트림으로 변환하여 입출력
  - FilterOutputStream을 상속받는 DataInputStream, DataOutputStream 클래스를 사용하면 추가 기능을 사용할 수 있다.


- DataOutputStream, ByteArrayOutputStream, FileOutputStream

|구분|특징|기타|
|----|----|---|
|DataOutputStream|기본 자료형 및 String을 바이너리 코드로 쓸 때 사용 |기본 자료형, String -> 파일|
|ByteArrayOutputStream|바이트 배열 메모리 버퍼에 데이터를 쓴다. 메모리 버퍼를 사용하기 때문에 파일이 아닌 바이트 배열에 데이터를 쓸 수 있다.|이미지, 바이너리 데이터 -> 바이트 배열|
|FileOutputStream|파일에 대한 FileDescriptor를 열어서 파일에 바이트 스트림으로 데이터를 쓸 수 있다.|파일에 데이터|


- RandomAccessFile
  - 파일의 원하는 위치로 이동(`seek()`)해서 데이터를 읽고 쓸 수 있다.
  - ThreadSafe하지 않다.
  - 모드
    - r : 읽기 전용
    - rw : 읽고 쓰기
    - rws : 읽고 쓰기 모드로 파일을 열고, 변경 사항은 파일과 동기화
    - rwd : 읽고 쓰기 모드로 파일을 열고, 변경 사항은 파일의 데이터 부분에 대해서만 동기화

</div>
</details>

<details>
<summary>Step 1</summary>
<div markdown="1">

**요구 사항** 

- File IO Util Class에 대한 이해
- API를 통한 File DB의 저장 및 조회에 대한 이해

**결과**

- File IO Util Class

  - ByteWriter
    - 여러 타입을 각 크기에 맞는 바이트 배열로 반환
    - 비트 연산자를 통한 각 배열 값 할당
    - & 0xFF, 형변환을 통한 unsigned 
    - String은 headerArray를 추가하여 바이트배열 길이 포함
  - DataWriter
    - 필드
      - int written : 입력된 바이트 수 추가
      - ByteArrayOutputStream bout :
      - DataOutput inner : 
    - 생성
      - 생성자 (private)
      - 팩토리 메서드
        - typeOfByteArray : ByteArrayOutput과 DataOutputStream을 사용
        - typeOfRandomAccessFile(RandomAccessFile file) : DataOutput에 RandomAccessFile 사용
    - 메서드
      - DataOutput에 값을 추가하는 메서드
      - ByteWriter에 의존

  - DataReader
    - 필드
      - int offset : custom buffer의 현재 위치
      - DataInputStream din
        - parameter
          - byte[] : parameter로 받은 byte 배열의 DataInputStream(BufferedInputStream(ByteArrayInputStream))
          - RandomAccessFile file : null
      - DataInput inner
        - parameter
          - Byte[] : 위에서 선언한 DataInputStream
          - RandomAccessFile file : file
    - 메서드
      - 바이트 배열의 값을 읽으면서, 해당 크기만큼 offset 추가
      - readString
        - header값을 읽기 위해 readInt 후 readBlob
        - String의 길이만큼 읽고 byte배열 return
        - 길이가 0일 경우 EMPTY("") return
  - FileReader
    - 4바이트 단위로 파일을 읽어오거나, 지정한 길이 만큼 읽어온다
    - 버프 최대 크기 4KB
  - FileWriter
    - File, byte[], boolean append를 인자로 갖는다
    - File이 없으면 canonical path를 생성하여, 파일을 생성
    - append를 true로 지정하면 파일을 이어서 작성

</div>
</details>

<details>
<summary>Step 2</summary>
<div markdown="1">

**요구 사항**
- 컬럼에 맞는 데이터 클래스를 정의하고, 클래스에 정보를 Write, Read하는 API 작성

**결과**

> 1. 에이전트에서 보내는 데이터 클래스 생성
- 같은 타입의 parameter가 있어 작성 시 실수를 방지하기 위해 dto를 parameter로 넘김
  - long : projectCode, time
  - int : agentId, status
- test code 작성을 위한 All Args constructor 추가
  - AbstractPackDTO에도 AllArgsConstructor 추가 (protected)

> 2. 파일이 없을 때 FileNotFoundException이 발생하는 것을 ExceptionHandler로 처리
- Response 시 결과에 맞는 응답을 내려주기 위해 ResponseMessage interface 생성
  - ErrorMessage 등 추후 추가 예정
  - 응답 메시지 관리용 enum MessageCode 추가
- 3번의 FileLoader에서 IOException 발생 시 catch하여 `FileLoadException(custom exception)` throw 
  
> 3. Repository에서 File 조회시 발생하는 중복 코드 처리
- io.whatap.repository.FileRepository

> 4. 새로운 db 파일 (log-request.db) 생성
- 기존 db 파일에 저장되는 데이터와 길이가 다르기 때문에 별도 파일에 데이터 저장

> 5. 테스트 코드 추가
   - 에이전트에서 와탭 서버로 전송되는 객체 <-> byte[] 변환 테스트
</div>
</details>

<details>
<summary>Step 3</summary>
<div markdown="1">

**요구 사항**
- 샘플데이터와 같은 포멧의 데이터가 여러 개 저장되어 있을 때, 특정 순서의 데이터 조회

**결과**
- 고정 길이 구하기
  - AbstractPack 자식 클래스의 고정 길이를 구하여 byte[]을 생성해주는 ByteArrayProvider 생성
- 특정 순서의 데이터 조회
  - file.seek()
    - parameter : `생성된 바이트 배열의 길이 * 특정순서(index)`
  - file.readFully()
    - 고정 길이만큼 데이터 읽기를 시도
    - read()를 사용하면 파일 끝에 도달하면 -1이 return
    - readFully()는 EOFException 발생
    - 데이터를 고정 길이만큼 읽지 못하고, -1이 발생하면 의도치 않은 객체가 생성될 수 있으므로 readFully() 사용

</div>
</details>