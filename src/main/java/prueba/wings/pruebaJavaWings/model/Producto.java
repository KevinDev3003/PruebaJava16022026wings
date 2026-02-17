package prueba.wings.pruebaJavaWings.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
public class Producto {

    @Id
    @Column(name = "ID_PRODUCTO")
    private Integer idProducto;

    @Column(name = "NOMBRE_PRODUCTO", length = 255, nullable = false)
    private String nombreProducto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_FAMILIA", nullable = false)
    private Familia familia;

    @Column(name = "PRECIO", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
