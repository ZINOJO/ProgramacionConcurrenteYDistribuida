package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Conectar al servidor en localhost:12345

            // Crear streams de entrada/salida para comunicarse con el servidor
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            // Recibir opciones del servidor y mostrarlas al cliente
            String options = (String) input.readObject();
            System.out.println("Opciones del servidor:\n" + options);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Seleccione una opción: ");
                String userChoice = scanner.nextLine();

                // Enviar la opción seleccionada al servidor
                output.writeObject(userChoice);

                // Recibir y mostrar la respuesta del servidor
                String serverResponse = (String) input.readObject();
                System.out.println("Respuesta del servidor: " + serverResponse);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
