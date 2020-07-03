package br.sc.senai.controller;

import br.sc.senai.model.Usuario;
import br.sc.senai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Collection;
import java.util.Optional;

@RestController // Isto indica que esta classe é um Controller
@RequestMapping(path = "/api")
// Isto indica que a URL deve conter o caminho /usuario após a URL principal da aplicação
public class UsuarioController {

    // Isto significa obter automaticamente o objeto usuárioRepository,
    // que é gerado automaticamente pelo Spring. Vamos usá-lo para manipular os dados (DAO).
    @Autowired
    private UsuarioRepository usuarioRepository;

//    @PostMapping(path = "/add") // Endpoint que recebe apenas requisições POST para inclusão de usuários
//    public @ResponseBody
//    String addNewUser(@RequestParam String nome
//            , @RequestParam String email, @RequestParam String senha) {
//        // @ResponseBody significa que a String retornada é a resposta
//        // @RequestParam significa que é um parâmetro da solicitação GET ou POST
//
//        Usuario u = new Usuario();
//        u.setNome(nome);
//        u.setEmail(email);
//        u.setSenha(senha);
//        usuarioRepository.save(u);
//        return "Usuário salvo no banco de dados";
//    }
    @PostMapping(path= "/usuarios")
    public @ResponseBody ResponseEntity<Usuario> addNewUser(@RequestBody Usuario usuario) {
        try {
            Usuario newUser = usuarioRepository.save(usuario);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

//    @GetMapping(path = "/all") // Endpoint que recebe apenas requisições GET para retornar todos os usuários
//    public @ResponseBody
//    Iterable<Usuario> getAllUsers() {
//        // Retorna um JSON com todos os usuários
//        return usuarioRepository.findAll();
//    }
    @GetMapping(path = "/usuarios")
    public @ResponseBody ResponseEntity<Iterable<Usuario>> getAllUsers()
    {
        try {
            Iterable<Usuario> usuarios = usuarioRepository.findAll();
            if (((Collection<?>) usuarios).size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping(path = "/remove") // Endpoint que recebe apenas requisições POST para inclusão de usuários
//    public @ResponseBody
//    String removeUser(@RequestParam Integer id) {
//        // @ResponseBody significa que a String retornada é a resposta
//        // @RequestParam significa que é um parâmetro da solicitação GET ou POST
//
//        usuarioRepository.deleteById(id);
//        return "Usuário excluído do banco de dados";
//    }
    @DeleteMapping("/usuarios/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> removeUser(@PathVariable("id") Integer id) {
        try{
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//    @PostMapping(path = "/update") // Endpoint que recebe apenas requisições POST para inclusão de usuários
//    public @ResponseBody
//    String updateUser(@RequestParam Integer id, @RequestParam String nome
//            , @RequestParam String email, @RequestParam String senha) {
//        // @ResponseBody significa que a String retornada é a resposta
//        // @RequestParam significa que é um parâmetro da solicitação GET ou POST
//
//        Usuario u = usuarioRepository.findById(id).get();
//        u.setNome(nome);
//        u.setEmail(email);
//        u.setSenha(senha);
//        usuarioRepository.save(u);
//        return "Usuário com id "+u.getId()+" atualizado no banco de dados";
//    }
    @PutMapping("/usuarios/{id}")
    public @ResponseBody ResponseEntity<Usuario> updateUser(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioData = usuarioRepository.findById(id);
            if (usuarioData.isPresent()) {
                Usuario updatedUser = usuarioData.get();
                updatedUser.setNome(usuario.getNome());
                updatedUser.setEmail(usuario.getEmail());
                updatedUser.setSenha(usuario.getSenha());
                return new ResponseEntity<>(usuarioRepository.save(updatedUser), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
    }

    // @ResponseBody significa que a String retornada é a resposta
    // @RequestParam significa que é um parâmetro da solicitação GET ou POST


    @PostMapping(path = "/todosusuarios/{nome}") // Endpoint que recebe apenas requisições POST para inclusão de usuários
    public @ResponseBody ResponseEntity<Iterable<Usuario>> findByName(@PathVariable("nome") String nome, @RequestBody Usuario usuario) {

        try {
            Iterable<Usuario> usuarios = usuarioRepository.findAllByName(nome);
            if (((Collection<?>) usuarios).size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
