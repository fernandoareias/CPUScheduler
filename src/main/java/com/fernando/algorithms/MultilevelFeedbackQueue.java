package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;

import java.util.ArrayList;
import java.util.List;

public class MultilevelFeedbackQueue implements Scheduler {
    private static final int DEFAULT_QUEUE_COUNT = 3; // Número padrão de filas
    private static final int DEFAULT_QUANTUM_BASE = 4; // Quantum base para a primeira fila
    private List<List<Process>> queues;               // Lista de filas
    private int[] quantum;                            // Quantum para cada fila

    public MultilevelFeedbackQueue() {
        this(DEFAULT_QUEUE_COUNT);
    }

    public MultilevelFeedbackQueue(int queueCount) {
        queues = new ArrayList<>();
        quantum = new int[queueCount];

        for (int i = 0; i < queueCount; i++) {
            queues.add(new ArrayList<>());
            quantum[i] = DEFAULT_QUANTUM_BASE * (i + 1); // Quantum cresce com a fila
        }
    }

    @Override
    public void enqueue(Process process) {
        // Adiciona o processo à fila de maior prioridade (fila 0)
        queues.get(0).add(process);
    }

    @Override
    public boolean isEmpty() {
        for (List<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFull() {
        // Uma lista não tem limite fixo, então sempre retorna false
        return false;
    }

    @Override
    public void run() {
        System.out.println("Executando escalonador Multilevel Feedback Queue");

        while (!isEmpty()) {
            boolean executed = false;

            // Itera sobre as filas, começando pela de maior prioridade
            for (int i = 0; i < queues.size(); i++) {
                List<Process> queue = queues.get(i);

                if (!queue.isEmpty()) {
                    Process currentProcess = queue.remove(0); // Remove o primeiro processo da fila
                    System.out.println("Executando: " + currentProcess + " na fila " + i);

                    // Executa o processo por no máximo 'quantum' unidades de tempo
                    int executionTime = Math.min(quantum[i], currentProcess.getRemainingTime());
                    currentProcess.execute(executionTime);

                    if (!currentProcess.isCompleted()) {
                        System.out.println("Processo " + currentProcess.getName() + " pausado. Tempo restante: " +
                                currentProcess.getRemainingTime());

                        // Rebaixa o processo para a próxima fila, se existir
                        if (i + 1 < queues.size()) {
                            queues.get(i + 1).add(currentProcess);
                        } else {
                            // Se não houver mais filas, retorna o processo à última fila
                            queues.get(i).add(currentProcess);
                        }
                    } else {
                        System.out.println("Processo " + currentProcess.getName() + " concluído.");
                    }

                    executed = true;
                    break;
                }
            }

            if (!executed) {
                System.out.println("Nenhum processo disponível para execução.");
            }
        }
    }

    @Override
    public String toString() {
        return "MultilevelFeedbackQueue";
    }
}