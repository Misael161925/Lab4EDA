import java.util.*;

public class HashPacientesSondeoLineal implements Map<String, Paciente> {

    private static final Paciente MARCADOR_ELIMINADO = new Paciente("DELETED", "—", 5, 0);

    private Paciente[] tabla;
    private String[] claves;
    private int M;
    private int tamano;

    public HashPacientesSondeoLineal(int M) {
        this.M = M;
        this.tabla = new Paciente[M];
        this.claves = new String[M];
        this.tamano = 0;
    }

    public HashPacientesSondeoLineal() {
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
        if (tamano >= M * 0.7) {
            redimensionar(M * 2);
        }

        int index = hash(id);
        while (claves[index] != null && !claves[index].equals(id)) {
            index = (index + 1) % M;
        }

        Paciente antiguo = tabla[index];
        if (antiguo == null || antiguo == MARCADOR_ELIMINADO) {
            tamano++;
        }

        claves[index] = id;
        tabla[index] = p;
        return (antiguo == MARCADOR_ELIMINADO) ? null : antiguo;
    }

    public Paciente get(Object id) {
        if (!(id instanceof String)) return null;
        String clave = (String) id;

        int index = hash(clave);
        int intentos = 0;

        while (claves[index] != null && intentos < M) {
            if (claves[index].equals(clave) && tabla[index] != MARCADOR_ELIMINADO) {
                return tabla[index];
            }
            index = (index + 1) % M;
            intentos++;
        }
        return null;
    }

    public Paciente remove(Object id) {
        if (!(id instanceof String)) return null;
        String clave = (String) id;

        int index = hash(clave);
        int intentos = 0;

        while (claves[index] != null && intentos < M) {
            if (claves[index].equals(clave) && tabla[index] != MARCADOR_ELIMINADO) {
                Paciente eliminado = tabla[index];
                tabla[index] = MARCADOR_ELIMINADO;
                tamano--;
                return eliminado;
            }
            index = (index + 1) % M;
            intentos++;
        }
        return null;
    }

    private void redimensionar(int nuevaCapacidad) {
        HashPacientesSondeoLineal nuevaTabla = new HashPacientesSondeoLineal(nuevaCapacidad);
        for (int i = 0; i < M; i++) {
            if (claves[i] != null && tabla[i] != null && tabla[i] != MARCADOR_ELIMINADO) {
                nuevaTabla.put(claves[i], tabla[i]);
            }
        }
        this.M = nuevaCapacidad;
        this.claves = nuevaTabla.claves;
        this.tabla = nuevaTabla.tabla;
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
        for (Paciente p : tabla) {
            if (p != null && p != MARCADOR_ELIMINADO && p.equals(value)) return true;
        }
        return false;
    }

    public void putAll(Map<? extends String, ? extends Paciente> m) {
        for (Entry<? extends String, ? extends Paciente> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public void clear() {
        Arrays.fill(tabla, null);
        Arrays.fill(claves, null);
        tamano = 0;
    }

    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();
        for (String k : claves) {
            if (k != null) keys.add(k);
        }
        return keys;
    }

    public Collection<Paciente> values() {
        List<Paciente> vals = new ArrayList<>();
        for (Paciente p : tabla) {
            if (p != null && p != MARCADOR_ELIMINADO) vals.add(p);
        }
        return vals;
    }

    public Set<Entry<String, Paciente>> entrySet() {
        Set<Entry<String, Paciente>> entries = new HashSet<>();
        for (int i = 0; i < M; i++) {
            if (claves[i] != null && tabla[i] != null && tabla[i] != MARCADOR_ELIMINADO) {
                entries.add(new AbstractMap.SimpleEntry<>(claves[i], tabla[i]));
            }
        }
        return entries;
    }
}
