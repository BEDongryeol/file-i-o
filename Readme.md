<details>
<summary>1주차 </summary>
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
<summary>Step 1. File IO Util Class 이해</summary>
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
<summary>Step 2. 고정길이 데이터 1개 저장 및 조회</summary>
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
<summary>Step 3. M번째 데이터 조회</summary>
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

---

</div>
</details>

<details>
<summary>2주차 사전 정리</summary>
<div markdown="1">

## TODO

- BinarySearch 최적화
- 피드백 반영

|                      |     RandomAccessFile     | vs.  |                         FileChannel                          |
| :------------------: | :----------------------: | :--: | :----------------------------------------------------------: |
|    특정 위치 접근    | random하게 접근 (seek()) |  >   | FileChannel -> FileChannelImpl -> IOUtils -> FileDispatcherImpl -> native method (절대 위치로 읽어온다) |
|  데이터 전송 (횟수)  |  1바이트 * 데이터 크기   |  <   |                            버퍼링                            |
|      IO (횟수)       |  1바이트 * 데이터 크기   |  <   |                             1번                              |
| 데이터 읽어오는 방법 |   jvm - os (byte[] 등)   |  <   | DirectBuffer 가능 (FileChannelImpl에서 NativeThread를 추가해서 초과 시 2배로, 최초 NativeThreadSet : 2개) |
|      대용량처리      |                          |  <   |                         block, chunk                         |

- RandomAccessFile이 데이터 탐색 시 random하게 접근하는데에 최적화된 클래스이고, position은 비교적 크기가 작은 데이터이므로  RandomAccessFile 선택

- RandomAccessFile : 단일 데이터 조회, position (index)
- FileChannel : 여러 개 데이터 조회

### Step4. 시간으로 고정길이 데이터 1개의 position 조회하기

- **프로세스**
  - RandomAccesFile에서 binarySearch를 통해 데이터 position 찾기

    - 데이터가 존재할 때 : return

    - 데이터가 존재하지 않을 때
      - 특정 값을 전달 : `-1` 등을 전달해주면 정상처리된 줄 알고 해당 값을 사용하는 다른 프로세스에서 에러 발생 가능
      - **빈 값을 전달 : Optional을 통한 처리 가능**

    - IOException, EOFException이 발생했을 때

### Step5. 고정길이 데이터 M번째부터 연속된 K개 조회하기

- 최대 1024개로 제한, 설정된 ByteBufferPool의 사이즈로 수정 가능

- **프로세스**

  - RandomAccesFile에서 binarySearch를 통해 데이터 position 찾기 (Step4의 메소드 재사용)

    - 데이터가 존재할 때

      - ```
        do {
        	seek(position);
        	readFully(long size byte);
        	position += dataFixedSize;
        	n--;
        } while (n > 0)
        ```

      - 중간에 EOF 등이 발생했을 때는 log로 찍고, 지금까지 저장한 데이터만 return

    - 데이터가 존재하지 않을 때

    - EOF Exception이 발생했을 때 

      - 중간에 EOF 등이 발생했을 때는 log로 찍고, 지금까지 저장한 데이터만 return

**시작점(position)을 찾을 때는 RandomAccessFile**

**인덱스를 받아와서 여러 값을 동시에 읽으려면 pointer를 이동, 읽기를 하는 RandomAccessFile이 아닌 FileChannel을 통해 비동기로 한 번에 다 읽어 오기**



Step3에서 사용한 고정 길이 데이터 1개 조회하는 거 -> 몇 번 째 데이터인지 알고 있어야한다.\

- 메소드

  1. 데이터의 position을 return 해주는 메소드

  2. FileChannel을 통해 절대 위치에 데이터를 write하는 메소드

     - 1번 메소드를 사용하고 있어도 position에 관계 없이 절대 위치에 write 할 수 있으므로 처리 가능

     - #### readBytesAt은 channel을 사용하자.

  3. 데이터 길이를 효율적으로 읽어 들일 수 있는 방법

     1. ByteArrayProvider : 데이터가 변경되면 이전 데이터를 읽어올 때 충돌 발생

     2. 데이터를 저장할 때 메타데이터도 같이 저장

        1. 데이터 제일 앞에 메타데이터 삽입

           - 문제점

             1. Channel을 사용하면 ByteBuffer를 매번 생성
                - 해결 가능 여부 : ByteBuffer.slice()로 할당해놓은 버퍼 사용? 

             2. 메타데이터 저장으로 용량 증가
                - 32byte + 알파

             3. `기존 데이터에 메타데이터가 없을 경우?`

        2. 파일의 0번 라인에 저장해서 파일 분할?

           - 문제점
             - 기존 로직에 영향을 미칠 수 있음

