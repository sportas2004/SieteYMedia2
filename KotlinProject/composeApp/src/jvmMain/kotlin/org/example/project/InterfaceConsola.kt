package org.example.project

class InterfaceConsola(private val logica: SieteYMedia) {
    fun presentarJuego(): String {
       return "- El usuario es el jugador y el ordenador la banca.\n"+
       "- No hay en la baraja 8s y 9s. El 10 es la sota, el 11 el caballo y el 12 el Rey.\n"+
        "- Las figuras valen medio punto y el resto su valor.\n"+
        "- Primero juega el jugador.\n"+
        "- El jugador puede pedir cartas o plantarse.\n"+
       "- Si supera 7.5 pierde.\n"+
        "- Luego juega la banca, que debe empatar o superar al jugador sin pasarse.\n"
    }
}
