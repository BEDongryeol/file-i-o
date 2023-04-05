package io.file.data;

import io.file.data.ifs.IPack;
import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class AbstractPack implements IPack {

    private long time; // 로그가 생성된 시간
    private long projectCode; // 프로젝트 번호
    private int agentId; // 에이전트 아이디

    protected AbstractPack() {
    }

    public AbstractPack(long time, long projectCode, int agentId) {
        this.time = time;
        this.projectCode = projectCode;
        this.agentId = agentId;
    }

    @Override
    public void write(DataWriter dw) {
        dw.writeLong(time);
        dw.writeLong(projectCode);
        dw.writeInt(agentId);
    }

    @Override
    public void read(DataReader dr) {
        this.time = dr.readLong();
        this.projectCode = dr.readLong();
        this.agentId = dr.readInt();
    }

}
