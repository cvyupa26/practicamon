package ec.edu.com.examencarlosyupa.controller;

import java.util.List;

import ec.edu.com.examencarlosyupa.dto.PagoRolRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.com.examencarlosyupa.dto.EmpleadoPagoRQ;
import ec.edu.com.examencarlosyupa.dto.PagoRolRQ;
import ec.edu.com.examencarlosyupa.model.PagoRol;
import ec.edu.com.examencarlosyupa.service.PagoRolService;

@RestController
@RequestMapping("/api/pagoRol")

public class PagoRolController {

    private final PagoRolService pagoRolService;

    public PagoRolController(PagoRolService pagoRolService) {
        this.pagoRolService = pagoRolService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PagoRolRQ rq) {
        this.pagoRolService.crearPagoRol(rq);
        return ResponseEntity.ok("Pago Rol creado correctamente");
    }

    @PutMapping("/validar-pago-rol")
    public ResponseEntity<PagoRolRS> validatePayRolePayment(@RequestParam String mes, @RequestParam String ruc ,@RequestBody EmpleadoPagoRQ empleadoPagoRQ) {
        PagoRolRS rs = this.pagoRolService.validarPagoRol(mes, ruc, empleadoPagoRQ);
        return ResponseEntity.ok(rs);
    }



}
