package br.sc.senai.controller;

import br.sc.senai.model.Perfil;
import br.sc.senai.model.Usuario;
import br.sc.senai.repository.PerfilRepository;
import br.sc.senai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping(path= "/perfil")
    public @ResponseBody
    ResponseEntity<Perfil> addNewPerfil(@RequestBody Perfil perfil) {
        try {
            Perfil newPerfil = perfilRepository.save(perfil);
            return new ResponseEntity<>(newPerfil, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "/perfil")
    public @ResponseBody ResponseEntity<Iterable<Perfil>> getAllPerfis()
    {
        try {
            Iterable<Perfil> perfis = perfilRepository.findAll();
            if (((Collection<?>) perfis).size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(perfis, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/perfil/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> removePerfil(@PathVariable("id") Integer id) {
        try{
            perfilRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PutMapping("/perfil/{id}")
    public @ResponseBody ResponseEntity<Perfil> updatePerfil(@PathVariable("id") Integer id, @RequestBody Perfil perfil) {
        try {
            Optional<Perfil> perfilData = perfilRepository.findById(id);
            if (perfilData.isPresent()) {
                Perfil updatedPerfil = perfilData.get();
                updatedPerfil.setDescricao(perfil.getDescricao());
                return new ResponseEntity<>(perfilRepository.save(updatedPerfil), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(path = "/todosperfis/{descricao}") // Endpoint que recebe apenas requisições POST para inclusão de usuários
    public @ResponseBody ResponseEntity<Iterable<Perfil>> findByDescricao(@PathVariable("descricao") String descricao, @RequestBody Perfil perfil) {

        try {
            Iterable<Perfil> perfis = perfilRepository.findByDescricao(descricao);
            if (((Collection<?>) perfis).size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(perfis, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }





}
