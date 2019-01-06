package com.iSA.fibonnaci.cdi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FibonnaciBean {
    public List<BigDecimal> fibonacciNumbers(Integer number) {

        List<BigDecimal> out = new ArrayList<>();
        for(int i=0; i<= number ;i++){
            if (i==0 || i==1){
                out.add((BigDecimal.valueOf(i)));
            } else {
                out.add(out.get(i-1).add(out.get(i-2)));
            }
        }

        return out;
    }
}
