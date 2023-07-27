package ec.edu.com.examencarlosyupa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ec.edu.com.examencarlosyupa.model.Empleado;
import ec.edu.com.examencarlosyupa.model.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa,String> {
    @Query(value = "{'ruc': ?0}", fields = "{'empleado': 1}")
    List<Empresa> findEmployeesByEmpresaRuc(String ruc );

    @Query(value = "{'empleados.numeroDeCuenta': ?0}", fields = "{'empleados.$': 1}")
    Empresa findEmpresaByEmpleados(String cedulaIdentidad);

    Empresa findByRuc(String ruc);



}
