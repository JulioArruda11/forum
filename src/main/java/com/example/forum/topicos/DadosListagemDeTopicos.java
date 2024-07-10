package com.example.forum.topicos;

import java.time.LocalDateTime;

public record DadosListagemDeTopicos(
        String titulo,
        String mensagem,
        String autor,
        String curso,
        LocalDateTime data_criacao,
        boolean estado) {

    public DadosListagemDeTopicos(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso(), topico.getData_criacao(), topico.isEstado());
    }

}
