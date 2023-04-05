package io.file.data;

import io.file.common.io.support.ByteLengthProvider;
import io.file.dto.RequestPackDTO;
import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Getter
@ToString(callSuper = true)
public class RequestLogPack extends FixedLengthAbstractPack {

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

    public static List<RequestLogPack> createLogs(DataReader dataReader) {
        int available = dataReader.available();
        int count = available / ByteLengthProvider.REQUEST_LOG_PACK;

        List<RequestLogPack> logs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            logs.add(create(dataReader));
        }
        return logs;
    }

}
