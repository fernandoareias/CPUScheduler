package com.fernando;

import com.fernando.common.Process;
import com.fernando.common.Scheduler;
import com.fernando.common.SchedulerFactory;

public class Main {
    public static void main(String[] args) {

        if (args.length != 4 || !args[0].equals("--algorithm") || !args[2].equals("--processors")) {
            System.out.println("Exemplo de uso: --algorithm fcfs --processors 10");
            return;
        }

        String algorithmName = args[1];
        int processorCount;

        try {
            processorCount = Integer.parseInt(args[3]);
            if (processorCount <= 0) {
                throw new IllegalArgumentException("O número de processadores deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: O número de processadores deve ser um valor inteiro válido.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        Scheduler scheduler;
        try {
            scheduler = SchedulerFactory.getInstance().get(algorithmName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Foi selecionado o algoritmo: " + scheduler);

        long startTimeProcessCreation = System.nanoTime();

        for (int i = 0; i < processorCount; i++) {
            String name = "P" + (i + 1);
            int arrivalTime = i;                      // Tempo de chegada
            int burstTime = (i + 1) * 2;              // Tempo de execução
            int deadline = arrivalTime + burstTime + 5; // Prazo (deadline)

            scheduler.enqueue(new Process(name, arrivalTime, burstTime, deadline));
        }

        long endTimeProcessCreation = System.nanoTime();
        long processCreationTime = endTimeProcessCreation - startTimeProcessCreation;

        long startTimeSchedulerRun = System.nanoTime();

        scheduler.run();

        long endTimeSchedulerRun = System.nanoTime();
        long schedulerRunTime = endTimeSchedulerRun - startTimeSchedulerRun;

        System.out.println("Tempo total para criar os processos: " + processCreationTime / 1_000_000.0 + " ms");
        System.out.println("Tempo total para executar o escalonador: " + schedulerRunTime / 1_000_000.0 + " ms");
    }
}