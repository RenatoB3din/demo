package br.sc.senai.repository;

import br.sc.senai.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    @Query(value = "SELECT u FROM Usuario u WHERE u.nome = :nome")
    Collection<Usuario> findAllByName(@Param("nome") String nome);
}
