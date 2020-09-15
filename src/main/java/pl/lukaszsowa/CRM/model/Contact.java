package pl.lukaszsowa.CRM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String leadSource;

    @NotBlank
    private String status;

    @NotBlank
    private String phone;

    private boolean doNotCall;

    @NotBlank
    private String email;

    private boolean emailOutput;

    private LocalDateTime createTime;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToMany(mappedBy = "contactSet")
    Set<Training> trainingSet;

}
