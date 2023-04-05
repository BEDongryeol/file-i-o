package io.file.data;

import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import lombok.Getter;

@Getter
public class ServerLogPack extends AbstractPack {

    private String content; // 로그 본문
    private String fileName; // 로그가 수집된 파일 이름
    private long line; // 파일에서 몇 번째 라인인지

    public ServerLogPack(long projectCode, int agentId, long time, String content, String fileName, long line) {
        super(time, projectCode, agentId);
        this.content = content;
        this.fileName = fileName;
        this.line = line;
    }

    public static ServerLogPack create(DataReader dataReader){
        ServerLogPack serverLog = new ServerLogPack();
        serverLog.read(dataReader);
        return serverLog;
    }

    private ServerLogPack(){}

    @Override
    public void write(DataWriter dw) {
        super.write(dw);
        dw.writeString(content);
        dw.writeString(fileName);
        dw.writeLong(line);
    }

    @Override
    public void read(DataReader dr) {
        super.read(dr);
        this.content = dr.readString();
        this.fileName = dr.readString();
        this.line = dr.readLong();
    }

}
