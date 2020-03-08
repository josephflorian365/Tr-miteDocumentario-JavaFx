

package model.dao;

import java.util.List;


public interface CRUD<Generic> {
    public List Listar();
    public int add(Object[] o);
    public int update(Object[] o);
    public void eliminar(int id);
    
}
