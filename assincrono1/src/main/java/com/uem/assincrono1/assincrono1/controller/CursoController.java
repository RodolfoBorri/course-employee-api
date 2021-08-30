package com.uem.assincrono1.assincrono1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.assincrono1.assincrono1.dto.request.CursoRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.CursoResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.ResponseDTO;
import com.uem.assincrono1.assincrono1.service.CursoService;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController extends ControllerBase{

	@Autowired
	private CursoService cursoService;
	
	public CursoController(MessageSource messageSource) {
		super(messageSource);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<CursoResponseDTO>> cadastra(@Valid @RequestBody CursoRequestDTO cursoRequestDTO) {
		
		CursoResponseDTO cursoResponseDTO = cursoService.cadastra(cursoRequestDTO);
		
		System.out.println("Entrou cadastra CursoController, DTO:" + cursoRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new ResponseDTO<>(cursoResponseDTO));
	}
	
	@GetMapping
	public ResponseEntity<ResponseDTO<List<CursoResponseDTO>>> consultaTodos() {
	
		List<CursoResponseDTO> cursoResponseDTO = cursoService.consultaTodos();
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoResponseDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<CursoResponseDTO>> consultaPorId(@PathVariable Long id) {
	
		CursoResponseDTO cursoResponseDTO = cursoService.consultaCursoPorId(id);
		
		System.out.println("Entrou consultaPorId CursoController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoResponseDTO));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> atualiza(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO cursoRequestDTO){
		cursoService.atualiza(id, cursoRequestDTO);
		
		System.out.println("Entrou atualiza CursoController, DTO:" + cursoRequestDTO);	
		
		return ResponseEntity.noContent().build();
	}
}
