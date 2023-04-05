package io.file.dto;

import lombok.Data;

@Data
public class ApplicationPackDTO extends AbstractPackDTO {
    private String content; // 로그 본문

    private long line; // 같은 시간에 수집된 라인 중 몇 번째 라인인지

}