- 현재 문제점

  - **FileReader**
    - ByteBuffer에서 byte를 읽어올 때 byteBuffer.limit으로 크기를 지정하는데 객체 별 크기를 지정해주어야함
    - 어떤 클래스를 몇 개 가져올 지 추가해주어야 할 듯
    - **DTO로 해결**

### Step6. 시간 범위에 맞는 데이터 조회

- 시간 범위를 현재까지로 validation

</div>
</details>

<details>
<summary>Step 4. Time 필드로 데이터 시작위치 찾기</summary>
<div markdown="1">

**요구 사항**
- Time 필드를 기준으로 오름차순 되어 정렬되어 있는 데이터를 `Time 값`을 통해 효율적으로 찾기
- Binary Search 사용

**결과**
- Controller
  - unixTime을 사용하므로 path variable 값에 대한 validation 설정
    - 조회 기준 현재 값 이전의 날짜만 조회 가능 

- Service
  - FileService에서 BinarySearchSupport 객체를 생성
  - BinarySearchSupport
    - `특정 필드를 통해 읽을 때 binarySearch를 통해 데이터를 읽을 수 있는 클래스`
    - RandomAccessFile, byteLength(객체 1개당 데이터 크기)를 인자로 받는다.
      - 팩토리 메소드 사용해서, 생성자에서 RAF 길이 조회 시 exception 발생 시 throw
        - IO 클래스 접근 시 exception 발생하면 controller 전달
    - getPosition() 메소드 통해서 binarySearch
      - 데이터를 못찾을 시 controller 전달
    - position return 받으면 해당 위치부터 데이터의 고정 크기만큼 readFully
        
</div>
</details>

<details>
<summary>Step 5. M번째 데이터부터 연속된 K개 조회</summary>
<div markdown="1">

**요구 사항**
- 고정 길이 데이터 N개 조회
- M번째 데이터부터 연속된 K개 조회

**결과**
- Controller
  - parameter로 시작 index와 개수 count를 받는다.
  - 최대 조회 수 validation
    - 한 번에 많은 수의 데이터 요청 OOM 방지

- Service
  - IndexQueryDTO
    - M번째부터 연속된 K개를 조회
      - 해당 position부터 `고정된 크기 * 조회하려는 데이터 개수` 만큼 읽어오도록 구현
    - Reader에 데이터를 넘기기 전에 데이터의 고정된 크기, startIndex, count를 가진 query객체 생성
      - 추후 다른 고정 크기 데이터가 생겼을 때 팩토리 메서드 추가 등 반복 코드 감소
  - FixedLengthReader
    - `특정 포지션, 인덱스를 통해 고정 데이터를 읽을 수 있는 클래스`
    - readAllFromIndexTo
      - `인덱스와 바이트 크기`로 데이터의 첫 위치를 구하고
      - `개수`를 통해서 총 읽어올 바이트 크기를 구한다.
      - 데이터 여러 개를 읽어올 때는 FileChannel 통해서 DirectBuffer 사용
        - ByteBufferPool 클래스를 생성해서 미리 할당 후 사용 가능한 버퍼 조회
        - 찾지 못할 시 orElseGet() 통해서 재귀적으로 호출
          - orElse() 실행할 경우 데이터를 메소드가 추가적으로 실행된다.
        - Controller의 1024개와 버퍼 사이즈를 맞추어 두었음 (버퍼 32 * 1024)
          - 기존에 설정한 DirectBuffer 크기보다 데이터보다 요청한 데이터 수가 많을 때 underflow가 발생할 수 있으므로
          - 딱 맞는 크기의 바이트 배열 생성
</div>
</details>

<details>
<summary>Step 6. 시간 범위로 데이터 조회</summary>
<div markdown="1">

**요구 사항**
- 시계열 데이터를 통해 사이의 데이터 조회하기

**결과**
- Controller
  - path variable로 시간 받아오기

- Service
  - BinarySearchSupport
    - 이진 탐색을 할 때 최초에 중간에 있는 시간을 확인한다
    - 이 때 중간에 있는 시간 값을 저장해두고
      - 요청 시간의 크기에 따라 분기 처리
    - 조회 시작, 끝 시간이 모두 midTime보다 작으면 최대 탐색 구간을 midPosition으로 설정
    - 끝 시간은 최소 탐색 구간을 조회 시작 시간을 통해 얻은 위치부터 실행
  - 시작, 끝 시간의 파일 내 position을 구하여, 이 사이의 바이트 크기를 추출
  - 조회를 시작한 시간부터 해당 바이트만큼 읽어온다
</div>
</details>
