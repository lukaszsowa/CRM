package pl.lukaszsowa.CRM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter first name!")
    private String firstName;

    @NotBlank(message = "Enter last name!")
    private String lastName;

    @NotBlank(message="Enter job title!")
    private String jobTitle;

    @NotBlank(message = "Enter lead source!")
    private String leadSource;

    @NotBlank(message = "Enter status!")
    private String status;

    @NotBlank(message = "Enter company!")
    private String company;

    @NotBlank(message = "Enter phone number!")
    private String phone;

    private boolean doNotCall;

    @NotBlank(message = "Enter e-mail")
    private String email;

    private boolean emailOutput;

    private LocalDateTime createTime;

}
