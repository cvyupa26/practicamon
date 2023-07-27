package ec.edu.com.examencarlosyupa.dto;

import java.math.BigDecimal;
import java.util.Date;

import ec.edu.com.examencarlosyupa.model.EmpleadoPago;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagoRolRS {
    private String mes;
    private Date fechaProceso;
    private String rucEmpresa;
    private String cuentaPrincipal;
    private BigDecimal valorTotal;
    private BigDecimal valorReal;
    private Integer totalTransacciones;
    private Integer Errores;

}
