package com.example.forum.topicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("SELECT COUNT(t) FROM Topico t WHERE t.autor = :autor")
    long countByAutor(String autor);

    @Query("SELECT COUNT(t) FROM Topico t WHERE t.titulo = :titulo")
    long countByTitulo(String titulo);
}
