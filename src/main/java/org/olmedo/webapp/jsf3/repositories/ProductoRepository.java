package org.olmedo.webapp.jsf3.repositories;

import org.olmedo.webapp.jsf3.entities.Producto;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto>{
    List<Producto> buscarPorNombre(String nombre);
    /*4  despues el ProductoRepositoryImpl en ves de heredar de CrudRepository ahora va a heredar de ProductoRepositoty*/
}
