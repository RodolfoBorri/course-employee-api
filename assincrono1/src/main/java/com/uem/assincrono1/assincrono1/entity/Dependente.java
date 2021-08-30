package com.uem.assincrono1.assincrono1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "db_dependente")
public class Dependente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME_DEPENDENTE", nullable = false)
	private String nomeDependente;
	
	@ManyToOne
	@JoinColumn(name = "ID_FUNCIONARIO", nullable = false, referencedColumnName = "id")
	private Funcionario funcionario;
}
