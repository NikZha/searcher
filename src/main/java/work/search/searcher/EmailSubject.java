package work.search.searcher;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("EMAILSUBJECT")
@NoArgsConstructor
public class EmailSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    private LocalDate dateQuery;
    
    private String searchQuery;

    private String findedEmail;

    private String findedLink;
    

}
