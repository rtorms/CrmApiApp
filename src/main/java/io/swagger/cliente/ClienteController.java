package io.swagger.cliente;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.api.ClienteApi;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ClienteController implements ClienteApi {

	private final ClienteService clienteSyncService;

	public ClienteController(ClienteService clienteSyncService) {
		this.clienteSyncService = clienteSyncService;
	}

	@Override
	public ResponseEntity<Cliente> salvarCliente(@Validated @RequestBody Cliente cliente) {
		Cliente cli = new Cliente();
		if(cliente.getId() == null || cliente.getId() == 0) {
			cli = clienteSyncService.save(cliente);
		} else {
			cli = clienteSyncService.update(cliente);
		}
		return ResponseEntity.ok(cli);
	}

	@Override
	public ResponseEntity<Cliente> atualizarCliente(@Validated @RequestBody Cliente cliente) {
		Cliente cli = new Cliente();
		if(cliente.getId() == null) {
			cli = clienteSyncService.save(cliente);
		} else {
			cli = clienteSyncService.update(cliente);
		}
		return ResponseEntity.ok(cli);
	}

	@Override
	public ResponseEntity<List<Cliente>> listar(String pesquisa) {
		List<Cliente> clientes = clienteSyncService.pesquisa(pesquisa);
		return ResponseEntity.ok(clientes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Cliente> carregarCliente(@RequestParam(value = "id") Long id) {
		Cliente cliente = clienteSyncService.findById(id);
		return (ResponseEntity<Cliente>) (cliente != null ? ResponseEntity.ok(cliente)
				: ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<Boolean> deleteCliente(@RequestParam(value = "id") Long id) {
		Boolean exclusao = clienteSyncService.deleteCliente(id);
		return (ResponseEntity<Boolean>) ResponseEntity.ok(exclusao);
	}

}
