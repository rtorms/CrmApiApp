
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
import io.swagger.cliente.Cliente;
import io.swagger.v3.oas.annotations.Operation;

@Api(tags = "CrmApiApp")
@RequestMapping(path = "/cliente")
public interface ClienteApi {

   //*************** Cliente ************************************
	
	@Operation(summary = "Salvar Cliente", description = "Salvar Cliente")
	@PostMapping(value = "/salvar", produces = { "application/json", "application/xml" }, consumes = {
			"application/json", "application/xml", "application/x-www-form-urlencoded" })
	ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente);

	@Operation(summary = "Atualizar Cliente", description = "Atualizar Cliente")
	@PutMapping(value = "/atualizar", produces = { "application/json", "application/xml" }, consumes = {
			"application/json", "application/xml", "application/x-www-form-urlencoded" })
	ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente);
	
	@Operation(summary = "Listar cliente", description = "Pesquisa um cliente")
	@GetMapping(path = "/listar")
	ResponseEntity<List<Cliente>> listar(@RequestParam(value = "pesquisa", defaultValue = "") String pesquisa);
	

	@Operation(summary = "Carregar cliente", description = "Carregar cliente pelo ID")
	@GetMapping(value = "/carregar")
	ResponseEntity<Cliente> carregarCliente(
			@RequestParam(value = "id") @ApiParam(name = "id", value = "ID do cliente", example = "1") Integer id);

	@Operation(summary = "Excluir cliente", description = "Excluir cliente pelo ID" )
	@DeleteMapping(value = "/delete")
	ResponseEntity<Boolean> deleteCliente(
			@RequestParam(value = "id") @ApiParam(name = "id", value = "ID do cliente", example = "1") Integer id);

}

