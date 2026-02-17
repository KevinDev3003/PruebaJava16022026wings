package prueba.wings.pruebaJavaWings.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTOS_FAMILIAS")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FAMILIA")
    private Integer idFamilia;

    @Column(name = "NOMBRE_FAMILIA", length = 255, nullable = false)
    private String nombreFamilia;

    public Integer getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(Integer idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }
}
