package com.uem.assincrono1.assincrono1.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FuncionarioRequestDTO {

	private Long matriculaFuncionario;
	
	private String nomeFuncionario;

	private Date dataNascimento;
}
