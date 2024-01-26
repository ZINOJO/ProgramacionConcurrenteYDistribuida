package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class chatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto del servidor

            while (true) {
                System.out.println("Esperando conexiones...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostName());

                // Crear un nuevo hilo para manejar la conexión con el cliente
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
            this.input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Enviar opciones al cliente
            output.writeObject("1. Opción A\n2. Opción B\n3. Opción C\n4. Opción D\n5. Opción E");

            while (true) {
                // Esperar la opción seleccionada por el cliente
                String clientChoice = (String) input.readObject();

                // Procesar la opción y enviar la respuesta correspondiente
                String response = processClientChoice(clientChoice);
                output.writeObject(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar los recursos al finalizar la conexión con el cliente
                input.close();
                output.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processClientChoice(String choice) {
        // Aquí puedes implementar la lógica para manejar las diferentes opciones del cliente
        // En este ejemplo, simplemente se devuelve un mensaje genérico
        return "Respuesta a la opción " + choice;
    }
}
