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
public class Baraja {

    /**
     * Una array de 52 o 54 cartas. Un mazo de 54 cartas contiene dos Jokers,
     *      * además de las 52 cartas de un mazo de póker regular.
     */
    private Carta[] baraja;

    /**
     * Realiza un seguimiento de la cantidad de cartas que se han repartido
     * desde      * el mazo hasta ahora.
     */
    private int cartasUsadas;

    /**
     * Construye una baraja de póker regular de 52 cartas. Inicialmente, las
     * cartas       * están en un orden ordenado. El método shuffle () se puede
     * llamar a       * aleatorizar el orden. (Tenga en cuenta que "nuevo Deck
     * ()" es equivalente       * a "cubierta nueva (falsa)".)
     */
    public Baraja() {
        this(false);  // Simplemente llama al otro constructor en esta clase.
    }

    /**
     * @param includeJokers si es verdadero, dos comodines están incluidos en el
     * mazo; si es falso,       * no hay Jokers en el mazo.
     */
    public Baraja(boolean incluidoJokers) {
        if (incluidoJokers) {
            baraja = new Carta[54];
        } else {
            baraja = new Carta[52];
        }
        int cartaCt = 0; // Cuántas cartas se han creado hasta ahora.
        for (int palo = 0; palo <= 3; palo++) {
            for (int valor = 1; valor <= 13; valor++) {
                baraja[cartaCt] = new Carta(valor, palo);
                cartaCt++;
            }
        }
        if (incluidoJokers) {
            baraja[52] = new Carta(1, Carta.JOKER);
            baraja[53] = new Carta(2, Carta.JOKER);
        }
        cartasUsadas = 0;
    }

    /**
     * Coloque todas las tarjetas usadas nuevamente en el mazo (si las hay), y
     *       * baraja el mazo en un orden aleatorio.
     */
    public void barajar() {
        for (int i = baraja.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * (i + 1));
            Carta temp = baraja[i];
            baraja[i] = baraja[rand];
            baraja[rand] = temp;
        }
        cartasUsadas = 0;
    }

    /**
     * A medida que las cartas se reparten desde el mazo, la cantidad de cartas
     * dejadas       * disminuye. Esta función devuelve la cantidad de cartas
     * que       * aún quedan en el mazo. El valor de retorno sería       * 52 o
     * 54 (dependiendo de si el mazo incluye comodines)       * cuando se crea
     * la plataforma por primera vez o después de que la plataforma ha sido
     *       * barajado. Disminuye en 1 cada vez que el método dealCard ()      
     * * se llama.
     */
    public int cartasIzquierda() {
        return baraja.length - cartasUsadas;
    }

    /**
     * Quita la siguiente carta del mazo y la devuelve. Es ilegal       * para
     * llamar a este método si no hay más cartas en el mazo. Usted puede       *
     * verifique el número de tarjetas restantes llamando a la función cardsLeft
     * ().       * @return la carta que se retira de la baraja.       * @throws
     * IllegalStateException si no quedan cartas en el mazo
     */
    public Carta cartaOferta() {
        if (cartasUsadas == baraja.length) {
            throw new IllegalStateException("No quedan cartas en la baraja.");
        }
        cartasUsadas++;
        return baraja[cartasUsadas - 1];
        /* Nota de programación: las tarjetas no se eliminan literalmente de la matriz
         // eso representa el mazo. Simplemente hacemos un seguimiento de cuántas tarjetas
         // se han usado.Nota de programación: las tarjetas no se eliminan literalmente de la matriz*/

    }

    /**
     * Prueba si el mazo contiene comodines.       * @return true, si este es un
     * mazo de 54 cartas que contiene dos comodines, o falso si       * esta es
     * una baraja de 52 cartas que no contiene comodines.       * /
      *
     */
    public boolean tieneJoker() {
        return (baraja.length == 54);
    }
}
