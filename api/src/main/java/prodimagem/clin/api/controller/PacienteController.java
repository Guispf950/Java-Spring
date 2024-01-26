package prodimagem.clin.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import prodimagem.clin.api.domain.paciente.*;


@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPacientes dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri(); //URL- O objetivo é associar um endereço remoto com um nome de recurso na Internet. URI - é o identificador do recurso
        //http://localhost:8080/medicos/{id}

        return ResponseEntity.created(uri).body(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacientes>>listar(Pageable paginacao){ //é possivel especificar a paginacao dos registros aqui no back, definindo o size e as paginas
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page); //o metodo findAll sem argumentos é diferente do findAll com um Pageable no parametro
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPacientes dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
       var paciente = repository.getReferenceById(id);
       return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

    }
}
