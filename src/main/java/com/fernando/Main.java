package com.fernando;

import com.fernando.common.Process;
import com.fernando.common.SchedulerFactory;

public class Main {
    public static void main(String[] args) {

        if(args.length != 4)
            System.out.println("Example: --algorithm fcfs --processors 10");

        var scheduler = SchedulerFactory.getInstance().get(args[1]);
        System.out.println("Foi selecionado o algoritmo: " + scheduler.toString());
        var x = 0;
        while(x <= Integer.parseInt(args[3]))
        {
            scheduler.enqueue(new Process());
            x++;
        }

        scheduler.run();
    }


}