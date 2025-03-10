package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;

import java.util.Arrays;

//Shortest Job First
public class SJF implements Scheduler {
    private static final int DEFAULT_CAPACITY = 20;
    private Process[] buffer; // Array que representa o buffer circular
    private int head;         // Índice do início da fila
    private int tail;         // Índice do próximo espaço disponível
    private int size;         // Número de elementos na fila
    private int capacity;     // Capacidade máxima do buffer

    public SJF() {
        this(DEFAULT_CAPACITY);
    }

    public SJF(Integer capacity) {
        this.capacity = capacity;
        buffer = new Process[this.capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void enqueue(Process process) {
        if (isFull()) {
            resize();
        }

        int insertIndex = tail;
        for (int i = head; i != tail; i = (i + 1) % capacity) {
            if (buffer[i].getBurstTime() > process.getBurstTime()) {
                insertIndex = i;
                break;
            }
        }

        for (int i = tail; i != insertIndex; i = (i - 1 + capacity) % capacity) {
            buffer[i] = buffer[(i - 1 + capacity) % capacity];
        }

        buffer[insertIndex] = process;
        tail = (tail + 1) % capacity;
        size++;
    }

    public Process dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        Process removedProcess = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        size--;
        return removedProcess;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Process[] newBuffer = new Process[newCapacity];

        for (int i = 0; i < size; i++) {
            newBuffer[i] = buffer[(head + i) % capacity];
        }

        buffer = newBuffer;
        head = 0;
        tail = size;
        capacity = newCapacity;
    }

    public void run() {
        if (isEmpty()) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        while (!isEmpty()) {
            Process currentProcess = dequeue();
            System.out.println("Executando: " + currentProcess);
        }
    }


    /**
     * Ordena os processos no buffer pelo burstTime.
     */
    private void sortProcessesByBurstTime() {
        // Cria um array temporário para armazenar os processos atuais
        Process[] tempArray = new Process[size];
        for (int i = 0; i < size; i++) {
            tempArray[i] = buffer[(head + i) % capacity];
        }

        // Ordena o array temporário pelo burstTime
        Arrays.sort(tempArray, (p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));

        // Copia os processos ordenados de volta para o buffer
        for (int i = 0; i < size; i++) {
            buffer[(head + i) % capacity] = tempArray[i];
        }
    }

    @Override
    public String toString() {
        return "SJF";
    }
}