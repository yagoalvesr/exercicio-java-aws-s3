package com.java.aws.s3.turma3.exercice.sql.repository;

import com.java.aws.s3.turma3.exercice.sql.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}