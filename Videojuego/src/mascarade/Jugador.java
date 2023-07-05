package mascarade; 

public class Jugador {
    private String nombre;
    private Rol rol;
    private int monedas;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public int getMonedas() {
        return monedas;
    }
    
    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
    
    public void agregarMonedas(int cantidad) {
        monedas += cantidad;
    }
    
    public void restarMonedas(int cantidad) {
        monedas -= cantidad;
    }
}
