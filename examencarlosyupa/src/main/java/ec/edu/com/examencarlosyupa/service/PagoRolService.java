package ec.edu.com.examencarlosyupa.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import ec.edu.com.examencarlosyupa.model.Empleado;
import org.springframework.stereotype.Service;

import ec.edu.com.examencarlosyupa.dto.EmpleadoPagoRQ;
import ec.edu.com.examencarlosyupa.dto.PagoRolRQ;
import ec.edu.com.examencarlosyupa.dto.PagoRolRS;
import ec.edu.com.examencarlosyupa.model.EmpleadoPago;
import ec.edu.com.examencarlosyupa.model.Empresa;
import ec.edu.com.examencarlosyupa.model.PagoRol;
import ec.edu.com.examencarlosyupa.repository.EmpresaRepository;
import ec.edu.com.examencarlosyupa.repository.PagoRolRepository;
import lombok.val;

@Service
public class PagoRolService {

    private final PagoRolRepository pagoRolRepository;
    private final EmpresaRepository empresaRepository;

    public PagoRolService(PagoRolRepository pagoRolRepository, EmpresaRepository empresaRepository) {
        this.pagoRolRepository = pagoRolRepository;
        this.empresaRepository = empresaRepository;
    }

    public void crearPagoRol(PagoRolRQ rq) {
        try {
            PagoRol pagoRol = transformPagoRolRQ(rq);
            List<PagoRol> pagoRolList = this.pagoRolRepository.getAllEmpleados();
            Integer pagosCounter = 0;
            for (PagoRol pagos : pagoRolList) {
                pagosCounter = pagosCounter + pagos.getEmpleadoPago().getValor().intValue();
            }
            pagoRol.setValorTotal(BigDecimal.valueOf(pagosCounter.doubleValue()));
            pagoRol.setValorReal(BigDecimal.valueOf(0.0));
            pagoRol.getEmpleadoPago().setEstado("PEN");
            this.pagoRolRepository.save(pagoRol);
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al crear el pago rol");
        }
    }

    public PagoRolRS validarPagoRol(String mes, String ruc, EmpleadoPagoRQ empleadoPagoRQ) {

        Integer totalTrx = 0;
        Integer errors = 0;
        Integer realValCounter = 0;

        PagoRol pagoRolTmp = this.pagoRolRepository.findByCuentaEmpleado(empleadoPagoRQ.getNumeroCuenta());
        Empresa empresaTmp = this.empresaRepository.findEmpresaByEmpleados(empleadoPagoRQ.getNumeroCuenta());

        if(pagoRolTmp == null){
            throw new RuntimeException("No existe el rol de pagos");
        }else{
            if (pagoRolTmp.getMes().equals(mes) && pagoRolTmp.getRucEmpresa().equals(ruc)) {
                for(Empleado empleado: empresaTmp.getEmpleados()){
                    if (empleado.getNumeroDeCuenta().equals(pagoRolTmp.getEmpleadoPago().getNumeroCuenta())){
                        pagoRolTmp.getEmpleadoPago().setEstado("VAL");
                        totalTrx++;
                        realValCounter = realValCounter + pagoRolTmp.getEmpleadoPago().getValor().intValue();
                        pagoRolTmp.setValorReal(BigDecimal.valueOf(realValCounter));
                    }else{
                        pagoRolTmp.getEmpleadoPago().setEstado("BAD");
                        errors++;
                    }
                }
                this.pagoRolRepository.save(pagoRolTmp);
                PagoRolRS response = transformPagoRolRS(pagoRolTmp);
                response.setTotalTransacciones(totalTrx);
                response.setErrores(errors);
                return response;
            } else {
                throw new RuntimeException("No existe el rol de pagos");
            }


        }


    }


    public List<PagoRol> obtenerEmpleados() {
        return this.pagoRolRepository.getAllEmpleados();
    }

    public PagoRol transformPagoRolRQ(PagoRolRQ rq) {
        PagoRol pagoRol = PagoRol
                .builder()
                .mes(rq.getMes())
                .fechaProceso(rq.getFechaProceso())
                .rucEmpresa(rq.getRucEmpresa())
                .cuentaPrincipal(rq.getCuentaPrincipal())
                .empleadoPago(rq.getEmpleadoPago())
                .build();
        return pagoRol;
    }

    public PagoRolRS transformPagoRolRS(PagoRol pagoRol) {
        PagoRolRS pagoRolRS = PagoRolRS
                .builder()
                .mes(pagoRol.getMes())
                .fechaProceso(pagoRol.getFechaProceso())
                .rucEmpresa(pagoRol.getRucEmpresa())
                .cuentaPrincipal(pagoRol.getCuentaPrincipal())
                .valorTotal(pagoRol.getValorTotal())
                .valorReal(pagoRol.getValorReal())
                .build();
        return pagoRolRS;
    }

}
