package com.uem.assincrono1.assincrono1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.assincrono1.assincrono1.entity.Dependente;
import com.uem.assincrono1.assincrono1.entity.Funcionario;

public interface DependenteRepository extends JpaRepository<Dependente, Long>{

	Optional<Dependente> findByNomeDependente(String nomeDependente);

	List<Dependente> findAllByFuncionario(Funcionario funcionario);

}
