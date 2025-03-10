package com.fernando.algorithms;

import com.fernando.common.Scheduler;
import com.fernando.common.Process;


public class FCFS implements Scheduler {
    private static final int DEFAULT_CAPACITY = 20;
    private Process[] buffer; // Array que representa o buffer circular
    private int head;         // Índice do início da fila
    private int tail;         // Índice do próximo espaço disponível
    private int size;         // Número de elementos na fila
    private int capacity;     // Capacidade máxima do buffer

    public FCFS() {
        this(DEFAULT_CAPACITY);
    }

    public FCFS(Integer capacity) {
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
        buffer[tail] = process;
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
            int currentPosition = head;
            Process currentProcess = dequeue();
            System.out.println("Executando: " + currentProcess + " | Posicao: " + currentPosition);
        }
    }

    @Override
    public String toString() {
        return "FCFS";
    }
}