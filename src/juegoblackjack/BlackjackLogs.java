/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoblackjack;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Andres
 */
public class BlackjackLogs {

    public static void main(String[] args) {

         try {
            File file = new File("logs.txt");
            FileWriter escribir = new FileWriter(file, true);

            int dinero;
            int apuesta = 0;
            boolean usuarioGanador;
            String texto = "Bienvenido al juego de Blackjack \n";
            System.out.println(texto);
            escribir.write(texto);
            dinero = 100;  // El usuario comienza con $ 100.

            while (true) {
                texto = ("Tienes " + dinero + " dalares.");
                System.out.println(texto);
                escribir.write(texto);
                do {
                    texto = ("¿Cuanto dienero quieres Apostar ?  (Entre  0 y y su cantidad maxima.)");
                    System.out.println(texto);
                    escribir.write(texto);
                    Scanner entrada = new Scanner(System.in);
                    apuesta = Integer.parseInt(entrada.nextLine());

                    if (apuesta < 0 || apuesta > dinero) {
                        texto = "Su respuesta debe estar entre 0 y " + dinero + " ";
                    }
                    System.out.println(texto);
                    escribir.write(texto);
                } while (apuesta < 0 || apuesta > dinero);
                if (apuesta == 0) {
                    break;
                }
                usuarioGanador = jugarBlackjack();
                if (usuarioGanador) {
                    dinero = dinero + apuesta;
                } else {
                    dinero = dinero - apuesta;
                }
                System.out.println();
                if (dinero == 0) {
                    texto = "Parece que te has quedado sin dinero!!!";
                    System.out.println(texto);
                    escribir.write(texto);
                    break;
                }
            }
            texto = "\n Sales con $ " + dinero + " ";
            System.out.println(texto);
            escribir.write(texto);
            escribir.close();
        } catch (Exception e) {
            System.out.println("No se encontró el archivo" + e);

        }

    } // fin del main()

