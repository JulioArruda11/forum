package com.example.forum.controller;

import com.example.forum.topicos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class ForumController {
    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        // Verifica se já existe um tópico com o mesmo autor
        long autorCount = repository.countByAutor(dados.autor());
        if (autorCount > 0) {
            throw new RuntimeException("Já existe um tópico cadastrado com o mesmo autor");
        }

        // Verifica se já existe um tópico com o mesmo título
        long tituloCount = repository.countByTitulo(dados.titulo());
        if (tituloCount > 0) {
            throw new RuntimeException("Já existe um tópico cadastrado com o mesmo título");
        }

        // Se não existir, pode cadastrar o novo tópico
        repository.save(new Topico(dados));
    }

    @GetMapping
    public Page<DadosListagemDeTopicos> ListarTopicos(Pageable paginacao){
        return repository.findAll(paginacao).map(DadosListagemDeTopicos::new);
    }

    // Endpoint para buscar um tópico específico pelo ID
    @GetMapping("/{id}")
    public DadosListagemDeTopicos buscarTopicoPorId(@PathVariable Long id) {
        Topico topico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        return new DadosListagemDeTopicos(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados){
        var topico = repository.getReferenceById(dados.id());
        topico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }
}
