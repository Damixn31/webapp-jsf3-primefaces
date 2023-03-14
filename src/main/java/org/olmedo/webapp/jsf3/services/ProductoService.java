package org.olmedo.webapp.jsf3.services;

import jakarta.ejb.Local;
import org.olmedo.webapp.jsf3.entities.Categoria;
import org.olmedo.webapp.jsf3.entities.Producto;

import java.util.List;
import java.util.Optional;

@Local
public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> porId(Long id);
    void guardar(Producto producto);
    void eliminar(Long id);
    List<Categoria> listarCategorias();
    Optional<Categoria> porIdCategoria(Long id);
    // 6. creamos para que devuelva una lista de producto, despues en ProductoServiceImpl  implementamos este metodo
    List<Producto> buscarPorNombre(String nombre);

}
