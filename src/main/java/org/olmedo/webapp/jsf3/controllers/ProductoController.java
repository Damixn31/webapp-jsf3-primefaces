package org.olmedo.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.olmedo.webapp.jsf3.entities.Categoria;
import org.olmedo.webapp.jsf3.entities.Producto;
import org.olmedo.webapp.jsf3.services.ProductoService;

import java.util.List;
import java.util.ResourceBundle;

@Model // --> es un estereotype que junta @Named y @RequestScope es tipica para usar un cotrolador
public class ProductoController {

    private Producto producto;

    private Long id;

    @Inject
    private ProductoService service;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ResourceBundle bundle;

    private List<Producto> listado;

    /* 1. para crear el buscador */
    private String textoBuscar;

    @PostConstruct
    public void init() {
        this.listado = service.listar();
        producto = new Producto();
    }

    @Produces
    @Model /*--> por defecto el model va a tomar el nombre del metodo o de la clase si lo queremos
    modificar ahi si tenemos que usar el @Named y @RequestScope*/
    public String titulo() {
        //return "Hola mundo JavaServer Face 3.0";
        return bundle.getString("producto.texto.titulo");
    }

//    @Produces
//    @RequestScoped
//    @Named("listado")
//    public List<Producto> findAll() {
//        //    return Arrays.asList(new Producto("peras"), new Producto("manzanas"), new Producto("mandarinas"));
//        return service.listar();
//    }

    //@Produces
    //@Model
    public Producto producto() {
        this.producto = new Producto();
        if (id != null && id > 0) {
            //producto = service.porId(id).orElseThrow();
            service.porId(id).ifPresent(p -> {
                this.producto = p;
            });
        }
        return producto;
    }

    @Produces
    @Model
    public List<Categoria> categorias(){
        return service.listarCategorias();
    }

    public void editar(Long id) {
        this.id = id;
        producto();
    }

    public void guardar() {
        System.out.println(producto);
        if (producto.getId() != null && producto.getId() > 0) {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.editar"), producto.getNombre())));
        } else {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.crear"), producto.getNombre())));
        }
        service.guardar(producto);
        listado = service.listar();
        producto = new Producto();
    }

    public void eliminar(Producto producto) {
        service.eliminar(producto.getId());
        facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.eliminar"), producto.getNombre())));
        listado = service.listar();
    }

    /*8. el keyup  va a tener un listener en el controlador para buscar, despues nos vamos a index en el evento ajax*/
    public void buscar(){
        this.listado = service.buscarPorNombre(this.textoBuscar);
    }

    public void cerrarDialogo() {
        System.out.println("Cerrando la ventana de dialogo! ..................");
        producto = new Producto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Producto> getListado() {
        return listado;
    }

    public void setListado(List<Producto> listado) {
        this.listado = listado;
    }

    /*2. hacemos los getter and setter despues vamos al index y creamos el form*/
    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
