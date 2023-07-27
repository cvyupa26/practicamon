package ec.edu.com.examencarlosyupa.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoPago {
    private String numeroCuenta;
    private BigDecimal valor;
    private String estado; 
}
