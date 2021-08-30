package com.uem.assincrono1.assincrono1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "db_funcionario")
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "MATRICULA_FUNCIONARIO", nullable = false, unique = true)
	private Long matriculaFuncionario;
	
	@Column(name = "NOME_FUNCIONARIO", nullable = false)
	private String nomeFuncionario;
	
	@Column(name = "DATA_NASCIMENTO", nullable = false)
	private Date dataNascimento;
	
}
