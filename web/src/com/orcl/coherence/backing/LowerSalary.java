package com.orcl.coherence.backing;


import com.tangosol.util.processor.AbstractProcessor;
import com.tangosol.util.InvocableMap.Entry;

import com.orcl.coherence.model.Employees;


public class LowerSalary extends AbstractProcessor {
    public LowerSalary() {
    }
    
    public Object process(Entry entry ) {
        Employees emp = (Employees)entry.getValue();
        emp.setSalary(emp.getSalary() * 0.9);
        entry.setValue(emp);
        return null;
    }
}
