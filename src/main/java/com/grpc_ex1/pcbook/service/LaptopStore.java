package com.grpc_ex1.pcbook.service;

import com.grpc_ex1.pcbook.pb.Laptop;

public interface LaptopStore {
    void Save(Laptop laptop) throws Exception;
    Laptop Find(String id);

}
