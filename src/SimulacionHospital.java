import java.util.*;

public class SimulacionHospital {

    public static void main(String[] args) {
        System.out.println("=== Escenario 1: Flujo Normal ===");
        simularHospital(10, 15, 1440, 42, false);

        System.out.println("\n=== Escenario 2: Alta Sobrecarga ===");
        simularHospital(8, 15, 1440, 42, false);

        System.out.println("\n=== BONUS: Priorización Automática ===");
        simularHospital(10, 15, 1440, 42, true);
    }

    public static void simularHospital(int intervaloLlegada, int intervaloAtencion,
                                       int tiempoTotal, long seed, boolean usarBonus) {

        Random rand = new Random(seed);

        Hospital hospital = new Hospital(
                new HashPacientesEncadenado(10007),
                new ArbolPacientes(),
                new ColaPrioridadPacientes()
        );

        Map<Integer, List<Long>> tiemposEsperaPorCategoria = new HashMap<>();
        Map<Integer, Integer> atendidosPorCategoria = new HashMap<>();
        Map<Integer, Integer> enEsperaPorCategoria = new HashMap<>();

        for (int c = 1; c <= 5; c++) {
            tiemposEsperaPorCategoria.put(c, new ArrayList<>());
            atendidosPorCategoria.put(c, 0);
            enEsperaPorCategoria.put(c, 0);
        }

        int[] probabilidades = {10, 15, 18, 27, 30};
        int[] acumulado = new int[5];
        acumulado[0] = probabilidades[0];
        for (int i = 1; i < 5; i++) acumulado[i] = acumulado[i - 1] + probabilidades[i];

        int LIMITE_ESPERA = 180;  

        for (int t = 0; t <= tiempoTotal; t++) {

            
            if (t % intervaloLlegada == 0) {
                String id = "RUT-" + rand.nextInt(Integer.MAX_VALUE);
                String nombre = "Paciente-" + t;
                int catRand = rand.nextInt(100) + 1;
                int categoria = 5;

                for (int i = 0; i < 5; i++) {
                    if (catRand <= acumulado[i]) {
                        categoria = i + 1;
                        break;
                    }
                }

                Paciente p = new Paciente(id, nombre, categoria, t);
                hospital.registrarPaciente(p);
            }

            
            if (usarBonus) {
                for (Paciente px : hospital.salaEspera.values()) {
                    long tiempoEspera = t - px.getTiempoLlegada();
                    if (tiempoEspera >= LIMITE_ESPERA && px.getCategoria() > 1) {

                        px.setCategoria(px.getCategoria() - 1);
                        px.registrarCambio("Subió de categoría por esperar más de 3 horas");

                        
                        hospital.colaAtencion.insertar(px);
                    }
                }
            }

            
            if (t % intervaloAtencion == 0 && !hospital.colaAtencion.estaVacia()) {
                Paciente p = hospital.atenderSiguiente();
                long espera = t - p.getTiempoLlegada();

                tiemposEsperaPorCategoria.get(p.getCategoria()).add(espera);
                atendidosPorCategoria.put(p.getCategoria(),
                        atendidosPorCategoria.get(p.getCategoria()) + 1);
            }
        }

       
        for (Paciente p : hospital.salaEspera.values()) {
            enEsperaPorCategoria.put(p.getCategoria(),
                    enEsperaPorCategoria.get(p.getCategoria()) + 1);
        }

        System.out.println("\n--- RESULTADOS ---");

        System.out.println("Pacientes atendidos por categoría:");
        for (int c = 1; c <= 5; c++)
            System.out.printf("C%d: %d%n", c, atendidosPorCategoria.get(c));

        System.out.println("Pacientes en espera al final:");
        for (int c = 1; c <= 5; c++)
            System.out.printf("C%d: %d%n", c, enEsperaPorCategoria.get(c));

        System.out.println("Tiempo de espera promedio por categoría (minutos):");
        for (int c = 1; c <= 5; c++) {
            List<Long> lista = tiemposEsperaPorCategoria.get(c);
            double promedio = lista.isEmpty()
                    ? 0
                    : lista.stream().mapToLong(Long::longValue).average().orElse(0);
            System.out.printf("C%d: %.2f%n", c, promedio);
        }
    }
}
