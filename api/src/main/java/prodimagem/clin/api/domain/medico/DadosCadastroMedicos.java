package prodimagem.clin.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import prodimagem.clin.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedicos(
        @NotBlank //verifica se esta vazio e nulo só funciona com STRING
        String nome,
        @NotBlank
        @Email //verifica se tem formato de email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // o "d" especifica que são digitos e o "{4,6}" que é de 4 a 6 digitos
        String crm,
        @NotBlank
        String telefone,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid // indica que endereco é um DTO(record) e que vai ter outras bean validations dentro de DadosEndereco,
               //ou seja faz as validações que estao em DadosEndereco (03-aula 10)
        DadosEndereco endereco) { }
