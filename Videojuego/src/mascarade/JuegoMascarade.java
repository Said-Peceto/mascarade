package mascarade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JuegoMascarade {
    private List<Jugador> jugadores;
    private int jugadorActualIndex;
    private Random random;
    
    public JuegoMascarade(int numeroJugadores) {
        jugadores = new ArrayList<>();
        random = new Random();
        
        
        List<Rol> rolesDisponibles = generarRolesDisponibles(numeroJugadores);
        for (int i = 0; i < numeroJugadores; i++) {
            Jugador jugador = new Jugador("Jugador " + (i + 1));
            Rol rol = rolesDisponibles.remove(random.nextInt(rolesDisponibles.size()));
            jugador.setRol(rol);
            jugadores.add(jugador);
        }
        
        jugadorActualIndex = random.nextInt(numeroJugadores); 
    }
    
    private List<Rol> generarRolesDisponibles(int numeroJugadores) {
        List<Rol> rolesDisponibles = new ArrayList<>();
        rolesDisponibles.add(new Rol("Ladron"));
        rolesDisponibles.add(new Rol("Loco"));
        rolesDisponibles.add(new Rol("Emperatriz"));
        rolesDisponibles.add(new Rol("Estafador"));
        rolesDisponibles.add(new Rol("Mecenas"));
        rolesDisponibles.add(new Rol("Juez"));
        
        // Si hay más de 4 jugadores, agregar más roles disponibles
        if (numeroJugadores > 4) {
            rolesDisponibles.add(new Rol("Caballero"));
            rolesDisponibles.add(new Rol("Bruja"));
        }
        
        return rolesDisponibles;
    }
    
    public void iniciarJuego() {
       
        prepararJuego();
        
       
        Scanner scanner = new Scanner(System.in);
        boolean juegoTerminado = false;
        
        while (!juegoTerminado) {
            Jugador jugadorActual = jugadores.get(jugadorActualIndex);
            
            System.out.println("\nTurno de " + jugadorActual.getNombre());
            System.out.println("Tu rol: " + jugadorActual.getRol().getNombre());
            
            
            realizarAcciones(jugadorActual);
            
            
            if (jugadorActual.getMonedas() >= 13) {
                juegoTerminado = true;
            } else {
                // Pasar al siguiente jugador
                jugadorActualIndex = (jugadorActualIndex + 1) % jugadores.size();
            }
            
            
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
        }
        
  
        mostrarResultadosFinales();
    }
    
    private void prepararJuego() {
        // Colocar todas las fichas de moneda en el Banco
        for (Jugador jugador : jugadores) {
            jugador.setMonedas(0);
        }
    }
    
    private void realizarAcciones(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nAcciones disponibles:");
        System.out.println("a) Intercambio de mascaras");
        System.out.println("b) Habilidad especial del personaje");
        System.out.println("c) Anunciar el personaje");
        
        System.out.print("Elige una opción (a, b, c): ");
        String opcion = scanner.nextLine().toLowerCase();
        
        switch (opcion) {
            case "a":
                intercambioDeCartas(jugador);
                break;
            case "b":
                habilidadEspecial(jugador);
                break;
            case "c":
                anunciarPersonaje(jugador);
                break;
            default:
                System.out.println("Opción no válida. Intenta nuevamente.");
                realizarAcciones(jugador);
                break;
                
        }
    }
    
    private void intercambioDeCartas(Jugador jugador) {
        
        System.out.println("Intercambio de cartas seleccionado. Solicitar al jugador que seleccione otro jugador con quien desea intercambiar cartas.");
    }
    
    private void habilidadEspecial(Jugador jugador) {
        // Implementar la lógica de la habilidad especial del personaje
        System.out.println("Habilidad especial seleccionada. Selecciona segun tu personaje:\n"
        		+ "Ladron: El jugador puede seleccionar a otro jugador y robar \n"
        		+ "una moneda del Banco o robar una moneda de ese jugador.\n"
        		+ "Emperatriz: El jugador puede tomar 2 monedas del Banco y luego seleccionar a otro jugador para robar una carta del mazo \no una moneda de ese jugador.\r\n"
        		+ "Loco: El jugador puede cambiar su carta con la carta de otro jugador sin revelarlas.\r\n"
        		+ "Estafador: El jugador puede seleccionar a otro jugador y forzarlo a darle 2 monedas o intercambiar una carta con ese jugador.\r\n"
        		+ "Mecenas: El jugador puede tomar 2 monedas del Banco y luego dar 1 moneda a otro jugador de su elección.\r\n"
        		+ "Juez: El jugador puede seleccionar a otro jugador y robar 2 monedas de ese jugador.\n"
        		+ "Caballero: El jugador puede seleccionar a otro jugador y retar a un duelo. Ambos jugadores revelan sus cartas, y el jugador\n con el rol más alto roba 3 monedas del Banco. En caso de empate, no ocurre nada.\r\n"
        		+ "Bruja: El jugador puede seleccionar a otro jugador y forzarlo a intercambiar su carta con la de otro jugador de su eleccion.");
    }
    
    private void anunciarPersonaje(Jugador jugador) {
        System.out.print("Anuncia qué personaje crees que tienes: ");
        String personajeAnunciado = new Scanner(System.in).nextLine();
        
        if (personajeAnunciado.equalsIgnoreCase(jugador.getRol().getNombre())) {
            jugador.agregarMonedas(2);
            System.out.println("¡Adivinaste correctamente! Obtienes 2 monedas del Banco.");
        } else {
            jugador.restarMonedas(1);
            System.out.println("Lo siento, te equivocaste. Pagas 1 moneda al Banco.");
        }
    }
    
    private void mostrarResultadosFinales() {
        System.out.println("\n--- Resultados finales ---");
        
        Jugador ganador = null;
        int maxMonedas = Integer.MIN_VALUE;
        
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ": " + jugador.getMonedas() + " monedas");
            
            if (jugador.getMonedas() > maxMonedas) {
                maxMonedas = jugador.getMonedas();
                ganador = jugador;
            } else if (jugador.getMonedas() == maxMonedas) {
                ganador = null; // Empate
            }
        }
        
        if (ganador != null) {
            System.out.println("\n¡El ganador es " + ganador.getNombre() + "!");
        } else {
            System.out.println("\n¡Es un empate!");
        }
    }
    
    public static void main(String[] args) {
        JuegoMascarade juego = new JuegoMascarade(4); // Pasar el número de jugadores deseado
        juego.iniciarJuego();
    }
}
