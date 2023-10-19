package tn.esprit.spring.khaddem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Universite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUniversite;
    private String nomUniv;
    @OneToMany(cascade = CascadeType.ALL)
  //  @JsonIgnore
    private List<Departement>departements;

    public Universite(String nomUniv, List<Departement> departements) {
        this.nomUniv = nomUniv;
        this.departements = departements;
    }

}
