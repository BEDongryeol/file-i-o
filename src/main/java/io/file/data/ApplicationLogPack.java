package io.file.data;

import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import lombok.Getter;

@Getter
public class ApplicationLogPack extends AbstractPack {

    private String content; // 로그 본문
    private long line; // 같은 시간에 수집된 라인 중 몇 번째 라인인지

    public ApplicationLogPack(long projectCode, int agentId, long time, String content, long line) {
        super(time, projectCode, agentId);
        this.content = content;
        this.line = line;
    }

    private ApplicationLogPack(){}

    public static ApplicationLogPack create(DataReader dataReader){
        ApplicationLogPack appLog = new ApplicationLogPack();
        appLog.read(dataReader);
        return appLog;
    }

    @Override
    public void write(DataWriter dw) {
        super.write(dw);
        dw.writeString(this.content);
        dw.writeLong(line);
    }

    @Override
    public void read(DataReader dr) {
        super.read(dr);
        this.content = dr.readString();
        this.line = dr.readLong();
    }

}
