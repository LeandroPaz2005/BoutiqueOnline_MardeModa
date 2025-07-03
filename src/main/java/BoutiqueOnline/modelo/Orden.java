package BoutiqueOnline.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;
    private double total;
    private String estado;

    //relacion de mucho a uno: muchos usuarios pueden tener una orden
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //relacion de uno a uno con detalle: un orden puede tener un detalle de orden
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalle = new ArrayList<>();

    public Orden() {
    }

    public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecibida, double total) {
        super();
        this.id = id;
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecibida = fechaRecibida;
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetalleOrden> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleOrden> detalle) {
        this.detalle = detalle;
    }

    //metodo para los estados de los productos
    public String getClaseEstado() {
        if (estado == null) {
            return "badge bg-secondary";
        }

        switch (estado.toUpperCase()) {
            case "ENTREGADO":
                return "badge bg-success";
            case "PENDIENTE":
                return "badge bg-warning text-dark";
            case "NUEVO":
                return "badge bg-primary";
            default:
                return "badge bg-secondary";
        }

    }

    @Override
    public String toString() {
        return "Orden del pedido"
                + "Id:" + id
                + "Numero:" + numero
                + "Fecha Creacion:" + fechaCreacion
                + "Fecha Recibida:" + fechaRecibida
                + "Total:" + total;
    }

   
}