    /*
   /**
    * Permita que el usuario juegue un juego de Blackjack, con la computadora como distribuidor.
    * @devuelve verdadero si el usuario gana el juego, falso si el usuario pierde.
     */
    static boolean jugarBlackjack() {

        juegoBlackjack.Baraja baraja;                  // Una baraja de cartas.  Una nueva bajara para cada juego.
        juegoBlackjack.ManoBlackjack manoDistribuidor;   // La mano del distribuidor.
        juegoBlackjack.ManoBlackjack manoUsuario;     // La mano del usuario .

        baraja = new juegoBlackjack.Baraja();
        manoDistribuidor = new juegoBlackjack.ManoBlackjack();
        manoUsuario = new juegoBlackjack.ManoBlackjack();

        /*  Baraja el mazo, luego reparte dos cartas a cada jugador. */
        baraja.barajar();
        manoDistribuidor.añadirCarta(baraja.cartaOferta());
        manoDistribuidor.añadirCarta(baraja.cartaOferta());
        manoUsuario.añadirCarta(baraja.cartaOferta());
        manoUsuario.añadirCarta(baraja.cartaOferta());

        System.out.println();
        System.out.println();

        /* Comprueba si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.
         */
        if (manoDistribuidor.getBlackjackValor() == 21) {
            System.out.println("El distribuidor tiene el " + manoDistribuidor.getCarta(0)
                    + " y el " + manoDistribuidor.getCarta(1) + ".");
            System.out.println("El usuario tiene el " + manoUsuario.getCarta(0)
                    + " y el " + manoUsuario.getCarta(1) + ".");
            System.out.println();
            System.out.println("El distribuidor tiene Blackjack. El distribuidor gana");
            return false;
        }

        if (manoUsuario.getBlackjackValor() == 21) {
            System.out.println("El distribuidor tiene el " + manoDistribuidor.getCarta(0)
                    + " y el " + manoDistribuidor.getCarta(1) + ".");
            System.out.println("El usuario tiene el " + manoUsuario.getCarta(0)
                    + " y el " + manoUsuario.getCarta(1) + ".");
            System.out.println();
            System.out.println("Tienes Blackjack. Tú ganas.");
            return true;
        }

        /*  Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
           tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
           cuando el usuario elige "Stand". Si el usuario supera los 21,
           el usuario pierde inmediatamente.
         */
        while (true) {

            /* Mostrar tarjetas de usuario y dejar que el usuario decida golpear o ponerse de pie. */
            System.out.println();
            System.out.println();
            System.out.println("Tus cartas son:");
            for (int i = 0; i < manoUsuario.getCountCarta(); i++) {
                System.out.println("    " + manoUsuario.getCarta(i));
            }
            System.out.println("Tu total es " + manoUsuario.getBlackjackValor());
            System.out.println();
            System.out.println("El distribuidor muestra el " + manoDistribuidor.getCarta(0));
            System.out.println();
            System.out.print("Pedir (H) o Plantarse (S)? ");
            char userAction;  // El usuario responde, 'H' o 'S'.
            do {
                userAction = Character.toUpperCase(juegoBlackjack.TextIO.getlnChar());
                if (userAction != 'H' && userAction != 'S') {
                    System.out.print("Por favor elija H o S:  ");
                }
            } while (userAction != 'H' && userAction != 'S');

            /* Si el usuario tiene éxito, el usuario obtiene una carta. Si el usuario se planta,
              el bucle termina (y es el turno del distribuidor de robar cartas).
             */
            if (userAction == 'S') {
                // bucle terminado; el usuario ha terminado de tomar cartas.
                break;
            } else {  //userAction es 'H'. Dale una carta al usuario. 
                // Si el usuario supera los 21, el usuario pierde.
                juegoBlackjack.Carta nuevaCarta = baraja.cartaOferta();
                manoUsuario.añadirCarta(nuevaCarta);
                System.out.println();
                System.out.println("Aciertos del usuario.");
                System.out.println("Tu carta es: " + nuevaCarta);
                System.out.println("El total que posees ahora es: " + manoUsuario.getBlackjackValor());
                if (manoUsuario.getBlackjackValor() > 21) {
                    System.out.println();
                    System.out.println("Has perdido yendo por encima de 21. Pierdes.");
                    System.out.println("La otra carta del distribuidor era el: " + manoDistribuidor.getCarta(1));
                    return false;
                }
            }

        } // end del ciclo while

        /* Si llegamos a este punto, el usuario se planta con 21 o menos.  Ahora es
          la oportunidad del distribuidor para sacar  El distribuidor roba cartas hasta que el 
           total sea> 16. Si el distribuidor supera los 21, el este pierde.
         */
        System.out.println();
        System.out.println("Usuario plantado");
        System.out.println("Las cartas del distribuidor son");
        System.out.println("    " + manoDistribuidor.getCarta(0));
        System.out.println("    " + manoDistribuidor.getCarta(1));
        while (manoDistribuidor.getBlackjackValor() <= 16) {
            juegoBlackjack.Carta nuevaCarta = baraja.cartaOferta();
            System.out.println("El distribuidor pide y obtiene el " + nuevaCarta);
            manoDistribuidor.añadirCarta(nuevaCarta);
            if (manoDistribuidor.getBlackjackValor() > 21) {
                System.out.println();
                System.out.println("El distribuidor quiebra por  sobrepasar los 21.  Tu ganas.");
                return true;
            }
        }
        System.out.println("El distribuidor pose un total de: " + manoDistribuidor.getBlackjackValor());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
          puedemos determinar el ganador comparando los valores de sus manos. */
        System.out.println();
        if (manoDistribuidor.getBlackjackValor() == manoUsuario.getBlackjackValor()) {
            System.out.println("El distribuidor gana en un empate. Tú pierdes.");
            return false;
        } else if (manoDistribuidor.getBlackjackValor() > manoUsuario.getBlackjackValor()) {
            System.out.println("El distribuidor gana: " + manoDistribuidor.getBlackjackValor()
                    + " puntos " + manoUsuario.getBlackjackValor() + ".");
            return false;
        } else {
            System.out.println("Tu ganas; " + manoUsuario.getBlackjackValor()
                    + " puntos " + manoDistribuidor.getBlackjackValor() + ".");
            return true;
        }

    }

}
