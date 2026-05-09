package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    public PrestamoResponse simularPrestamo(PrestamoRequest request) {
        BigDecimal P = request.monto();
        BigDecimal tasaMensual = request.tasaAnual()
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        int n = request.meses();

        // (1 + r)^n
        BigDecimal unoPlusR = BigDecimal.ONE.add(tasaMensual);
        BigDecimal unoPlusRpowN = unoPlusR.pow(n, MathContext.DECIMAL128);

        // cuota = P * (r * (1+r)^n) / ((1+r)^n - 1)
        BigDecimal numerador = P.multiply(tasaMensual.multiply(unoPlusRpowN));
        BigDecimal denominador = unoPlusRpowN.subtract(BigDecimal.ONE);
        BigDecimal cuotaMensual = numerador.divide(denominador, 2, RoundingMode.HALF_UP);

        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(n));
        BigDecimal interesTotal = totalPagar.subtract(P);

        return new PrestamoResponse(cuotaMensual, interesTotal, totalPagar);
    }
}