package com.uem.assincrono1.assincrono1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "db_curso_funcionario")
public class CursoFuncionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_curso", nullable = false, referencedColumnName = "id")
	private Curso curso;
	
	@OneToOne
	@JoinColumn(name = "id_funcionario", nullable = false, referencedColumnName = "id")
	private Funcionario funcionario;

	@Column(name = "ANO_FORMACAO", nullable = false)
	private String anoFormacao;
}
