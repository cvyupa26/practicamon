package ec.edu.com.examencarlosyupa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /*
     * @GetMapping
     * public ResponseEntity<List<PagoRol>> obtenerEmpleados() {
     * 
     * return ResponseEntity.ok(this.pagoRolService.obtenerEmpleados());
     * }
     */
    public ResponseEntity<?> validarPagoRol(String mes, String ruc, EmpleadoPagoRQ empleadoPagoRQ) {
        this.pagoRolService.validarPagoRol(mes, ruc, empleadoPagoRQ);
        return ResponseEntity.ok("Pago Rol validado correctamente");
    }

}
