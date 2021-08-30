package com.uem.assincrono1.assincrono1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.assincrono1.assincrono1.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Optional<Curso> findByNomeCurso(String nomeCurso);

}
