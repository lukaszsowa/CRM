package pl.lukaszsowa.CRM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private Date dateStart;

    private Date dateEnd;

    @NotBlank
    private String localization;

    @NotNull
    private int capacity;

    @NotNull
    private int freePlaces;

    @NotBlank
    private String trainer;

    @ManyToMany(mappedBy = "selectedTrainings")
    Set<Contact> contactSet;

    


}
