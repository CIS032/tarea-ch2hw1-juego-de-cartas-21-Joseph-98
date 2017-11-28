/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoblackjack;

/**
 *
 * @author Andres
 */
public class ManoBlackjack extends Mano {

    /**
     * Calcula y devuelve el valor de esta mano en el juego       * de
     * Blackjack.
     */
    public int getBlackjackValor() {

        int val;      // El valor calculado para la mano.
        boolean as;  // This will be set to true if the
        //   hand contains an ace.
        int cartas;    // Numero de cartas en la mano.

        val = 0;
        as = false;
        cartas = getCountCarta();  // (método definido en la clase Mano)

        for (int i = 0; i < cartas; i++) {
            // Agrega el valor de la i-ésima carta en la mano.
            Carta carta;    // La i-ésima tarjeta;
            int cartaVal;  // El valor de blackjack de la i-ésima carta.
            carta = getCarta(i);
            cartaVal = carta.getValor();  // El valor normal, de 1 a 13.
            if (cartaVal > 10) {
                cartaVal = 10;   // Para un Jack, Queen o King.
            }
            if (cartaVal == 1) {
                as = true;    // Hay al menos un as.
            }
            val = val + cartaVal;
        }

        // Ahora, val es el valor de la mano, contando cualquier as como 1.
        // Si hay un as, y si cambia su valor de 1 a
        // 11 dejaría el puntaje menor o igual a 21,
        // entonces hazlo sumando los 10 puntos adicionales a val. 
        if (as == true && val + 10 <= 21) {
            val = val + 10;
        }

        return val;

    }  // fin de getBlackjackValue()

} // fin de la clase BlackjackHand

