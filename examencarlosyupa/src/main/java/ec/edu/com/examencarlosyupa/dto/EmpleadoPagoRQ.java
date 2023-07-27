package ec.edu.com.examencarlosyupa.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoPagoRQ {

    private String numeroCuenta;
    private BigDecimal valor;
}
