import java.util.*;

public class ExperimentoHashVSBST {

    public static void main(String[] args) {
        int[] N_values = {10000, 50000, 100000, 500000, 1000000};
        int k = 10000;
        Random rand = new Random();

        System.out.printf("%-10s %-25s %-25s %-25s %-25s%n",
                "N",
                "Insert Hash (ms)",
                "Insert BST (ms)",
                "Busqueda Hash (ms)",
                "Busqueda BST (ms)");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (int N : N_values) {

            HashPacientesEncadenado hash = new HashPacientesEncadenado(N * 2 + 1);

            Map<String, Paciente> bst = new TreeMap<>();

            List<String> ids = new ArrayList<>();
            List<Paciente> pacientes = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                String id = "RUT-" + i;
                ids.add(id);

                pacientes.add(new Paciente(
                        id,
                        "Paciente" + i,
                        1 + rand.nextInt(5),
                        System.currentTimeMillis() + rand.nextInt(10000)
                ));
            }

            long inicioHash = System.nanoTime();
            for (Paciente p : pacientes) hash.put(p.getId(), p);
            long finHash = System.nanoTime();
            double tiempoInsertHashMs = (finHash - inicioHash) / 1_000_000.0;

            long inicioBST = System.nanoTime();
            for (Paciente p : pacientes) bst.put(p.getId(), p);
            long finBST = System.nanoTime();
            double tiempoInsertBSTMs = (finBST - inicioBST) / 1_000_000.0;

            List<String> idsBusqueda = new ArrayList<>();
            for (int i = 0; i < k; i++) idsBusqueda.add(ids.get(rand.nextInt(ids.size())));

            long inicioBusqHash = System.nanoTime();
            for (String id : idsBusqueda) hash.get(id);
            long finBusqHash = System.nanoTime();
            double tiempoBusqHashMs = (finBusqHash - inicioBusqHash) / 1_000_000.0;

            long inicioBusqBST = System.nanoTime();
            for (String id : idsBusqueda) bst.get(id);
            long finBusqBST = System.nanoTime();
            double tiempoBusqBSTMs = (finBusqBST - inicioBusqBST) / 1_000_000.0;

            System.out.printf("%-10d %-25.5f %-25.5f %-25.5f %-25.5f%n",
                    N, tiempoInsertHashMs, tiempoInsertBSTMs, tiempoBusqHashMs, tiempoBusqBSTMs);
        }

        System.out.println("\n--- Peor caso BST (IDs ordenados) ---");
        System.out.printf("%-10s %-25s %-25s %-25s %-25s%n",
                "N",
                "Insert Hash (ms)",
                "Insert BST (ms)",
                "Busqueda Hash (ms)",
                "Busqueda BST (ms)");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (int N : N_values) {

            HashPacientesEncadenado hash = new HashPacientesEncadenado(N * 2 + 1);

            Map<String, Paciente> bst = new TreeMap<>();

            List<String> idsOrdenados = new ArrayList<>();
            List<Paciente> pacientesOrdenados = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                String id = String.format("RUT-%09d", i);
                idsOrdenados.add(id);

                pacientesOrdenados.add(new Paciente(
                        id,
                        "Paciente" + i,
                        1 + rand.nextInt(5),
                        System.currentTimeMillis() + rand.nextInt(10000)
                ));
            }

            long inicioHash = System.nanoTime();
            for (Paciente p : pacientesOrdenados) hash.put(p.getId(), p);
            long finHash = System.nanoTime();
            double tiempoInsertHashMs = (finHash - inicioHash) / 1_000_000.0;

            long inicioBST = System.nanoTime();
            for (Paciente p : pacientesOrdenados) bst.put(p.getId(), p);
            long finBST = System.nanoTime();
            double tiempoInsertBSTMs = (finBST - inicioBST) / 1_000_000.0;

            List<String> idsBusqueda = new ArrayList<>();
            for (int i = 0; i < k; i++) idsBusqueda.add(idsOrdenados.get(rand.nextInt(idsOrdenados.size())));

            long inicioBusqHash = System.nanoTime();
            for (String id : idsBusqueda) hash.get(id);
            long finBusqHash = System.nanoTime();
            double tiempoBusqHashMs = (finBusqHash - inicioBusqHash) / 1_000_000.0;

            long inicioBusqBST = System.nanoTime();
            for (String id : idsBusqueda) bst.get(id);
            long finBusqBST = System.nanoTime();
            double tiempoBusqBSTMs = (finBusqBST - inicioBusqBST) / 1_000_000.0;

            System.out.printf("%-10d %-25.5f %-25.5f %-25.5f %-25.5f%n",
                    N, tiempoInsertHashMs, tiempoInsertBSTMs, tiempoBusqHashMs, tiempoBusqBSTMs);
        }
    }
}
