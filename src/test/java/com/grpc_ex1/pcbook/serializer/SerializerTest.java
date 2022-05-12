package com.grpc_ex1.pcbook.serializer;

import com.grpc_ex1.pcbook.pb.Laptop;
import com.grpc_ex1.pcbook.sample.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SerializerTest {

    @Test
    void writeAndReadBinaryFile() throws IOException {
        String binaryFile = "laptop.bin";
        Laptop laptop1 = new Generator().NewLaptop();

        Serializer serializer = new Serializer();
        serializer.WriteBinaryFile(laptop1,binaryFile);

        Laptop laptop2 = serializer.ReadBinaryFile(binaryFile);
        Assertions.assertEquals(laptop1, laptop2);
    }

    public static void main(String[] args) throws IOException {
        Serializer serializer = new Serializer();
        Laptop laptop = serializer.ReadBinaryFile("laptop.bin");
        serializer.WriteJSONFile(laptop,"laptop.json");
    }
}