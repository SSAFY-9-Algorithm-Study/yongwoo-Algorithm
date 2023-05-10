fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val inputArr = Array(n + 1) { IntArray(m + 1) }
    val array = Array(n + 1) { IntArray(m + 1) }

    var max = 0
    for (i in 1 .. n) {
        val input = readLine()
        for (j in 1 .. m) {
            array[i][j] = input[j - 1] - '0'
            if (array[i][j] == 1) {
                array[i][j] = array[i - 1][j].coerceAtMost(array[i][j - 1]).coerceAtMost(array[i - 1][j - 1]) + 1
            }

            if (max < array[i][j]) max = array[i][j]
        }
    }

    print(max * max)
}
