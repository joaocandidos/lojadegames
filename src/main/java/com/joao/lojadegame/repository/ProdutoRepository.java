package com.joao.lojadegame.repository;

import com.joao.lojadegame.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produtos, Long> {


    public List<Produtos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
