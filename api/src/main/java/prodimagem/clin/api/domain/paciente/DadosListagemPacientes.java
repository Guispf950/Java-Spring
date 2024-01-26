package prodimagem.clin.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import prodimagem.clin.api.domain.endereco.Endereco;

public record DadosListagemPacientes(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        String cpf,
        @NotNull
        @Valid
        Endereco endereco
        ) {
            public DadosListagemPacientes(Paciente paciente){
                this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(),paciente.getEndereco());
            }
            }
