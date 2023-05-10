fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    val list = Array<ArrayList<Pair<Int, Int>>>(2_001) { arrayListOf() }
    for (i in 0 until N) {
        val (C, S) = readLine().split(" ").map { it.toInt() }
        list[S].add(Pair(i, C))
    }

    var sum = 0
    val colorSumArr = IntArray(200_001)

    val printArr = IntArray(N)
    for (i in list.indices) {
        if (list[i].size == 0) continue

        list[i].forEach { pair ->
            val (idx, color) = pair
            printArr[idx] = sum - colorSumArr[color]
        }

        sum += list[i].size * i
        list[i].forEach { pair ->
            val (idx, color) = pair
            colorSumArr[color] += i
        }
    }

    val sb = StringBuilder()
    printArr.forEach {
        sb.append(it).append("\n")
    }

    print(sb)
}
