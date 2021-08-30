package com.uem.assincrono1.assincrono1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.assincrono1.assincrono1.entity.Curso;
import com.uem.assincrono1.assincrono1.entity.CursoFuncionario;
import com.uem.assincrono1.assincrono1.entity.Funcionario;

public interface CursoFuncionarioRepository extends JpaRepository<CursoFuncionario, Long>{

	public Optional<CursoFuncionario> findByCursoAndFuncionarioAndAnoFormacao(Curso curso, Funcionario funcionario, String anoFormacao);
	
	public List<CursoFuncionario> findByFuncionario(Funcionario funcionario);
	
	public List<CursoFuncionario> findByCurso(Curso curso);
}
