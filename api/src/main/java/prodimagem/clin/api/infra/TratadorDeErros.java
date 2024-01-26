package prodimagem.clin.api.infra;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    //A anotação @RestControllerAdviceé uma anotação do Spring Framework que é usada para marcar classes que desejam fornecer conselhos (advice) para controladores
    // que respondam às requisições REST em um aplicativo Spring. Em termos simples, ela é usada para centralizar e gerenciar a lógica de tratamento de abordagens e
    // outras questões globais para controladores REST.

    @ExceptionHandler(EntityNotFoundException.class) // toda vez que for lançada uma Exception Entity Not Found Exception o spring chamara esse metodo
    public ResponseEntity tratarErro404(){

        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class) //essa exception é lançada pelo Bean Validation lança quando existe algum campo inválido
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){ // é possivel receber a exception lançada colocando como parametro
        var erros = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); //stream usado juntamente com map para converter o objeto erro(FieldError) para o DTO DadosErroValidacao
    }

    private record DadosErroValidacao(String campo, String mensagem){ //é criado um DTO dentro da classe mesmo, pois será utilizado somente aqui
        public DadosErroValidacao(FieldError erro){ //construtor que recebe um FieldError como parametro e atribui os campos desse field erro para as variaveis campo e mensagem
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
