package ec.edu.com.examencarlosyupa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.com.examencarlosyupa.dto.EmpresaRQ;
import ec.edu.com.examencarlosyupa.repository.EmpresaRepository;
import ec.edu.com.examencarlosyupa.service.EmpresaService;

@RestController
@RequestMapping("/api/Empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EmpresaRQ empresaRQ) {
        this.empresaService.crearEmpresa(empresaRQ);
        return ResponseEntity.ok().build();
    }

}
