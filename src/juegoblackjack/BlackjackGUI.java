/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoblackjack;

import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class BlackjackGUI {

    public static void main(String[] args) {

        int dinero;          // La cantidad de dinero que tiene el usuario
        int apuesta;            // Cantidad de apuestas de usuario en un juego.
        boolean usuarioGandador;   // ¿El usuario gano el juego?

        JOptionPane.showMessageDialog(null, "Bienvenido al juego de  blackjack.");

        dinero = 100;  // El usuario comienza con $ 100.

        while (true) {
            JOptionPane.showMessageDialog(null, "Tienes " + dinero + " dalares.");
            do {
                apuesta = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cuántos dinero quieres apostar? (Ingresa 0 para finalizar)"));
                //apuesta=Integer.parseInt(JOptionPane.showInputDialog(null," : "));

                if (apuesta < 0 || apuesta > dinero) {
                    JOptionPane.showInputDialog("Tu respuesta de esta entres 0 y" + dinero + '.');
                }
            } while (apuesta < 0 || apuesta > dinero);
            if (apuesta == 0) {
                break;
            }
            usuarioGandador = playBlackjack();
            if (usuarioGandador) {
                dinero = dinero + apuesta;
            } else {
                dinero = dinero - apuesta;
            }

            if (dinero == 0) {
                JOptionPane.showMessageDialog(null, "¡Parece que te has quedado sin dinero!");
                break;
            }
        }

        JOptionPane.showMessageDialog(null, "Te vas con $" + dinero + '.');

    } // fin del main()

    /*
   /**
    * Permita que el usuario juegue un juego de Blackjack, con la computadora como distribuidor.
    * @devuelve verdadero si el usuario gana el juego, falso si el usuario pierde.
     */
    static boolean playBlackjack() {

        Baraja baraja;                  // Una baraja de cartas.  Una nueva bajara para cada juego.
        ManoBlackjack manoDistribuidor;   // La mano del distribuidor.
        ManoBlackjack manoUsuario;     // La mano del usuario .

        baraja = new Baraja();
        manoDistribuidor = new ManoBlackjack();
        manoUsuario = new ManoBlackjack();

        /*  Baraja el mazo, luego reparte dos cartas a cada jugador. */
        baraja.barajar();
        manoDistribuidor.añadirCarta(baraja.cartaOferta());
        manoDistribuidor.añadirCarta(baraja.cartaOferta());
        manoUsuario.añadirCarta(baraja.cartaOferta());
        manoUsuario.añadirCarta(baraja.cartaOferta());

        /* Comprueba si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.
         */
        if (manoDistribuidor.getBlackjackValor() == 21) {
            JOptionPane.showMessageDialog(null, "El distribuidor tiene el " + manoDistribuidor.getCarta(0)
                    + " y el " + manoDistribuidor.getCarta(1) + ".");
            JOptionPane.showMessageDialog(null, "El usuario tiene el " + manoUsuario.getCarta(0)
                    + " y el " + manoUsuario.getCarta(1) + ".");

            JOptionPane.showMessageDialog(null, "El distribuidor tiene Blackjack. El distribuidor gana");
            return false;
        }

        if (manoUsuario.getBlackjackValor() == 21) {
            JOptionPane.showMessageDialog(null, "El distribuidor tiene el " + manoDistribuidor.getCarta(0)
                    + " y el " + manoDistribuidor.getCarta(1) + ".");
            JOptionPane.showMessageDialog(null, "El usuario tiene el " + manoUsuario.getCarta(0)
                    + " y el " + manoUsuario.getCarta(1) + ".");

            JOptionPane.showMessageDialog(null, "Tienes Blackjack. Tú ganas.");
            return true;
        }

        /* Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
           tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
           cuando el usuario elige "Stand". Si el usuario supera los 21,
           el usuario pierde inmediatamente.
         */
        while (true) {

            /*Mostrar tarjetas de usuario y dejar que el usuario decida golpear o ponerse de pie. */
            //  JOptionPane.showMessageDialog(null,"Tus cartas son:");
            for (int i = 0; i < manoUsuario.getCountCarta(); i++) {
                JOptionPane.showMessageDialog(null, "    " + manoUsuario.getCarta(i));
            }
            JOptionPane.showMessageDialog(null, "Tu total es " + manoUsuario.getBlackjackValor());

            JOptionPane.showMessageDialog(null, "El distribuidor muestra el " + manoDistribuidor.getCarta(0));

            JOptionPane.showMessageDialog(null, "Pedir (H) o Plantarse (S)? ");

            String userAction1;  // El usuario responde, 'H' o 'S'.
            while ((userAction1 = JOptionPane.showInputDialog("Ingrese una opción: ")).length() != 1);
            char userAction = userAction1.charAt(0);
            do {
                //userAction = Character.toUpperCase( TextIO.getlnChar() );
                if (userAction != 'H' && userAction != 'S') {
                    JOptionPane.showMessageDialog(null, "Por favor elija H o S:  ");
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
                Carta nuevaCarta = baraja.cartaOferta();
                manoUsuario.añadirCarta(nuevaCarta);

                JOptionPane.showMessageDialog(null, "Aciertos del usuario.");
                JOptionPane.showMessageDialog(null, "Tu carta es: " + nuevaCarta);
                JOptionPane.showMessageDialog(null, "El total que posees ahora es: " + manoUsuario.getBlackjackValor());
                if (manoUsuario.getBlackjackValor() > 21) {

                    JOptionPane.showMessageDialog(null, "Has perdido yendo por encima de 21. Pierdes.");
                    JOptionPane.showMessageDialog(null, "La otra carta del distribuidor era el: " + manoDistribuidor.getCarta(1));
                    return false;
                }
            }

        } // fin del ciclo while

        /* Si llegamos a este punto, el usuario se planta con 21 o menos.  Ahora es
          la oportunidad del distribuidor para sacar  El distribuidor roba cartas hasta que el 
           total sea> 16. Si el distribuidor supera los 21, el este pierde.
         */
        JOptionPane.showMessageDialog(null, "Usuario plantado");
        JOptionPane.showMessageDialog(null, "Las cartas del distribuidor son");
        JOptionPane.showMessageDialog(null, "    " + manoDistribuidor.getCarta(0));
        JOptionPane.showMessageDialog(null, "    " + manoDistribuidor.getCarta(1));
        while (manoDistribuidor.getBlackjackValor() <= 16) {
            Carta nuevaCarta = baraja.cartaOferta();
            JOptionPane.showMessageDialog(null, "El distribuidor pide y obtiene el " + nuevaCarta);
            manoDistribuidor.añadirCarta(nuevaCarta);
            if (manoDistribuidor.getBlackjackValor() > 21) {

                JOptionPane.showMessageDialog(null, "El distribuidor quiebra por  sobrepasar los 21.  Tu ganas.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "El distribuidor pose un total de: " + manoDistribuidor.getBlackjackValor());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
          puedemos determinar el ganador comparando los valores de sus manos. */
        if (manoDistribuidor.getBlackjackValor() == manoUsuario.getBlackjackValor()) {
            JOptionPane.showMessageDialog(null, "El distribuidor gana en un empate. Tú pierdes.");
            return false;
        } else if (manoDistribuidor.getBlackjackValor() > manoUsuario.getBlackjackValor()) {
            JOptionPane.showMessageDialog(null, "El distribuidor gana: " + manoDistribuidor.getBlackjackValor()
                    + " puntos " + manoUsuario.getBlackjackValor() + ".");
            return false;
        } else {
            JOptionPane.showMessageDialog(null, "Tu ganas; " + manoUsuario.getBlackjackValor()
                    + " puntos " + manoDistribuidor.getBlackjackValor() + ".");
            return true;
        }

    }  // fin del juego Blackjack()
}
