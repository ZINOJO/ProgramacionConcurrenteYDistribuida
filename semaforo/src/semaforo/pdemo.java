import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Puente {
    private final Lock lock = new ReentrantLock();
    private final Condition nortePuedeCruzar = lock.newCondition();
    private final Condition surPuedeCruzar = lock.newCondition();
    private boolean cocheSurCruzando = false;
    private boolean cocheNorteCruzando = false;

    public void cruzarPuenteDesdeNorte() {
        lock.lock();
        try {
            while (cocheSurCruzando) {
                nortePuedeCruzar.await(); // Esperar si hay coche(s) del sur cruzando
            }
            cocheNorteCruzando = true;
            System.out.println("Coche del Norte cruzando el puente");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        try {
            // Hacer algo mientras el coche del norte cruza (opcional)
            Thread.sleep(1000); // Simulando tiempo de cruce
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        try {
            System.out.println("Coche del Norte ha cruzado el puente");
            cocheNorteCruzando = false;
            surPuedeCruzar.signal(); // Despierta a los coches del sur que esperan
        } finally {
            lock.unlock();
        }
    }

    public void cruzarPuenteDesdeSur() {
        lock.lock();
        try {
            while (cocheNorteCruzando) {
                surPuedeCruzar.await(); // Esperar si hay coche(s) del norte cruzando
            }
            cocheSurCruzando = true;
            System.out.println("Coche del Sur cruzando el puente");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        try {
            // Hacer algo mientras el coche del sur cruza (opcional)
            Thread.sleep(1000); // Simulando tiempo de cruce
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        try {
            System.out.println("Coche del Sur ha cruzado el puente");
            cocheSurCruzando = false;
            nortePuedeCruzar.signal(); // Despierta a los coches del norte que esperan
        } finally {
            lock.unlock();
        }
    }
} // Llave de cierre para la clase Puente
