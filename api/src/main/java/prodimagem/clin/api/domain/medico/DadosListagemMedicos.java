package prodimagem.clin.api.domain.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosListagemMedicos(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String crm,
        @NotNull
        Especialidade especialidade) {

    public DadosListagemMedicos(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade()); // é necessario chamar o construtor principal que esta na assinatura do Record entre os ()
    }
}
