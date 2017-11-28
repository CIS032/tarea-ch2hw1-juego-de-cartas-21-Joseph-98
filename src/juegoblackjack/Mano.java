/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoblackjack;

import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class Mano {

   private ArrayList<Carta> mano;   //Las cartas en la mano.

    /**
     *Crea una mano que esté inicialmente vacía.
     */
    public Mano() {
        mano = new ArrayList<Carta>();
    }

    /**
     * Retire todas las cartas de la mano, dejándolas vacías.
     */
    public void clear() {
        mano.clear();
    }

    /**
    * Agrega una carta a la mano. Se agrega al final de la mano actual.
      * @param c la tarjeta no nula que se agregará.
      * @throws NullPointerException si el parámetro c es nulo.
     */
    public void añadirCarta(Carta c) {
        if (c == null)
            throw new NullPointerException("No se puede agregar una carta nula a una mano.");
        mano.add(c);
    }

    /**
     * Retire una tarjeta de la mano, si está presente.
      * @param c la tarjeta que se eliminará. Si c es nulo o si la tarjeta no está en
      * la mano, entonces no se hace nada.
     */
    public void eliminarCarta(Carta c) {
        mano.remove(c);
    }

    /**
     * Retire la tarjeta en una posición específica de la mano.
      * @param posicionar la posición de la tarjeta que se va a eliminar, donde
      * las posiciones comienzan desde cero.
      * @throws IllegalArgumentException si el puesto no existe en
      * la mano, eso es si la posición es menor que 0 o mayor que
      * o igual a la cantidad de cartas en la mano.
     */
    public void eliminarCarta(int posicion) {
        if (posicion < 0 || posicion >= mano.size())
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + posicion);
        mano.remove(posicion);
    }

    /**
     * Devuelve la cantidad de cartas en la mano.
     */
    public int getCountCarta() {
        return mano.size();
    }

    /**
     * Obtiene la carta en una posición específica en la mano. (Tenga en cuenta que esta tarjeta
      * no se elimina de la mano!)
      * @param posicionar la posición de la tarjeta que se va a devolver
      * @throws IllegalArgumentException si la posición no existe en la mano
     */
    public Carta getCarta(int posicion) {
        if (posicion < 0 || posicion >= mano.size())
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + posicion);
        return mano.get(posicion);
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo palo sean
      * agrupados, y dentro de un palo, las tarjetas se ordenan por valor.
      * Tenga en cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void ordenarPorPalo() {
        ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  // Posición de la tarjeta mínima.
            Carta c = mano.get(0);  // carta minima.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getPalo()< c.getPalo()||
                        (c1.getPalo()== c.getPalo()&& c1.getValor()< c.getValor()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            nuevaMano.add(c);
        }
        mano = nuevaMano;
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo valor sean
      * agrupados. Las cartas con el mismo valor se clasifican por palo.
      * Tenga en cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void ordenarPorValor() {
        ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  // Posicion de la carta minima.
            Carta c = mano.get(0);  // Carta minima.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getValor()< c.getValor()||
                        (c1.getValor()== c.getValor()&& c1.getPalo()< c.getPalo()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            nuevaMano.add(c);
        }
        mano = nuevaMano;
    }   
}

    
