var R = 0
var C = 0
lateinit var visited: Array<BooleanArray>

var cnt = 0 // 결과

val dx = arrayOf(-1, 0, 1)

fun dfs(x: Int, y: Int): Boolean {
    visited[x][y] = true
    if (y == C - 1) {
        cnt++
        return true
    }

    val ny = y + 1
    for (i in dx.indices) {
        val nx = x + dx[i]
        if (isWall(nx, ny) || visited[nx][ny]) continue

        val isFinished = dfs(nx, ny)
        if (isFinished) return true
    }

    return false
}

fun isWall(x: Int, y: Int): Boolean {
    if (x < 0 || x >= R || y < 0 || y >= C) return true
    return false
}

fun main() = with(System.`in`.bufferedReader()) {
    readLine().split(" ").apply {
        R = this[0].toInt()
        C = this[1].toInt()
    }
    visited = Array(R) { BooleanArray(C) }

    for (i in 0 until R) {
        val input = readLine()
        for (j in 0 until C) {
            if (input[j] == 'x') visited[i][j] = true
        }
    }

    for (i in 0 until R) {
        if (visited[i][0]) continue
        dfs(i, 0)
    }

    print(cnt)
}
