package ec.edu.com.examencarlosyupa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.com.examencarlosyupa.dto.EmpleadoRQ;
import ec.edu.com.examencarlosyupa.dto.EmpleadoRS;
import ec.edu.com.examencarlosyupa.dto.EmpresaRQ;
import ec.edu.com.examencarlosyupa.model.Empleado;
import ec.edu.com.examencarlosyupa.model.Empresa;
import ec.edu.com.examencarlosyupa.repository.EmpresaRepository;
import lombok.Data;

@Data
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;

    }

    public void crearEmpresa(EmpresaRQ rq) {
        try {
            Empresa empresa = transformEmpresaRQ(rq);
            this.empresaRepository.save(empresa);
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al crear la empresa");
        }
    }

    public Empresa transformEmpresaRQ(EmpresaRQ rq) {
        Empresa empresa = Empresa
                .builder()
                .ruc(rq.getRuc())
                .razonsocial(rq.getRazonsocial())
                .cuenprincipal(rq.getCuenprincipal())
                .empleados(rq.getEmpleados())
                .build();
        return empresa;
    }

        public EmpleadoRS responseEmpleadoRS(Empleado rs) {
        EmpleadoRS empleadoRS = EmpleadoRS.builder()
                .cedulaIdentidad(rs.getCedulaIdentidad()) 
                .numeroDeCuenta(rs.getNumeroDeCuenta())
                .build();
                return empleadoRS;
    }

    public EmpleadoRS responseEmpleadoRS(Empleado rs) {
        EmpleadoRS empleadoRS = EmpleadoRS.builder()
                .cedulaIdentidad(rs.getCedulaIdentidad()) 
                .numeroDeCuenta(rs.getNumeroDeCuenta())
                .build();
                return empleadoRS;
    }

}
