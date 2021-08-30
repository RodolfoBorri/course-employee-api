package com.uem.assincrono1.assincrono1.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_NULL)
public class FuncionarioResponseDTO {
	
	private Long id;

	private Long matriculaFuncionario;
	
	private String nomeFuncionario;

	private Date dataNascimento;
}
