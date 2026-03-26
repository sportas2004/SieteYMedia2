package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import recursos.Carta

private val fondoMesa = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF05110D),
        Color(0xFF123328),
        Color(0xFF030A08),
    )
)

private val colorTitulo = Color(0xFFFFE08A)
private val colorTexto = Color(0xFFFFFBF0)
private val colorTextoSecundario = Color(0xFFE8F3EC)
private val colorBotonPrincipal = Color(0xFFE63946)
private val colorBotonSecundario = Color(0xFF264653)
private val colorBotonAccion = Color(0xFF7B2CBF)
private val colorBotonSalir = Color(0xFF2A9D8F)

@Composable
fun App() {
    var moverse by remember { mutableStateOf("inicio") }

    when (moverse) {
        "inicio" -> inicio { moverse = "juego" }
        "juego" -> juego({ moverse = "inicio" }, { moverse = "intermedio" })
        "intermedio" -> moverse = "juego"
    }
}

@Composable
fun inicio(comenzar: () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoMesa)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Siete y Media",
                    color = colorTitulo,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Pulsa para empezar la mano",
                    color = colorTextoSecundario,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = comenzar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBotonPrincipal,
                        contentColor = Color.White,
                    ),
                ) {
                    Text("Comenzar")
                }
            }
        }
    }
}

@Composable
fun juego(inicio: () -> Unit, seguirJugando: () -> Unit) {
    val logicaJuego by remember { mutableStateOf(SieteYMedia()) }
    val mensajesJuego by remember { mutableStateOf(InterfaceConsola(logicaJuego)) }
    val continuar = remember { mutableStateListOf(false, false) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoMesa)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!continuar[0]) {
                    Text(
                        text = mensajesJuego.presentarJuego(),
                        color = colorTexto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { continuar[0] = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorBotonAccion,
                            contentColor = Color.White,
                        ),
                    ) {
                        Text("Continuar")
                    }
                }
                if (continuar[0]) {
                    turnoJugador(logicaJuego)
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = seguirJugando,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBotonSecundario,
                        contentColor = Color.White,
                    ),
                ) {
                    Text("Repetir de nuevo")
                }

                Button(
                    onClick = inicio,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBotonSalir,
                        contentColor = Color.White,
                    ),
                ) {
                    Text("Atras")
                }
            }
        }
    }
}

@Composable
fun turnoJugador(objAlgoritmo: SieteYMedia) {
    val cartasPosesion = remember { mutableStateListOf<Carta>() }
    val pedirCarta = remember { mutableStateListOf(false, false, false, false, false) }
    var cartaEntregada by remember { mutableStateOf<Carta?>(null) }
    var valorGuardado by remember { mutableStateOf(0.0) }
    var planto by remember { mutableStateOf(false) }

    if (valorGuardado < 7.5 && !planto) {
        if (!pedirCarta[4]) {
            Text(
                text = "Como minimo recibes una carta",
                color = colorTexto,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        if (!pedirCarta[0]) {
            cartaEntregada = objAlgoritmo.getbaraja().darCartas(1).first()
        }
        if (!pedirCarta[2]) {
            Button(
                onClick = {
                    pedirCarta[1] = true
                    pedirCarta[0] = true
                    pedirCarta[2] = true
                    pedirCarta[4] = true
                    cartaEntregada?.let { cartasPosesion.add(it) }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBotonPrincipal,
                    contentColor = Color.White,
                ),
            ) {
                Text("Pedir carta")
            }
        }

        if (pedirCarta[2] && !pedirCarta[3]) {
            Text("Estas son tus cartas jugador:", color = colorTitulo, fontWeight = FontWeight.Bold)
            Text(cartasPosesion.joinToString(" "), color = colorTexto, fontSize = 18.sp)
            Spacer(modifier = Modifier.padding(5.dp))
            val valor = objAlgoritmo.valorCartas(cartasPosesion)
            valorGuardado = valor
            Text("Valor de cartas: $valor", color = colorTextoSecundario, fontSize = 18.sp)
            Button(
                onClick = { pedirCarta[3] = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBotonAccion,
                    contentColor = Color.White,
                ),
            ) {
                Text("Continuar")
            }
        }

        if (pedirCarta[3]) {
            Text("Pides carta o te plantas?", color = colorTexto, fontWeight = FontWeight.SemiBold)
            Button(
                onClick = {
                    pedirCarta[0] = false
                    pedirCarta[1] = false
                    pedirCarta[3] = false
                    cartaEntregada?.let { cartasPosesion.add(it) }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBotonPrincipal,
                    contentColor = Color.White,
                ),
            ) {
                Text("Pido carta")
            }

            Button(
                onClick = {
                    pedirCarta[0] = false
                    pedirCarta[1] = false
                    pedirCarta[3] = false
                    planto = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBotonSecundario,
                    contentColor = Color.White,
                ),
            ) {
                Text("Me planto")
            }
        }
    } else {
        turnoBanca(objAlgoritmo, valorGuardado)
        return
    }
}

@Composable
fun turnoBanca(objAlgoritmo: SieteYMedia, valorJugador: Double) {
    val cartasBanca = remember { mutableStateListOf<Carta>() }
    var valorBanca by remember { mutableStateOf(0.0) }
    val estadoBanca = remember { mutableStateListOf(false, false) }
    if (valorJugador > 7.5) {
        Text("Jugador, te has pasado, gana la banca", color = colorTexto, fontSize = 20.sp)
        return
    }

    if (!estadoBanca[0]) {
        Text("Turno de banca ...", color = colorTitulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }

    while (objAlgoritmo.valorCartas(cartasBanca) < valorJugador) {
        val carta = objAlgoritmo.getbaraja().darCartas(1).first()
        cartasBanca.add(carta)
        estadoBanca[0] = true
    }
    if (!estadoBanca[1]) {
        Text("Estas son mis cartas:", color = colorTexto, fontWeight = FontWeight.Bold)
        Text(cartasBanca.joinToString(" "), color = colorTextoSecundario, fontSize = 18.sp)
        val valor = objAlgoritmo.valorCartas(cartasBanca)
        valorBanca = valor
        Button(
            onClick = { estadoBanca[1] = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorBotonAccion,
                contentColor = Color.White,
            ),
        ) {
            Text("Siguiente")
        }
    }

    if (estadoBanca[1]) {
        if (valorBanca > 7.5) {
            Text("Valor de cartas rival: $valorBanca", color = colorTextoSecundario, fontSize = 18.sp)
            Text("Me pase, ganas tu jugador", color = colorTexto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            return
        } else {
            Text("Valor de cartas rival: $valorBanca", color = colorTextoSecundario, fontSize = 18.sp)
            Text("Gana la banca", color = colorTexto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            return
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Siete y Media",
    ) {
        App()
    }
}
