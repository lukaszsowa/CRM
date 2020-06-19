package pl.lukaszsowa.CRM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //Lista użytkowników
    //Jan Kowalski jan.kowalski jankowalski@crm.pl kowalski
    //Piotr Nowak piotr.nowak piotrnowak@crm.pl nowak

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Enter first name")
    private String firstName;

    @NotBlank(message = "Enter last name")
    private String lastName;

    @NotBlank(message = "Enter login")
    private String login;

    @Email
    @NotBlank(message = "Enter e-mail")
    private String email;

    @NotBlank(message = "Enter password")
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

}
