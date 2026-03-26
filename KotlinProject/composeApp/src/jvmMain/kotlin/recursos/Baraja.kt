package recursos

import kotlin.random.Random

class Baraja {
    val mazo = mutableListOf<Carta>()

    init {
        for (p in Palo.values()) {
            for (n in 1..12) {
                if (n != 8 && n != 9) {
                    mazo.add(Carta(p, n))
                }
            }
        }
    }


    fun barajar() =  mazo.shuffle()
    fun darCartas(numCartas: Int): List<Carta> {
        if (numCartas > mazo.size) return emptyList()

        val cartas = mazo.take(numCartas)
        repeat(numCartas) { mazo.removeAt(0) }

        return cartas
    }
}
