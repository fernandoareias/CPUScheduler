package com.fernando.common;

public class Process implements Comparable<Process> {
    private String name;          // Nome do processo (identificador)
    private int arrivalTime;      // Tempo de chegada do processo
    private int burstTime;        // Tempo de execução necessário
    private int priority;         // Prioridade do processo (usada em algoritmos baseados em prioridade)
    private int remainingTime;    // Tempo restante para concluir a execução

    /**
     * Construtor padrão.
     *
     * @param name        Nome do processo.
     * @param arrivalTime Tempo de chegada do processo.
     * @param burstTime   Tempo de execução necessário.
     */
    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // Inicializa o tempo restante com o burstTime
        this.priority = 0;              // Prioridade padrão (pode ser ajustada posteriormente)
    }

    /**
     * Construtor com prioridade.
     *
     * @param name        Nome do processo.
     * @param arrivalTime Tempo de chegada do processo.
     * @param burstTime   Tempo de execução necessário.
     * @param priority    Prioridade do processo.
     */
    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this(name, arrivalTime, burstTime);
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
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
        // Ordena por burstTime (SJF) ou prioridade (Prioridade)
        return Integer.compare(this.burstTime, other.burstTime);
    }

    @Override
    public String toString() {
        return "Process{name='" + name + "', arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime + ", priority=" + priority +
                ", remainingTime=" + remainingTime + "}";
    }
}