package chess

fun main() {
    println("Pawns-Only Chess")
    println("First Player's name:")
    val player1 = readln()
    println("Second Player's name:")
    val player2 = readln()
    val pole = MutableList(8) { MutableList(8) { " " } }
    pole[1] = mutableListOf("B", "B", "B", "B", "B", "B", "B", "B")
    pole[6] = mutableListOf("W", "W", "W", "W", "W", "W", "W", "W")
    var bFirst = MutableList(8) { 0 }
    var wFirst = MutableList(8) { 0 }

    printField(pole)
    var count = 0
    while (true) {
        if (winBlack(pole)) {
            println("Black Wins!\nBye!")
            break
        } else if (winWhite(pole)) {
            println("White Wins!\nBye!")
            break
        } else if (count == 0 && drawWhite(pole)) {
            println("Stalemate!\nBye!")
            break
        } else if (count != 0 && drawBlack(pole)) {
            println("Stalemate!\nBye!")
            break
        }

        if (count == 0) {
            println("$player1's turn:")
        } else println("$player2's turn:")
        val inp = readln()
        if (inp == "exit") {
            println("Bye!")
            break
        } else if (inp.matches("[a-h][1-8][a-h][1-8]".toRegex())) {
            val one = transformer(inp[1])
            val two = transformer(inp[0])
            val three = transformer(inp[3])
            val four = transformer(inp[2])
            if (count == 0) {
                if (pole[one][two] != "W") {
                    println("No white pawn at ${inp[0]}${inp[1]}")
                } else if (one < three) {
                    println("Invalid Input")
                } else if (pole[one][two] == "W" && pole[three][four] == " " &&
                    one == 6 && two == four && one - three == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "W"
                    count = 1
                    bFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "W" && pole[three][four] == " " &&
                    one == 6 && two == four && one - three == 2) {
                    pole[one][two] = " "
                    pole[three][four] = "W"
                    count = 1
                    wFirst[two] = 1
                    printField(pole)
                } else if (pole[one][two] == "W" && pole[three][four] == " " &&
                    one != 6 && two == four && one - three == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "W"
                    count = 1
                    bFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "W" && pole[three][four] == "B" &&
                    (two - four == 1 || four - two == 1) && one - three == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "W"
                    count = 1
                    bFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "W" && pole[three + 1][four] == "B" && three + 1 == 3 &&
                    bFirst[four] == 1 && (two - four == 1 || four - two == 1)) {
                    pole[three + 1][four] = " "
                    pole[one][two] = " "
                    pole[three][four] = "W"
                    count = 1
                    bFirst = MutableList(8) { 0 }
                    printField(pole)
                } else println("Invalid Input")
            } else if (count != 0) {
                if (pole[one][two] != "B") {
                    println("No black pawn at ${inp[0]}${inp[1]}")
                } else if (one > three) {
                    println("Invalid Input")
                } else if (pole[one][two] == "B" && pole[three][four] == " " &&
                    one == 1 && two == four && three - one == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "B"
                    count = 0
                    wFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "B" && pole[three][four] == " " &&
                    one == 1 && two == four && three - one == 2) {
                    pole[one][two] = " "
                    pole[three][four] = "B"
                    count = 0
                    bFirst[two] = 1
                    printField(pole)
                } else if (pole[one][two] == "B" && pole[three][four] == " " &&
                    one != 1 && two == four && three - one == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "B"
                    count = 0
                    wFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "B" && pole[three][four] == "W" &&
                    (two - four == 1 || four - two == 1) && three - one == 1) {
                    pole[one][two] = " "
                    pole[three][four] = "B"
                    count = 0
                    wFirst = MutableList(8) { 0 }
                    printField(pole)
                } else if (pole[one][two] == "B" && pole[three - 1][four] == "W" && three - 1 == 4 &&
                    wFirst[four] == 1 && (two - four == 1 || four - two == 1)) {
                    pole[three - 1][four] = " "
                    pole[one][two] = " "
                    pole[three][four] = "B"
                    count = 0
                    wFirst = MutableList(8) { 0 }
                    printField(pole)
                } else println("Invalid Input")
            }
        }
    }
}

fun printField(list: MutableList<MutableList<String>>) {
    var n = 8
    for (i in 0..7) {
        println("  +---+---+---+---+---+---+---+---+")
        println("$n | ${list[i].joinToString(" | ")} |")
        n--
    }
    println("  +---+---+---+---+---+---+---+---+\n    a   b   c   d   e   f   g   h\n")
}

fun transformer (input: Char): Int {
    val num = arrayOf('8', '7', '6', '5', '4', '3', '2', '1')
    val char = arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    val x = if (input.isDigit()) {
        num.indexOf(input)
    } else char.indexOf(input)
    return  x
}

fun winBlack(w: MutableList<MutableList<String>>): Boolean {
    var x = 0
    var r = 1
    for (i in w){
        for (j in i) {
            if(j.contains("W")) r = 0
        }
    }
    for (k in w[7]){
        if (k.contains("B")) x = 1
    }
    return r == 1 || x == 1
}

fun winWhite(w: MutableList<MutableList<String>>): Boolean {
    var x = 0
    var r = 1
    for (i in w){
        for (j in i) {
            if(j.contains("B")) r = 0
        }
    }
    for (k in w[0]){
        if (k.contains("W")) x = 1
    }
    return r == 1 || x == 1
}

fun drawWhite(list: MutableList<MutableList<String>>): Boolean {
    var riad = -1
    var allW = 0
    var w = 0
    for (i in list) {
        riad++
        var stolb = -1
        for (j in i) {
            stolb++
            if (j.contains("W")) {
                w++
                if (stolb == 0 && list[riad - 1][stolb] == "B" && list[riad - 1][stolb + 1] != "B"){
                    allW++
                } else if (stolb == 0 && list[riad - 1][stolb] == "B" && list[riad - 1][stolb + 1] == "B") {
                    allW++
                } else if (stolb == 7 && list[riad - 1][stolb] == "B" && list[riad - 1][stolb - 1] != "B") {
                    allW++
                } else if (stolb == 7 && list[riad - 1][stolb] == "B" && list[riad - 1][stolb - 1] == "B") {
                    allW++
                } else if (list[riad - 1][stolb] == "B" && list[riad - 1][stolb - 1] != "B" && list[riad - 1][stolb + 1] != "B")
                    allW++
            }
        }
    }
    return allW == w
}

fun drawBlack(list: MutableList<MutableList<String>>): Boolean {
    var riad = -1
    var allB = 0
    var b = 0
    for (i in list) {
        riad++
        var stolb = -1
        for (j in i) {
            stolb++
            if (j.contains("B")) {
                b++
                if (stolb == 0 && list[riad + 1][stolb] == "W" && list[riad + 1][stolb + 1] != "W"){
                    allB++
                } else if (stolb == 0 && list[riad + 1][stolb] == "W" && list[riad + 1][stolb + 1] == "W"){
                    continue
                } else if (stolb == 7 && list[riad + 1][stolb] == "W" && list[riad + 1][stolb - 1] != "W") {
                    allB++
                } else if (stolb == 7 && list[riad + 1][stolb] == "W" && list[riad + 1][stolb - 1] != "W") {
                    allB++
                } else if (list[riad + 1][stolb] == "W" && list[riad + 1][stolb - 1] != "W" && list[riad + 1][stolb + 1] != "W")
                    allB++
            }
        }
    }
    return allB == b
}