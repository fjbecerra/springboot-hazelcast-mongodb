package com.pakius.person.config;

import com.hazelcast.core.IMap;
import com.pakius.person.model.Person;

public class OperationsMap {

    private IMap<String, Person> map;

    public OperationsMap(IMap<String, Person> map)
    {
        this.map = map;
    }

    public IMap<String, Person> getMap() {
        return map;
    }
}
