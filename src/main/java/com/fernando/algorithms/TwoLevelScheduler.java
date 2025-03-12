package com.fernando.algorithms;

import com.fernando.common.Process;
import com.fernando.common.Scheduler;

import java.util.ArrayDeque;
import java.util.Deque;

public class TwoLevelScheduler implements Scheduler {
    private static final int DEFAULT_TIME_QUANTUM = 5;
    private static final int DEFAULT_MAX_MEMORY = 3;


    private Deque<Process> diskQueue; // Fila de processos no disco (Nível 1)
    private Deque<Process> readyQueue; // Fila de processos na memória (Nível 2)
    private int timeQuantum; // Quantum para o escalonador Round Robin
    private int maxMemoryProcesses;

    public TwoLevelScheduler(){
        this(DEFAULT_TIME_QUANTUM, DEFAULT_MAX_MEMORY);
    }

    public TwoLevelScheduler(int timeQuantum, int maxMemoryProcesses) {
        this.diskQueue = new ArrayDeque<>();
        this.readyQueue = new ArrayDeque<>();
        this.timeQuantum = timeQuantum;
        this.maxMemoryProcesses = maxMemoryProcesses;
    }

    public void enqueue(Process process) {
        diskQueue.offerLast(process); // Adiciona o processo ao disco
        System.out.println("Processo " + process.getName() + " adicionado ao disco.");
    }

    public Process dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return readyQueue.pollFirst(); // Remove e retorna o primeiro processo da memória
    }

    public boolean isEmpty() {
        return diskQueue.isEmpty() && readyQueue.isEmpty();
    }

    public boolean isFull() {
        return readyQueue.size() >= maxMemoryProcesses;
    }


    public void moveProcessesToMemory() {
        while (!isFull() && !diskQueue.isEmpty()) {
            Process process = diskQueue.pollFirst();
            loadProcessToMemory(process);
        }
    }

    private void loadProcessToMemory(Process process) {
        readyQueue.offerLast(process);
        System.out.println("Movendo processo " + process.getName() + " do disco para a memória.");
    }

    private void writeToDiskIfCompleted(Process process) {
        if (process.isCompleted()) {
            System.out.println("Processo " + process.getName() + " concluído e removido da memória.");
        } else {
            System.out.println("Processo " + process.getName() + " movido de volta para o disco.");
            diskQueue.offerLast(process); // Escreve o processo de volta no disco
        }
    }

    public void run() {
        if (isEmpty()) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        System.out.println("Executando escalonador Two-Level com quantum: " + timeQuantum);

        while (!readyQueue.isEmpty()) {
            Process currentProcess = dequeue();

            if (!currentProcess.isCompleted()) {
                int executionTime = Math.min(timeQuantum, currentProcess.getRemainingTime());
                currentProcess.execute(executionTime);
                System.out.println("Executando " + currentProcess.getName() + " por " + executionTime + " unidades de tempo.");

                if (!currentProcess.isCompleted()) {
                    System.out.println("Processo " + currentProcess.getName() + " pausado. Tempo restante: " +
                            currentProcess.getRemainingTime());
                    readyQueue.offerLast(currentProcess);
                } else {
                    writeToDiskIfCompleted(currentProcess);
                }
            }
        }

        moveProcessesToMemory();
    }

    @Override
    public String toString() {
        return "TwoLevelScheduler";
    }
}