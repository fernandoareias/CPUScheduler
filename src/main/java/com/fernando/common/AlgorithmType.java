package com.fernando.common;

public enum AlgorithmType {
    FCFS,
    SJF,
    PRIORITY;

    public static AlgorithmType fromString(String value) {
        for (AlgorithmType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Algoritmo inválido: " + value);
    }
}