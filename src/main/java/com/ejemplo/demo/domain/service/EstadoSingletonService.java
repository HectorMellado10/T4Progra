package com.ejemplo.demo.domain.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bean singleton de Spring: una sola instancia para toda la aplicacion.
 * AtomicInteger garantiza acceso seguro en entornos concurrentes.
 */
@Service
public class EstadoSingletonService {

    private final AtomicInteger ultimoValor = new AtomicInteger(0);

    public int actualizar(int valor) {
        ultimoValor.set(valor);
        return ultimoValor.get();
    }

    public int obtenerActual() {
        return ultimoValor.get();
    }

    public int reiniciar() {
        ultimoValor.set(0);
        return ultimoValor.get();
    }
}
