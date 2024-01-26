package prodimagem.clin.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> { // necessario indicar o tipo da entidade Medico e o
                                                                            // tipo da chave primaria dessa entidade <Medico, Long>
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    //Pelo nome que atribuimos ao metodo o proprio spring identifica o tipo de consulta no banco, nesse caso ele vai trazer
    // os medicos que possuem o atributo ativo igual a true

    //"migration" geralmente se refere a operações que envolvem a modificação do esquema do banco de dados,
    // como adicionar, modificar ou excluir tabelas e colunas.
    //OBS: Migrations executadas nao podem ser modificadas, é necessario criar uma nova
}
