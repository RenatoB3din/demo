package br.sc.senai.repository;

import br.sc.senai.model.Perfil;
import br.sc.senai.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PerfilRepository extends CrudRepository<Perfil, Integer> {
    @Query(value = "SELECT p FROM Perfil p WHERE p.descricao = :descricao")
    Collection<Perfil> findByDescricao(@Param("descricao") String descricao);
}
