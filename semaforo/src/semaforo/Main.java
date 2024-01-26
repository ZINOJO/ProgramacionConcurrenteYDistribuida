package semaforo;

import java.util.concurrent.Semaphore;

class UsoDeRecursos {
    private Semaphore semaforo;
    private int unidadesDisponibles;

    public UsoDeRecursos(int k) {
        semaforo = new Semaphore(k, true); // Inicializar semáforo con k permisos y uso de fairness
        unidadesDisponibles = k;
    }

    public void reserva(int r) {
        try {
            semaforo.acquire(r); // Intentar adquirir r unidades del recurso
            unidadesDisponibles -= r;
            System.out.println(Thread.currentThread().getName() + " ha reservado " + r + " unidades. Unidades disponibles: " + unidadesDisponibles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void libera(int l) {
        semaforo.release(l); // Liberar l unidades del recurso
        unidadesDisponibles += l;
        System.out.println(Thread.currentThread().getName() + " ha liberado " + l + " unidades. Unidades disponibles: " + unidadesDisponibles);
    }
}

public class Main {
    public static void main(String[] args) {
        UsoDeRecursos usoRecursos = new UsoDeRecursos(5); // Por ejemplo, con 5 unidades disponibles

        // Crear algunos procesos que reserven y liberen unidades
        for (int i = 0; i < 10; i++) {
            int unidadesReservar = (int) (Math.random() * 3) + 1; // Reservar entre 1 y 3 unidades
            int unidadesLiberar = (int) (Math.random() * unidadesReservar) + 1; // Liberar entre 1 y unidadesReservar unidades

            Thread proceso = new Thread(() -> {
                usoRecursos.reserva(unidadesReservar);

                // Hacer algo con el recurso reservado (opcional)

                usoRecursos.libera(unidadesLiberar);
            });

            proceso.start();
        }
    }
}
