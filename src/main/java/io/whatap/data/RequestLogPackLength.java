package io.whatap.data;

import io.whatap.common.io.support.ByteLengthProvider;
import io.whatap.dto.RequestPackDTO;
import io.whatap.io.data.DataReader;
import io.whatap.io.data.DataWriter;
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
public class RequestLogPackLength extends FixedLengthAbstractPack {

    private int status;
    private long responseTime;

    private RequestLogPackLength(long projectCode, int agentId, long time, int status, long responseTime) {
        super(time, projectCode, agentId);
        this.status = status;
        this.responseTime = responseTime;
    }

    private RequestLogPackLength() {
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

    public static RequestLogPackLength valueOf(RequestPackDTO dto) {
        return new RequestLogPackLength(dto.getProjectCode(), dto.getAgentId(), dto.getTime(), dto.getStatus(), dto.getResponseTime());
    }

    public static RequestLogPackLength create(DataReader dataReader) {
        RequestLogPackLength requestLog = new RequestLogPackLength();
        requestLog.read(dataReader);
        return requestLog;
    }

    public static List<RequestLogPackLength> createLogs(DataReader dataReader) {
        int available = dataReader.available();
        int count = available / ByteLengthProvider.REQUEST_LOG_PACK;

        List<RequestLogPackLength> logs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            logs.add(create(dataReader));
        }
        return logs;
    }

}
