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
public class Carta {

   public final static int ESPADAS = 0;   // Códigos para los 4 palos, más Joker.
    public final static int CORAZONES = 1;
    public final static int DIAMANTES = 2;
    public final static int TREBOLES = 3;
    public final static int JOKER = 4;

    public final static int AS = 1;      // Códigos para las tarjetas no numéricas.
    public final static int JOTA = 11;    //   Las tarjetas 2 a 10 tienen su 
    public final static int REINA = 12;   //   valores numéricos para sus códigos.
    public final static int REY = 13;

    /**
     * Traje de esta carta, una de las constantes SPADES, HEARTS, DIAMONDS, CLUBS,
     * o JOKER. La demanda no se puede cambiar después de que se construya la tarjeta.
     */
    private final int palo;

    /**
     * El valor de la tarjeta. Para una carta normal, este es uno de los valores 1 a través de
     * 13, con 1 que representa ACE. Para un JOKER, el valor puede ser cualquier cosa. los
     * el valor no se puede cambiar después de que se construye la tarjeta.
     */
    private final int valor;

    /**
     * Crea un Joker, con 1 como el valor asociado. (Tenga en cuenta que "nueva tarjeta ()"
      * es equivalente a "nueva Tarjeta (1, Tarjeta.CAJA)".)
     */
    public Carta() {
        palo = JOKER;
        valor = 1;
    }

    /**
     * Crea una carta con un palo y valor especificados.
      *
      * @param theValue el valor de la nueva tarjeta. Para una tarjeta regular
      * (no comodín), el valor debe estar en el rango de 1 a 13, con 1
      * representando un As. Puedes usar las constantes Card.ACE, Card.JACK,
      * Card.QUEEN y Card.KING. Para un Joker, el valor puede ser cualquier cosa.
      * @param theSuit el traje de la nueva tarjeta. Este debe ser uno de los valores
      * Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS o Card.JOKER.
      * @throws IllegalArgumentException si los valores de los parámetros no están en el
      * rangos permisibles
     */
    public Carta(int elValor, int elPalo) {
        if (elPalo != ESPADAS && elPalo != CORAZONES && elPalo != DIAMANTES
                && elPalo != TREBOLES && elPalo != JOKER) {
            throw new IllegalArgumentException("Cojunto de naipes ilegal");
        }
        if (elPalo != JOKER && (elValor < 1 || elValor > 13)) {
            throw new IllegalArgumentException("Illegal playing card value");
        }
        valor = elValor;
        palo = elPalo;
    }

    /**
     * Devuelve la demanda de esta tarjeta.
      *
      * @ devuelve el traje, que es una de las constantes Card.SPADES,
      * Card.HEARTS, Card.DIAMONDS, Card.CLUBS o Card.JOKER
     */
    public int getPalo() {
        return palo;
    }

    /**
     * Devuelve el valor de esta tarjeta.
      *
      * @return the value, que es uno de los números del 1 al 13, inclusive
      * para una tarjeta regular, y que puede ser de algún valor para un Joker.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Devuelve una representación de Cadena del palo de la carta.
      *
      * @return una de las cadenas "Picas", "Corazones", "Diamantes", "Clubes" o
      * "Joker".
     */
    public String getSuitAsString() {
        switch (palo) {
            case ESPADAS:
                return "Espadas";
            case CORAZONES:
                return "Corazones";
            case DIAMANTES:
                return "Diamantes";
            case TREBOLES:
                return "Treboles";
            default:
                return "Joker";
        }
    }

    /**
     * Devuelve una representación de cadena del valor de la tarjeta.
      *
      * @return para una tarjeta regular, una de las cadenas "Ace", "2", "3", ...,
      * "10", "Jack", "Queen" o "King". Para un Joker, la cuerda es siempre
      * numérico.
     */
    public String getValueAsString() {
        if (palo == JOKER) {
            return "" + valor;
        } else {
            switch (valor) {
                case 1:
                    return "As";
                case 2:
                    return "2";
                case 3:
                    return "3";
                case 4:
                    return "4";
                case 5:
                    return "5";
                case 6:
                    return "6";
                case 7:
                    return "7";
                case 8:
                    return "8";
                case 9:
                    return "9";
                case 10:
                    return "10";
                case 11:
                    return "Jota";
                case 12:
                    return "Reina";
                default:
                    return "Rey";
            }
        }
    }

    /**
     * Devuelve una representación de cadena de esta tarjeta, que incluye tanto su traje como
      * su valor (excepto que para un Joker con valor 1, el valor de retorno es solo
      * "Joker"). Los valores de muestra de retorno son: "Reina de Corazones", "10 de Diamantes",
      * "Ace of Spades", "Joker", "Joker # 2"
     */
    public String toString() {
        if (palo == JOKER) {
            if (valor == 1) {
                return "Joker";
            } else {
                return "Joker #" + valor;
            }
        } else {
            return getValueAsString() + " de " + getSuitAsString();
        }
    }
}
