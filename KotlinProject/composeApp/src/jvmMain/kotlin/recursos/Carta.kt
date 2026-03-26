package recursos

class Carta(val palo: Palo, val numero: Int) {
    override fun toString(): String {
        return "(" + palo + ", " + numero + ')'
    }
}