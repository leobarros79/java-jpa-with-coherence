package com.orcl.coherence.backing;


import com.tangosol.util.processor.AbstractProcessor;
import com.tangosol.util.InvocableMap.Entry;

import com.orcl.coherence.model.Employees;


public class RaiseSalary extends AbstractProcessor {
    public RaiseSalary() {
    }
    
    public Object process(Entry entry ) {
        Employees emp = (Employees)entry.getValue();
        emp.setSalary(emp.getSalary() * 1.10);
        entry.setValue(emp);
        return null;
    }
}
