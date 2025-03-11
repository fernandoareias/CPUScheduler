package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;

import java.util.PriorityQueue;

// Earliest Deadline First
public class EDF implements Scheduler {
    private PriorityQueue<Process> queue;

    public EDF() {
        // Ordena os processos pelo deadline
        this.queue = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.getDeadline(), p2.getDeadline()));
    }

    public void enqueue(Process process) {
        queue.offer(process);
    }

    public Process dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return queue.poll(); // Remove e retorna o processo com o menor deadline
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        // Uma PriorityQueue não tem limite fixo, então sempre retorna false
        return false;
    }

    public void run() {
        if (isEmpty()) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        System.out.println("Executando escalonador Earliest Deadline First (EDF)");

        while (!isEmpty()) {
            Process currentProcess = dequeue(); // Remove o processo com o menor deadline
            System.out.println("Executando: " + currentProcess);

            // Executa o processo até o fim (sem quantum)
            currentProcess.execute(currentProcess.getRemainingTime());

            if (currentProcess.isCompleted()) {
                System.out.println("Processo " + currentProcess.getName() + " concluído.");
            } else {
                System.out.println("Erro: O processo " + currentProcess.getName() + " não foi concluído.");
            }
        }
    }

    @Override
    public String toString() {
        return "EDF";
    }
}