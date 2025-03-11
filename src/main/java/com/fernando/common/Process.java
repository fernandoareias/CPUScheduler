package com.fernando.common;


public class Process implements Comparable<Process> {
    private String name;          // Nome do processo
    private int arrivalTime;      // Tempo de chegada
    private int burstTime;        // Tempo de execução necessário
    private int remainingTime;    // Tempo restante para concluir a execução
    private int deadline;         // Prazo (deadline)

    public Process(String name, int arrivalTime, int burstTime, int deadline) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void execute(int timeUnits) {
        if (timeUnits > remainingTime) {
            throw new IllegalArgumentException("Não é possível executar mais tempo do que o restante.");
        }
        remainingTime -= timeUnits;
    }

    public boolean isCompleted() {
        return remainingTime <= 0;
    }

    @Override
    public int compareTo(Process other) {
        // Ordena por deadline (menor deadline primeiro)
        return Integer.compare(this.deadline, other.deadline);
    }

    @Override
    public String toString() {
        return "Process{name='" + name + "', arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime + ", deadline=" + deadline +
                ", remainingTime=" + remainingTime + "}";
    }
}