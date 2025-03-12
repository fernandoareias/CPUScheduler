package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;

import java.util.ArrayDeque;
import java.util.Deque;

// Cmpatible Time-Sharing System
public class CTSS implements Scheduler {
    private static final int DEFAULT_QUANTUM = 5; // Quantum padrão
    private Deque<Process> queue;                // Fila de processos (Deque para eficiência)
    private int quantum;                         // Fatia de tempo fixa

    public CTSS() {
        this(DEFAULT_QUANTUM);
    }

    public CTSS(int quantum) {
        this.queue = new ArrayDeque<>();
        this.quantum = quantum;
    }

    public void enqueue(Process process) {
        queue.offerLast(process); // Adiciona o processo ao final da fila
    }

    public Process dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return queue.pollFirst(); // Remove e retorna o primeiro processo da fila
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        // Uma Deque não tem limite fixo, então sempre retorna false
        return false;
    }

    public void run() {
        if (isEmpty()) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        System.out.println("Executando escalonador CTSS com quantum: " + quantum);

        while (!isEmpty()) {
            Process currentProcess = dequeue();
            System.out.println("Executando: " + currentProcess);

            // Executa o processo por no máximo 'quantum' unidades de tempo
            int executionTime = Math.min(quantum, currentProcess.getRemainingTime());
            currentProcess.execute(executionTime);

            if (!currentProcess.isCompleted()) {
                System.out.println("Processo " + currentProcess.getName() + " pausado. Tempo restante: " +
                        currentProcess.getRemainingTime());
                enqueue(currentProcess); // Retorna o processo ao final da fila
            } else {
                System.out.println("Processo " + currentProcess.getName() + " concluído.");
            }
        }
    }

    @Override
    public String toString() {
        return "CTSS";
    }
}