

package model.dominio;





public class Area {
    
    private int id;
    private String nombre;
    private String estado;
    public String valor = "NINGUNO";
    
    //Button


    
    public Area() {
    }

    public Area(int id, String nombre, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }





}
