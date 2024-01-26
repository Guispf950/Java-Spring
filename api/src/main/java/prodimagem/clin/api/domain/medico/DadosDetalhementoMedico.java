package prodimagem.clin.api.domain.medico;

import prodimagem.clin.api.domain.endereco.Endereco;

public record DadosDetalhementoMedico(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosDetalhementoMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(),
                medico.getEndereco());
    }
}
