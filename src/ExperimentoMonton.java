import java.util.*;

public class ExperimentoMonton {

    public static void main(String[] args) {
        int[] N_values = {10000, 50000, 100000, 250000, 500000, 750000, 1000000};
        Random rand = new Random();

        System.out.printf("%-10s %-25s %-25s%n", "N", "Tiempo Insertar (ms)", "Tiempo ExtraerMin (ms)");
        System.out.println("-------------------------------------------------------------");

        for (int N : N_values) {
            ColaPrioridadPacientes cola = new ColaPrioridadPacientes();

            List<Paciente> pacientes = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                String id = "RUT-" + (rand.nextInt(Integer.MAX_VALUE));
                int categoria = 1 + rand.nextInt(5);
                long tiempoLlegada = System.currentTimeMillis() + rand.nextInt(10000);
                pacientes.add(new Paciente(id, "Paciente" + i, categoria, tiempoLlegada));
            }

            long inicioInsert = System.nanoTime();
            for (Paciente p : pacientes) {
                cola.insertar(p);
            }
            long finInsert = System.nanoTime();
            double tiempoInsertMs = (finInsert - inicioInsert) / 1_000_000.0;

            long inicioExtraer = System.nanoTime();
            while (!cola.estaVacia()) {
                cola.extraerMin();
            }
            long finExtraer = System.nanoTime();
            double tiempoExtraerMs = (finExtraer - inicioExtraer) / 1_000_000.0;

            System.out.printf("%-10d %-25.5f %-25.5f%n", N, tiempoInsertMs, tiempoExtraerMs);
        }
    }
}

