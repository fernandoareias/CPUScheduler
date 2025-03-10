package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin implements Scheduler {
    private static final int DEFAULT_QUANTUM = 4; // Quantum padrão
    private List<Process> queue;                  // Fila de processos
    private int quantum;                          // Fatia de tempo (quantum)

    public RoundRobin() {
        this(DEFAULT_QUANTUM);
    }

    public RoundRobin(int quantum) {
        this.queue = new ArrayList<>();
        this.quantum = quantum;
    }

    public void enqueue(Process process) {
        queue.add(process);
    }

    public Process dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return queue.remove(0); // Remove e retorna o primeiro processo da fila
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        // Uma lista não tem limite fixo, então sempre retorna false
        return false;
    }

    public void run() {
        if (isEmpty()) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        System.out.println("Executando escalonador Round Robin com quantum: " + quantum);

        while (!isEmpty()) {
            Process currentProcess = dequeue();
            System.out.println("Executando: " + currentProcess);

            int executionTime = Math.min(quantum, currentProcess.getRemainingTime());
            currentProcess.execute(executionTime);

            if (!currentProcess.isCompleted()) {
                System.out.println("Processo " + currentProcess.getName() + " pausado. Tempo restante: " +
                        currentProcess.getRemainingTime());
                enqueue(currentProcess);
            } else {
                System.out.println("Processo " + currentProcess.getName() + " concluído.");
            }
        }
    }

    @Override
    public String toString() {
        return "RoundRobin";
    }
}