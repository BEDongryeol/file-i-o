package io.file.data.ifs;

import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;

public interface IPack {
    void write(DataWriter dw);

    void read(DataReader dr);

}
