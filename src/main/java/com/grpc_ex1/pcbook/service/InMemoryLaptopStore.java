package com.grpc_ex1.pcbook.service;

import com.grpc_ex1.pcbook.pb.Laptop;

import java.util.concurrent.ConcurrentMap;

public class InMemoryLaptopStore implements LaptopStore{
    private ConcurrentMap<String,Laptop> data;

    @Override
    public void Save(Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())) {
            throw new AlreadyExistsException("laptop ID already exists");
        }
        // deep copy laptop
        Laptop other = laptop.toBuilder().build();
        data.put(other.getId(), other);

    }

    @Override
    public Laptop Find(String id) {
        if (!data.containsKey(id)){
            return null;
        }
        Laptop other = data.get(id).toBuilder().build();
        return other;
    }
}
