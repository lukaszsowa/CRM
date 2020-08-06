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

    @NotBlank
    private String companyName;

    @NotBlank
    private String nip;

    @NotBlank
    private String segment;

    @NotBlank
    private String industry;

    @NotBlank
    private String leadSource;

    @NotBlank
    private String status;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String website;

    @NotBlank
    private String fax;

    private Boolean permissionToCall;

    private Boolean permissionToEmail;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String postCode;

    @NotBlank
    private String country;



}
