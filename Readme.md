### Step 1

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
