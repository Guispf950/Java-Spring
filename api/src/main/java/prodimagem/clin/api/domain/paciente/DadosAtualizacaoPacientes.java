package prodimagem.clin.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import prodimagem.clin.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPacientes(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        DadosEndereco endereco
) {

}