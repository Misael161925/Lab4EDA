import java.util.*;

public class ExperimentoHash {

    public static void main(String[] args) {
        int M = 10007;
        int incremento = 1000;
        int maxPacientes = 9000;

        HashPacientesEncadenado hashEncadenado = new HashPacientesEncadenado(M);
        HashPacientesSondeoLineal hashLineal = new HashPacientesSondeoLineal(M);

        Random rand = new Random();
        List<String> idsInsertados = new ArrayList<>();

        System.out.printf("%-10s %-25s %-25s %-25s %-25s%n",
                "N",
                "Insert Encadenado (ms/op)",
                "Insert Sondeo (ms/op)",
                "Busqueda Encadenado (ms/op)",
                "Busqueda Sondeo (ms/op)");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (int n = incremento; n <= maxPacientes; n += incremento) {

            List<Paciente> nuevosPacientes = new ArrayList<>();
            for (int i = idsInsertados.size(); i < n; i++) {
                String id = "P" + i;
                idsInsertados.add(id);
                String nombre = "Paciente" + i;
                int categoria = 1 + rand.nextInt(5);
                long llegada = System.currentTimeMillis() + rand.nextInt(10000);
                nuevosPacientes.add(new Paciente(id, nombre, categoria, llegada));
            }

            long inicioEnc = System.nanoTime();
            for (Paciente p : nuevosPacientes) {
                hashEncadenado.put(p.getId(), p);
            }
            long finEnc = System.nanoTime();
            double tiempoInsertEncMs = (finEnc - inicioEnc) / 1_000_000.0;
            double tiempoInsertEncPorOp = tiempoInsertEncMs / nuevosPacientes.size();

            long inicioLin = System.nanoTime();
            for (Paciente p : nuevosPacientes) {
                hashLineal.put(p.getId(), p);
            }
            long finLin = System.nanoTime();
            double tiempoInsertLinMs = (finLin - inicioLin) / 1_000_000.0;
            double tiempoInsertLinPorOp = tiempoInsertLinMs / nuevosPacientes.size();

            List<String> idsBusqueda = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                idsBusqueda.add(idsInsertados.get(rand.nextInt(idsInsertados.size())));
            }

            long inicioBusqEnc = System.nanoTime();
            for (String id : idsBusqueda) {
                hashEncadenado.get(id);
            }
            long finBusqEnc = System.nanoTime();
            double tiempoBusquedaEncMs = (finBusqEnc - inicioBusqEnc) / 1_000_000.0;
            double tiempoBusquedaEncPorOp = tiempoBusquedaEncMs / idsBusqueda.size();

            long inicioBusqLin = System.nanoTime();
            for (String id : idsBusqueda) {
                hashLineal.get(id);
            }
            long finBusqLin = System.nanoTime();
            double tiempoBusquedaLinMs = (finBusqLin - inicioBusqLin) / 1_000_000.0;
            double tiempoBusquedaLinPorOp = tiempoBusquedaLinMs / idsBusqueda.size();

            System.out.printf("%-10d %-25.5f %-25.5f %-25.5f %-25.5f%n",
                    n,
                    tiempoInsertEncPorOp,
                    tiempoInsertLinPorOp,
                    tiempoBusquedaEncPorOp,
                    tiempoBusquedaLinPorOp);
        }
    }
}

