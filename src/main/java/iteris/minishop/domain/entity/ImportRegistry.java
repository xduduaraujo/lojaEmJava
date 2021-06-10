package iteris.minishop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ImportRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idReg;

    @Column(name = "Success", nullable = false)
    private int qtdSucesso;

    @Column(name = "Failure", nullable = false)
    private int qtdFalha;

    @Column(name = "InsertionDate", nullable = false)
    private LocalDateTime dataReg;

    @Column(name = "FileName", length = 250,nullable = false)
    private String nomeDoArquivo;

}
