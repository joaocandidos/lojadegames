package com.joao.lojadegame.controller;


import com.joao.lojadegame.model.Produtos;
import com.joao.lojadegame.repository.CategoriaRepository;
import com.joao.lojadegame.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")//endereço (url ) do recurso produtos
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ProdutosController {

    @Autowired//INJEÇÃO DE DEPENDENCIA
    private ProdutoRepository produtoRepository;


    @Autowired
    private CategoriaRepository categoriaRepository;

    //metodo que retorna todos os objetos da classe produtos percistidos no banco de dados
    @GetMapping
    public ResponseEntity<List<Produtos>> getAll(){
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    //metodo que retorna um objeto da classe produtos especifico atraves do seu id

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getById(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //metodo que retorna um objetos da classe produtos atravez do nome do produto

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produtos>> getByTitulo(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }


    //metodo que adiciona um objeto na classe produtos

    @PostMapping
    public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produto){
        if(categoriaRepository.existsById(produto.getCategoria().getId()))
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoRepository.save(produto));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //metodo que atualiza um ou varios atribitos de um objeto na classe produtos



    @PutMapping
    public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produto){
        if(produtoRepository.existsById(produto.getId())) {

            if (categoriaRepository.existsById(produto.getCategoria().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(produtoRepository.save(produto));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    //metodo para deletar um objeto na trabela produtos


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Produtos> produtos = produtoRepository.findById(id);

        if(produtos.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        produtoRepository.deleteById(id);
    }

    public ProdutoRepository getProdutoRepository() {
        return produtoRepository;
    }

    public void setProdutoRepository(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public CategoriaRepository getCategoriaRepository() {
        return categoriaRepository;
    }

    public void setCategoriaRepository(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
}
