package ec.edu.com.examencarlosyupa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ec.edu.com.examencarlosyupa.model.PagoRol;

public interface PagoRolRepository extends MongoRepository<PagoRol,String> {
    @Query(value = "{}", fields = "{'empleadoPago': 1}")
    List<PagoRol> getAllEmpleados();

  List<PagoRol> findByRucEmpresaAndMes(String rucEmpresa, String mes);
  // PagoRol findByRucEmpresaAndMes(String rucEmpresa, String mes);

  Optional<PagoRol> findById(String id);
}
