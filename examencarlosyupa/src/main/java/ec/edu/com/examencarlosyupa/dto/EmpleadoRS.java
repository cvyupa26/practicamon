package ec.edu.com.examencarlosyupa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoRS {

    private Integer cedulaIdentidad;
    private String numeroDeCuenta;

}
