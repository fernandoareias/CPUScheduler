package com.fernando.common;

public interface Scheduler {
    /**
     * Adiciona um processo à fila de execução.
     *
     * @param process O processo a ser adicionado.
     */
    void enqueue(Process process);

    /**
     * Executa os processos na ordem definida pelo algoritmo de escalonamento.
     */
    void run();

    /**
     * Verifica se a fila de execução está vazia.
     *
     * @return true se a fila estiver vazia, false caso contrário.
     */
    boolean isEmpty();

    /**
     * Verifica se a fila de execução está cheia.
     *
     * @return true se a fila estiver cheia, false caso contrário.
     */
    boolean isFull();
}