
package io.swagger.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.oportunidade.Oportunidade;
import io.swagger.v3.oas.annotations.Operation;

@Api(tags = "CrmApiApp")
@RequestMapping(path = "/oportunidade")
public interface OportunidadeApi {

   //*************** Oportunidade ************************************
	
	@Operation(summary = "Salvar Oportunidade", description = "Salvar Oportunidade")
	@PostMapping(value = "/salvar", produces = { "application/json", "application/xml" }, consumes = {
			"application/json", "application/xml", "application/x-www-form-urlencoded" })
	ResponseEntity<Oportunidade> salvarOportunidade(@RequestBody Oportunidade oportunidade);

	@Operation(summary = "Atualizar Oportunidade", description = "Atualizar Oportunidade")
	@PutMapping(value = "/atualizar", produces = { "application/json", "application/xml" }, consumes = {
			"application/json", "application/xml", "application/x-www-form-urlencoded" })
	ResponseEntity<Oportunidade> atualizarOportunidade(@RequestBody Oportunidade oportunidade);
	
	@Operation(summary = "Listar oportunidade", description = "Pesquisa um oportunidade")
	@GetMapping(path = "/listar")
	ResponseEntity<List<Oportunidade>> listar(@RequestParam(value = "pesquisa", defaultValue = "") String pesquisa);
	

	@Operation(summary = "Carregar oportunidade", description = "Carregar oportunidade pelo ID")
	@GetMapping(value = "/carregar")
	ResponseEntity<Oportunidade> carregarOportunidade(
			@RequestParam(value = "id") @ApiParam(name = "id", value = "ID do oportunidade", example = "1") Integer id);

		
	@Operation(summary = "Excluir oportunidade", description = "Excluir oportunidade pelo ID" )
	@DeleteMapping(value = "/delete")
	ResponseEntity<Boolean> deleteOportunidade(
			@RequestParam(value = "id") @ApiParam(name = "id", value = "ID do oportunidade", example = "1") Integer id);

}

