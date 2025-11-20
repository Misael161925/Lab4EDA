import java.util.Stack;

public class Paciente implements Comparable<Paciente> {

    String id;
    String nombre;
    int categoria;
    long tiempoLlegada;
    Stack<String> historialCambios;

    public Paciente(String id, String nombre, int categoria, long tiempoLlegada) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoLlegada = tiempoLlegada;
        this.historialCambios = new Stack<>();
    }

    public void registrarCambio(String descripcion) {
        historialCambios.push(descripcion);
    }


    public int compareTo(Paciente o) {
        if (this.categoria != o.categoria)
            return this.categoria - o.categoria;
        return Long.compare(this.tiempoLlegada, o.tiempoLlegada);
    }

    public String getId() { 
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public int getCategoria() { 
        return categoria; 
    }
    
    public void setCategoria(int categoria) {
        this.categoria = categoria; 
    }

    public long getTiempoLlegada() { 
        return tiempoLlegada; 
    }
    
    public void setTiempoLlegada(long tiempoLlegada) { 
        this.tiempoLlegada = tiempoLlegada; 
    }

    public Stack<String> getHistorialCambios() { 
        return historialCambios; 
    }
    
    public void setHistorialCambios(Stack<String> historialCambios) { 
        this.historialCambios = historialCambios; 
    }
}
