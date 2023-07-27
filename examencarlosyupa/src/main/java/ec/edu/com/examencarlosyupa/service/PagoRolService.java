package ec.edu.com.examencarlosyupa.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
            pagoRol.setValorTotal(BigDecimal.valueOf(pagosCounter));
            pagoRol.setValorReal(BigDecimal.valueOf(0.0));
            pagoRol.getEmpleadoPago().setEstado("PEN");
            this.pagoRolRepository.save(pagoRol);
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al crear el pago rol");
        }
    }

    public PagoRolRS validarPagoRol(String mes, String ruc, EmpleadoPagoRQ empleadoPagoRQ) {

        Integer totaltransacciones = 0;
        Integer errores = 0;
        float valoRe = 0;

        List<PagoRol> pagoRolList = this.pagoRolRepository.findByRucEmpresaAndMes(ruc, mes);
        if (pagoRolList.isEmpty()) {
            throw new RuntimeException("No existe el pago rol");
        }
        List<Empresa> empresaList = this.empresaRepository.findEmployeesByEmpresaRuc(ruc);
        Integer sizeEmpresaList = empresaList.size();

        for (Empresa empleado : empresaList) {

            for (int i = 0; i < sizeEmpresaList; i++) {
                if (empleado.getEmpleados().get(i).getNumeroDeCuenta().equals(empleadoPagoRQ.getNumeroCuenta())) {

                    for (PagoRol empPagoRolExist : pagoRolList) {
                        Optional<PagoRol> pagoRol = this.pagoRolRepository.findById(empPagoRolExist.getId());
                        if(pagoRol.isPresent()){
                            valoRe = pagoRol.get().getValorReal().floatValue();
                        }
                        empPagoRolExist.getEmpleadoPago().setEstado("VAL");
                        totaltransacciones++;
                     
                    }

                } else {
                    for (PagoRol empPagoRolNoEx : pagoRolList) {

                        empPagoRolNoEx.getEmpleadoPago().setEstado("BAD");
                        errores++;
                    }
                }
            }

        }

        return null;

    }

    /*
     * public PagoRolRS validarPagoRol(String mes, String ruc) {
     * 
     * Empresa empresa = this.empresaRepository.findByRuc(ruc);
     * PagoRol pagoRol = this.pagoRolRepository.findByRucEmpresaAndMes(ruc, mes);
     * 
     * Boolean verifi = null;
     * Integer totaltransacciones = 0;
     * Integer errores = 0;
     * BigDecimal valorReal = BigDecimal.valueOf(0.0);
     * 
     * for (EmpleadoPago empPago : pagoRol.getEmpleadoPago()) {
     * 
     * if (empPago.getEstado().equals("PEN")) {
     * empPago.setEstado("VAL");
     * empPago.setValor(empPago.getValor());
     * totaltransacciones++;
     * valorReal = valorReal.add(empPago.getValor());
     * verifi = true;
     * } else {
     * errores++;
     * verifi = false;
     * }
     * }
     * 
     * 
     * }
     */

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

}
