public class ColaPrioridadPacientes {

    private Paciente[] heap;
    private int tamano;
    private int capacidad;

    public ColaPrioridadPacientes() {
        this.capacidad = 100;
        this.heap = new Paciente[capacidad];
        this.tamano = 0;
    }

    public ColaPrioridadPacientes(int capacidad) {
        this.capacidad = capacidad;
        this.heap = new Paciente[capacidad];
        this.tamano = 0;
    }

    boolean estaVacia() {
        return tamano == 0;
    }

    void insertar(Paciente p) {
        if (tamano == capacidad) {
            redimensionar();
        }
        heap[tamano] = p;
        tamano++;
        subir(tamano - 1);
    }

    Paciente obtenerMin() {
        if (estaVacia()) return null;
        return heap[0];
    }

    Paciente extraerMin() {
        if (estaVacia()) return null;

        Paciente raiz = heap[0];
        heap[0] = heap[tamano - 1];
        tamano--;
        bajar(0);
        return raiz;
    }

    void subir(int i) {
        while (i > 0 && comparar(heap[i], heap[padre(i)]) < 0) {
            intercambiar(i, padre(i));
            i = padre(i);
        }
    }

    void bajar(int i) {
        int izq = izquierdo(i);
        int der = derecho(i);
        int menor = i;

        if (izq < tamano && comparar(heap[izq], heap[menor]) < 0) {
            menor = izq;
        }
        if (der < tamano && comparar(heap[der], heap[menor]) < 0) {
            menor = der;
        }

        if (menor != i) {
            intercambiar(i, menor);
            bajar(menor);
        }
    }

    int comparar(Paciente a, Paciente b) {
        if (a.getCategoria() != b.getCategoria()) {
            return a.getCategoria() - b.getCategoria();
        } else {
            return Long.compare(a.getTiempoLlegada(), b.getTiempoLlegada());
        }
    }

    void intercambiar(int i, int j) {
        Paciente temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    int padre(int i) {
        return (i - 1) / 2;
    }

    int izquierdo(int i) {
        return 2 * i + 1;
    }

    int derecho(int i) {
        return 2 * i + 2;
    }

    void redimensionar() {
        capacidad *= 2;
        Paciente[] newHeap = new Paciente[capacidad];
        System.arraycopy(heap, 0, newHeap, 0, tamano);
        heap = newHeap;
    }

    int getTamano() {
        return tamano;
    }
}
