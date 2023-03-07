package io.whatap.data;

import io.whatap.dto.RequestPackDTO;
import io.whatap.io.DataReader;
import io.whatap.io.DataWriter;
import lombok.Getter;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Getter
public class RequestLogPack extends AbstractPack {

    private int status;
    private long responseTime;

    private RequestLogPack(long projectCode, int agentId, long time, int status, long responseTime) {
        super(time, projectCode, agentId);
        this.status = status;
        this.responseTime = responseTime;
    }

    private RequestLogPack() {
    }

    @Override
    public void write(DataWriter dw) {
        super.write(dw);
        dw.writeInt(status);
        dw.writeLong(responseTime);
    }

    @Override
    public void read(DataReader dr) {
        super.read(dr);
        this.status = dr.readInt();
        this.responseTime = dr.readLong();
    }

    public static RequestLogPack valueOf(RequestPackDTO dto) {
        return new RequestLogPack(dto.getProjectCode(), dto.getAgentId(), dto.getTime(), dto.getStatus(), dto.getResponseTime());
    }

    public static RequestLogPack create(DataReader dataReader) {
        RequestLogPack requestLog = new RequestLogPack();
        requestLog.read(dataReader);
        return requestLog;
    }
}
