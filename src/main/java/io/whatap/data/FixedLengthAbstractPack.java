package io.whatap.data;

import io.whatap.io.data.DataReader;
import io.whatap.io.data.DataWriter;
import lombok.ToString;

/**
 * Copyright whatap Inc since 2023/03/16
 * Created by Larry on 2023/03/16
 * Email : inwoo.server@gmail.com
 */
@ToString(callSuper = true)
public abstract class FixedLengthAbstractPack extends AbstractPack {

    protected FixedLengthAbstractPack() {
    }

    protected FixedLengthAbstractPack(long time, long projectCode, int agentId) {
        super(time, projectCode, agentId);
    }

    @Override
    public void write(DataWriter dw) {
        super.write(dw);
    }

    @Override
    public void read(DataReader dr) {
        super.read(dr);
    }
}
