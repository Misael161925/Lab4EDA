import java.util.Map;

public class Hospital {

    Map<String, Paciente> salaEspera;
    ArbolPacientes historicoPacientes;
    ColaPrioridadPacientes colaAtencion;

    public Hospital(Map<String, Paciente> implementacionHash) {
        this.salaEspera = implementacionHash;
        this.historicoPacientes = new ArbolPacientes();
        this.colaAtencion = new ColaPrioridadPacientes();
    }

    public Hospital(Map<String, Paciente> salaEspera,
                    ArbolPacientes historico,
                    ColaPrioridadPacientes cola) {
        this.salaEspera = salaEspera;
        this.historicoPacientes = historico;
        this.colaAtencion = cola;
    }

    public Hospital() {
        this(new HashPacientesEncadenado());
    }

    public void registrarPaciente(Paciente p) {
        salaEspera.put(p.getId(), p);
        colaAtencion.insertar(p);
        p.registrarCambio("Ingresó al hospital");
    }

    public Paciente atenderSiguiente() {
        Paciente p = colaAtencion.extraerMin();
        if (p != null) {
            salaEspera.remove(p.getId());
            historicoPacientes.insertar(p);
            p.registrarCambio("Fue atendido");
        }
        return p;
    }

    public Map<String, Paciente> getSalaEspera() { return salaEspera; }
    public ColaPrioridadPacientes getColaAtencion() { return colaAtencion; }
}
