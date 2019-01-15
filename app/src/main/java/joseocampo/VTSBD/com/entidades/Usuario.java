package joseocampo.VTSBD.com.entidades;

public class Usuario {
    private Integer documento;
    private String nombre;
    private String profesion;
    public Usuario(Integer documento,String nombre,String profesion){
        this.documento=documento;
        this.nombre=nombre;
        this.profesion=profesion;
    }
    public Usuario(){

    }


    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
