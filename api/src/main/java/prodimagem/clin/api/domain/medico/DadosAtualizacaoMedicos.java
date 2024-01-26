package prodimagem.clin.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import prodimagem.clin.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedicos(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
