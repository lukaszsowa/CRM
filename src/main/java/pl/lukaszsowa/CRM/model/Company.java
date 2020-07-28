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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter company name!")
    private String companyName;

    @NotBlank(message = "Enter NIP!")
    private String nip;

    @NotBlank(message = "Enter segment!")
    private String segment;

    @NotBlank(message = "Enter industry!")
    private String industry;

    @NotBlank(message = "Enter lead source!")
    private String leadSource;

    @NotBlank(message = "Enter company status!")
    private String status;

    @NotBlank(message = "Enter phone!")
    private String phone;

    @NotBlank(message = "Enter e-mail!")
    private String email;

    @NotBlank(message = "Enter website!")
    private String website;

    @NotBlank(message = "Enter fax!")
    private String fax;

    private Boolean permissionToCall;

    private Boolean permissionToEmail;

    @NotBlank(message = "Enter street!")
    private String street;

    @NotBlank(message = "Enter city!")
    private String city;

    @NotBlank(message = "Enter post code!")
    private String postCode;

    @NotBlank(message = "Enter country!")
    private String Country;



}
