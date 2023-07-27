package ec.edu.com.examencarlosyupa.dto;

import java.math.BigDecimal;
import java.sql.Date;

import ec.edu.com.examencarlosyupa.model.EmpleadoPago;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class PagoRolRQ {
    private String mes;
    private Date fechaProceso;
    private String rucEmpresa;
    private String cuentaPrincipal;
    private EmpleadoPago empleadoPago;
}
