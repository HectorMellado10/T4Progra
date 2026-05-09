package com.ejemplo.demo.domain.model;

/**
 * POJO sin anotaciones Spring: cada "new EstadoManual()" inicia desde cero.
 * Demuestra que, sin @Service, Spring no gestiona el ciclo de vida del objeto.
 */
public class EstadoManual {

    private int valor = 0;

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
