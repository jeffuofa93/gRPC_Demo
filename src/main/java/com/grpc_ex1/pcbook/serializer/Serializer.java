package com.grpc_ex1.pcbook.serializer;

import com.google.protobuf.util.JsonFormat;
import com.grpc_ex1.pcbook.pb.Laptop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {
    public void WriteBinaryFile(Laptop laptop, String filename) throws IOException {
        FileOutputStream outStream = new FileOutputStream(filename);
        laptop.writeTo(outStream);
        outStream.close();
    }

    public Laptop ReadBinaryFile(String filename) throws IOException {
        FileInputStream intStream = new FileInputStream(filename);
        Laptop laptop = Laptop.parseFrom(intStream);
        intStream.close();
        return laptop;

    }

    public void WriteJSONFile(Laptop laptop, String s) throws IOException {
        JsonFormat.Printer printer = JsonFormat.printer()
                .includingDefaultValueFields()
                .preservingProtoFieldNames();

        String jsonString = printer.print(laptop);

        FileOutputStream outStream = new FileOutputStream(s);
        outStream.write(jsonString.getBytes());
        outStream.close();
    }
}
