package work.search.searcher;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("EMAILSUPPLIERS")
@NoArgsConstructor
public class EmailSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    @NotBlank(message = "Enter query")
    private String searchQuery;

    private String findedEmail;

    private String findedLink;
    

}
