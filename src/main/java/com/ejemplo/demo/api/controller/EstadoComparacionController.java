package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.EstadoResponse;
import com.ejemplo.demo.api.generated.DemoEstadoApi;
import com.ejemplo.demo.domain.model.EstadoManual;
import com.ejemplo.demo.domain.service.EstadoSingletonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoComparacionController implements DemoEstadoApi {

    private final EstadoSingletonService estadoSingletonService;

    public EstadoComparacionController(EstadoSingletonService estadoSingletonService) {
        this.estadoSingletonService = estadoSingletonService;
    }

    @Override
    public ResponseEntity<EstadoResponse> actualizarSingleton(@PathVariable("valor") Integer valor) {
        // Bean singleton de Spring: el valor queda persistido para llamadas posteriores.
        int actual = estadoSingletonService.actualizar(valor);
        return ResponseEntity.ok(new EstadoResponse("singleton", actual));
    }

    @Override
    public ResponseEntity<EstadoResponse> obtenerSingleton() {
        return ResponseEntity.ok(new EstadoResponse("singleton", estadoSingletonService.obtenerActual()));
    }

    @Override
    public ResponseEntity<EstadoResponse> reiniciarSingleton() {
        return ResponseEntity.ok(new EstadoResponse("singleton", estadoSingletonService.reiniciar()));
    }

    @Override
    public ResponseEntity<EstadoResponse> actualizarManual(@PathVariable("valor") Integer valor) {
        // Clase sin @Service: se crea con new y su estado no persiste entre requests.
        EstadoManual estadoManual = new EstadoManual();
        estadoManual.setValor(valor);
        return ResponseEntity.ok(new EstadoResponse("manual", estadoManual.getValor()));
    }

    @Override
    public ResponseEntity<EstadoResponse> obtenerManual() {
        // Cada llamada inicia en 0 porque es una instancia nueva.
        EstadoManual estadoManual = new EstadoManual();
        return ResponseEntity.ok(new EstadoResponse("manual", estadoManual.getValor()));
    }
}
