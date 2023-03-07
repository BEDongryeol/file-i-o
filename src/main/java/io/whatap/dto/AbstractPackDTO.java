package io.whatap.dto;

import lombok.Data;

@Data
public abstract class AbstractPackDTO {
    private long projectCode; // 프로젝트 번호
    private int agentId; // 에이전트 아이디
    private long time; // 로그가 생성된 시간

    protected AbstractPackDTO() {
    }

    protected AbstractPackDTO(long projectCode, int agentId, long time) {
        this.projectCode = projectCode;
        this.agentId = agentId;
        this.time = time;
    }
}
