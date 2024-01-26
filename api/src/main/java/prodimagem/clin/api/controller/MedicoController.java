package prodimagem.clin.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import prodimagem.clin.api.domain.medico.*;


@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired // A anotação @Autowired permite que um contêiner de injeção de dependência
    // automaticamente forneça e injete instâncias de objetos dependentes correspondendo ao tipo de dados do objeto anotado,
    // Isso elimina a necessidade de criar manualmente essas instâncias
    private MedicoRepository repository;
    @PostMapping //@PostMapping é uma anotação para mapear métodos de controle (controllers) que lidam com solicitações HTTP do tipo POST. Essa anotação indica que o método associado deve ser chamado quando ocorre uma solicitação HTTP POST para o URI especificado

    @Transactional //Quando um método ou classe é marcado com @Transactional, o framework garante que a execução desse código ocorra dentro de uma transação. Se a execução for bem-sucedida, a transação é comprometida (commit).// No entanto, se ocorrer uma exceção durante a execução, a transação é revertida (rollback)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder){ // o uriBuilder é instanciando quando é realizada uma requisição para esse endpoint
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhementoMedico(medico)); //retornar o codigo 201, URI e a representação do registro/recurso criado
    }
    @GetMapping //nao precisa do @Transactional pois nao esta tendo alteralções no banco  //A classe Pageable permite especificar o número da página (page), o tamanho da página (size) e as informações de ordenação como o sort
    public ResponseEntity<Page<DadosListagemMedicos>> listar(@PageableDefault(size = 10, page = 0 , sort = {"nome"}) Pageable paginacao) { //o objeto Pageable recebe as informações da url ...0/medicos?size=1&page=2, o size é para quantos registros vao ser obtidos por paginas e a pagina é   quantas paginas vao vir com a requisição
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page); //devolve o codigo 200(resposta de status de sucesso que indica que a requisição foi bem sucedida) e junto devolver o objeto page

    }
        /*@GetMapping
        public List<DadosListagemMedicos>listar( ) {
         return repository.findAll().stream().map(DadosListagemMedicos::new).toList();
        } <-- metodo de listagem sem paginação */
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicos dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhementoMedico(medico)); // devolve o codigo 200 com o medico em especifico atualizado
    }


    @DeleteMapping("/{id}") //EXCLUSAO LOGICA,onde o registro do medico nao é apagado somente marcado como inativo
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){ // A anotacao @PathVariable indica que o id é o complemento da URL .../{id}, isso é chamado de parametro dinâmico
        var medico = repository.getReferenceById(id);
        medico.excluir(); //como esta usando a anotacao  @Transactional qualquer alteraçao feita ja é atualizada no banco
        return ResponseEntity.noContent().build(); //A api retorna um codigo especifico,nesse caso o 204 (foi processado com sucesso e não tem contéudo de resposta)
    }
    /*@DeleteMapping("/{id}")  DELETE, o registro do medico é totalmente apagado do sistema
    @Transactional
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    } */
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){ // A anotacao @PathVariable indica que o id é o complemento da URL .../{id}, isso é chamado de parametro dinâmico
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhementoMedico(medico));
    }
}
