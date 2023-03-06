package io.whatap.data.ifs;

import io.whatap.io.DataReader;
import io.whatap.io.DataWriter;

public interface IPack {
    void write(DataWriter dw);

    void read(DataReader dr);

}
