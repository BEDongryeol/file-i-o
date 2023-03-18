package io.whatap.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public abstract class AbstractPackDTO {

    @Min(value = 1, message = "프로젝트 번호는 1 이상이어야 합니다.")
    private long projectCode; // 프로젝트 번호
    @Min(value = 1, message = "Agent Id는 1 이상이어야 합니다.")
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
