import java.util.*;

public class ArbolPacientes {

    private static class NodoArbol {
        Paciente paciente;
        NodoArbol izquierdo;
        NodoArbol derecho;

        NodoArbol(Paciente paciente) {
            this.paciente = paciente;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoArbol raiz;

    public ArbolPacientes() {
        this.raiz = null;
    }

    public void insertar(Paciente p) {
        raiz = insertarRec(raiz, p);
    }

    private NodoArbol insertarRec(NodoArbol nodo, Paciente p) {
        if (nodo == null) {
            return new NodoArbol(p);
        }

        int comparacion = p.getId().compareTo(nodo.paciente.getId());
        if (comparacion < 0) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, p);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRec(nodo.derecho, p);
        } else {
            nodo.paciente = p;
        }

        return nodo;
    }

    public Paciente buscar(String id) {
        NodoArbol nodo = buscarRec(raiz, id);
        return (nodo != null) ? nodo.paciente : null;
    }

    private NodoArbol buscarRec(NodoArbol nodo, String id) {
        if (nodo == null) return null;

        int comparacion = id.compareTo(nodo.paciente.getId());
        if (comparacion == 0) {
            return nodo;
        } else if (comparacion < 0) {
            return buscarRec(nodo.izquierdo, id);
        } else {
            return buscarRec(nodo.derecho, id);
        }
    }

    public List<Paciente> obtenerPacientesEnOrden() {
        List<Paciente> lista = new ArrayList<>();
        inOrderRec(raiz, lista);
        return lista;
    }

    private void inOrderRec(NodoArbol nodo, List<Paciente> lista) {
        if (nodo != null) {
            inOrderRec(nodo.izquierdo, lista);
            lista.add(nodo.paciente);
            inOrderRec(nodo.derecho, lista);
        }
    }

    public void imprimirInOrder() {
        List<Paciente> pacientes = obtenerPacientesEnOrden();
        for (Paciente p : pacientes) {
            System.out.println(p.getId() + " - " + p.getNombre());
        }
    }
}
