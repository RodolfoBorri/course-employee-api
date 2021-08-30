package com.uem.assincrono1.assincrono1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.assincrono1.assincrono1.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	Optional<Funcionario> findByMatriculaFuncionario(Long matriculaFuncionario);

}
