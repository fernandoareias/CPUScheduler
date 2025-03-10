package com.fernando.common;

import com.fernando.algorithms.FCFS;

import java.util.HashMap;

import java.util.Map;

public class SchedulerFactory {
    private static final Map<AlgorithmType, Scheduler> algorithms = new HashMap<>();

    // Singleton
    private static final SchedulerFactory instance = new SchedulerFactory();

    private SchedulerFactory() {
        registerAlgorithm(AlgorithmType.FCFS, new FCFS());
    }

    public static SchedulerFactory getInstance() {
        return instance;
    }

    private void registerAlgorithm(AlgorithmType type, Scheduler scheduler) {
        algorithms.put(type, scheduler);
    }

    public Scheduler get(String algorithmName) {
        AlgorithmType type = AlgorithmType.fromString(algorithmName);
        return get(type);
    }

    private Scheduler get(AlgorithmType type) {
        Scheduler scheduler = algorithms.get(type);
        if (scheduler == null)
            throw new IllegalArgumentException("Algoritmo não encontrado: " + type);

        return scheduler;
    }
}