package com.joao.lojadegame.controller;


import com.joao.lojadegame.model.Categoria;
import com.joao.lojadegame.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;



//metodo para listar todas os objetos da tabela categoria persistidos no banco
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    //metodo para buscar por id um objeto na tabela categoria persistido no banco de dados
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id){
        return categoriaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //metodos para buscar por tipo um ou mais objetos da tabela categoria
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Categoria>> getByTitle(@PathVariable  String tipo){

        return ResponseEntity.ok(categoriaRepository
                .findAllByTipoContainingIgnoreCase(tipo));
    }

    //metodo para add um objeto na tabela categoria
    @PostMapping
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaRepository.save(categoria));
    }

    //metodo para atualizar um ou mais atributos de um objeto da tabela categoria

    @PutMapping
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //metodo para deletar um objeto na tabela categoria
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoriaRepository.deleteById(id);
    }



}
