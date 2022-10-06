fun tipoFrame(frame: Pair<Int, Int>): String{
    return when{
        frame.first == 10 -> "Strike"
        frame.first+frame.second == 10 -> "Spare"
        else -> "Open"
    }
}

fun separarFrames(tiradas: List<Int>): List<Pair<Int, Int>>{
    val frames = mutableListOf<Pair<Int, Int>>()
    var pos = 0
    while(pos<tiradas.size){
        frames.add(when{
            tiradas[pos] == 10 -> tiradas[pos] to -1
            pos==tiradas.size-1 && tipoFrame(frames.last()) != "Open" -> tiradas[pos] to -1
            else -> tiradas[pos] to tiradas[pos+1]
        })
        pos += if(tipoFrame(frames.last()) == "Strike") 1 else 2
    }
    if(frames.size == 10) frames.add(-1 to -1)
    if(frames.size == 11) frames.add(-1 to -1)
    return frames
}

fun obtenerPuntajeFrame(frame: Pair<Int, Int>, bonus1: Int, bonus2: Int): Int{
    return when(tipoFrame(frame)){
        "Strike" -> 10 + bonus1 + bonus2
        "Spare" -> 10 + bonus1
        else -> frame.first + frame.second
    }
}

fun obtenerPuntajeTotal(frames: List<Pair<Int, Int>>, pos: Int): Int{
    if(pos == 10) return 0
    val bonus1 = frames[pos+1].first
    val bonus2 = if(tipoFrame(frames[pos+1]) == "Strike") frames[pos+2].first else frames[pos+1].second
    return obtenerPuntajeFrame(frames[pos], bonus1, bonus2) + obtenerPuntajeTotal(frames, pos+1)
}

fun tiradasValidas(tiradas: List<Int>): Boolean{
    try {
        val frames = separarFrames(tiradas)
        var pos = 0
        for(i in 0..9){
            if(frames[i].first != -1) pos++
            if(frames[i].second != -1) pos++
        }
        val bonus = when(tipoFrame(frames[9])){
            "Strike" -> 2
            "Spare" -> 1
            else -> 0
        }
        return pos+bonus == tiradas.size
    } catch (e: Exception){
        return false
    }
}

fun KataBowling(tiradas: List<Int>): Int{
    return if(tiradasValidas(tiradas)) obtenerPuntajeTotal(separarFrames(tiradas), 0) else -1
}

fun imprimirPuntos(puntos: Int, juego: Int){
    println("--------------- JUEGO $juego ---------------")
    if(puntos == -1) println("Las tiradas ingresadas no son validas")
    else println("Puntaje total = $puntos")
}

fun main(args: Array<String>) {
    val juego1 = listOf(10, 1, 5, 7, 1, 10, 10, 5, 4, 10)
    imprimirPuntos(KataBowling(juego1), 1)

    val juego2 = listOf(6, 4, 10, 1, 0, 0, 7, 8, 1, 10, 9, 1, 3, 2, 5, 1, 10, 5, 2, 10)
    imprimirPuntos(KataBowling(juego2), 2)

    val juego3 = listOf(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6)
    imprimirPuntos(KataBowling(juego3), 3)

    val juego4 = listOf(5, 5, 4, 5, 8, 2, 10, 0, 10, 10, 6, 2, 10, 4, 6, 10, 10, 10)
    imprimirPuntos(KataBowling(juego4), 4)

    val juego5 = listOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10)
    imprimirPuntos(KataBowling(juego5), 5)

    val juego6 = listOf(1, 2, 0, 8, 7, 1, 9, 0, 3, 6, 7, 1, 5, 2, 3, 6, 1, 1, 3, 4)
    imprimirPuntos(KataBowling(juego6), 6)

    val juego7 = listOf(7, 2, 9, 1, 10, 6, 3, 10, 8, 2, 7, 3, 9, 0, 8, 1, 7, 3, 9)
    imprimirPuntos(KataBowling(juego7), 7)

    val juego8 = listOf(7, 3, 8, 2, 10, 10, 10, 8, 2, 7, 3, 9, 0, 10, 10, 10, 9)
    imprimirPuntos(KataBowling(juego8), 8)
}