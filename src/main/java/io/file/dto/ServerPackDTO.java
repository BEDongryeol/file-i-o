package io.file.dto;

import lombok.Data;

@Data
public class ServerPackDTO extends AbstractPackDTO {
    private String content; // 로그 본문

    private String fileName; // 로그가 수집된 파일 이름
    private long line; // 파일에서 몇 번째 라인인지
}
