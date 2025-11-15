import java.util.*;

public class HashPacientesEncadenado implements Map<String, Paciente> {

    private static class NodoHash {
        String clave;
        Paciente valor;

        NodoHash(String clave, Paciente valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private final LinkedList<NodoHash>[] tabla;
    private final int M;
    private int tamano;


    @SuppressWarnings("unchecked")
    public HashPacientesEncadenado(int M) {
        this.M = M;
        this.tabla = new LinkedList[M];
        this.tamano = 0;
    }

    public HashPacientesEncadenado() {
        this(101);
    }

    private int hash(String clave) {
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (31 * h + clave.charAt(i)) % M;
        }
        return Math.abs(h);
    }

    public Paciente put(String id, Paciente p) {
        int index = hash(id);
        if (tabla[index] == null) {
            tabla[index] = new LinkedList<>();
        }

        for (NodoHash nodo : tabla[index]) {
            if (nodo.clave.equals(id)) {
                Paciente antiguo = nodo.valor;
                nodo.valor = p;
                return antiguo;
            }
        }

        tabla[index].add(new NodoHash(id, p));
        tamano++;
        return null;
    }

    public Paciente get(Object id) {
        if (!(id instanceof String)) return null;
        int index = hash((String) id);
        LinkedList<NodoHash> lista = tabla[index];
        if (lista == null) return null;

        for (NodoHash nodo : lista) {
            if (nodo.clave.equals(id)) {
                return nodo.valor;
            }
        }
        return null;
    }

    public Paciente remove(Object id) {
        if (!(id instanceof String)) return null;
        int index = hash((String) id);
        LinkedList<NodoHash> lista = tabla[index];
        if (lista == null) return null;

        Iterator<NodoHash> it = lista.iterator();
        while (it.hasNext()) {
            NodoHash nodo = it.next();
            if (nodo.clave.equals(id)) {
                it.remove();
                tamano--;
                return nodo.valor;
            }
        }
        return null;
    }

    public int size() {
        return tamano;
    }

    public boolean isEmpty() {
        return tamano == 0;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        for (LinkedList<NodoHash> lista : tabla) {
            if (lista != null) {
                for (NodoHash nodo : lista) {
                    if (nodo.valor.equals(value)) return true;
                }
            }
        }
        return false;
    }

    public void putAll(Map<? extends String, ? extends Paciente> m) {
        for (Entry<? extends String, ? extends Paciente> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public void clear() {
        for (int i = 0; i < M; i++) {
            if (tabla[i] != null) {
                tabla[i].clear();
            }
        }
        tamano = 0;
    }

    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();
        for (LinkedList<NodoHash> lista : tabla) {
            if (lista != null) {
                for (NodoHash nodo : lista) {
                    keys.add(nodo.clave);
                }
            }
        }
        return keys;
    }

    public Collection<Paciente> values() {
        List<Paciente> valores = new ArrayList<>();
        for (LinkedList<NodoHash> lista : tabla) {
            if (lista != null) {
                for (NodoHash nodo : lista) {
                    valores.add(nodo.valor);
                }
            }
        }
        return valores;
    }

    public Set<Entry<String, Paciente>> entrySet() {
        Set<Entry<String, Paciente>> entries = new HashSet<>();
        for (LinkedList<NodoHash> lista : tabla) {
            if (lista != null) {
                for (NodoHash nodo : lista) {
                    entries.add(new AbstractMap.SimpleEntry<>(nodo.clave, nodo.valor));
                }
            }
        }
        return entries;
    }
}
