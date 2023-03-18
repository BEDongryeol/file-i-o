package io.whatap.data.ifs;

import io.whatap.io.data.DataReader;
import io.whatap.io.data.DataWriter;

public interface IPack {
    void write(DataWriter dw);

    void read(DataReader dr);

}
