package org.example.project

import recursos.Baraja
import recursos.Carta

class SieteYMedia {
    private val baraja = Baraja()
    private val cartasJugador = mutableListOf<Carta>()
    private val cartasBanca = mutableListOf<Carta>()

    init {
        baraja.barajar()
    }

    fun getbaraja()=baraja
    fun getcartasJugador() = cartasJugador

    fun getcartasBanca() = cartasBanca

    fun valorCartas(cartas: List<Carta>): Double =
        cartas.sumOf {
            if (it.numero > 7) 0.5 else it.numero.toDouble()
        }

    fun mostrarCartas(cartas: List<Carta>) {
        cartas.forEach { print("\t$it") }
        println()
    }
}
