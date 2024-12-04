package io.swagger.oportunidade;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.api.OportunidadeApi;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class OportunidadeController implements OportunidadeApi {

	private final OportunidadeService oportunidadeSyncService;

	public OportunidadeController(OportunidadeService oportunidadeSyncService) {
		this.oportunidadeSyncService = oportunidadeSyncService;
	}

	@Override
	public ResponseEntity<Oportunidade> salvarOportunidade(@Validated @RequestBody Oportunidade oportunidade) {
		Oportunidade opt = new Oportunidade();
		if(oportunidade.getId() == null) {
			opt = oportunidadeSyncService.save(oportunidade);
		} else {
			opt = oportunidadeSyncService.update(oportunidade);
		}
		return ResponseEntity.ok(opt);
	}

	@Override
	public ResponseEntity<Oportunidade> atualizarOportunidade(@Validated @RequestBody Oportunidade oportunidade) {
		Oportunidade opt = new Oportunidade();
		opt = oportunidadeSyncService.update(oportunidade);
		return ResponseEntity.ok(opt);
	}

	@Override
	public ResponseEntity<List<Oportunidade>> listar(@RequestParam(value = "pesquisa", defaultValue = "") String pesquisa) {
		List<Oportunidade> oportunidades = oportunidadeSyncService.pesquisa(pesquisa);
		return ResponseEntity.ok(oportunidades);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Oportunidade> carregarOportunidade(@RequestParam(value = "id") Integer id) {
		Oportunidade oportunidade = oportunidadeSyncService.findById(id);
		return (ResponseEntity<Oportunidade>) (oportunidade != null ? ResponseEntity.ok(oportunidade)
				: ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<Boolean> deleteOportunidade(@RequestParam(value = "id") Integer id) {
		Boolean exclusao = oportunidadeSyncService.deleteOportunidade(id);
		return (ResponseEntity<Boolean>) ResponseEntity.ok(exclusao);
	}

}
