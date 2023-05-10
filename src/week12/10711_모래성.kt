import kotlin.collections.ArrayDeque

var H = 0
var W = 0

val dx = arrayOf( -1, -1, -1, 0, 1, 1, 1, 0 )
val dy = arrayOf( -1, 0, 1, 1, 1, 0, -1, -1 )

fun isWall(x: Int, y: Int): Boolean {
    if (x < 0 || x >= H || y < 0 || y >= W) return true
    return false
}

fun main() = with(System.`in`.bufferedReader()) {
    readLine().split(" ").map{ it.toInt() }.apply {
        H = this[0]
        W = this[1]
    }
    val arr = Array(H) { IntArray(W) }

    for (i in 0 until H) {
        val inputLine = readLine()
        for (j in 0 until W) {
            val inputChar = inputLine[j]

            if (inputChar == '.') arr[i][j] = -1
            else arr[i][j] = inputChar - '0'
        }
    }

    val queue = ArrayDeque<Pair<Int, Int>>()
    for (i in 0 until H) {
        for (j in 0 until W) {
            if (arr[i][j] == -1) continue

            for (k in 0 until 8) {
                val nx = i + dx[k]
                val ny = j + dy[k]
                if (isWall(nx, ny)) continue

                if (arr[nx][ny] == -1) arr[i][j]--
            }

            if (arr[i][j] <= 0) {
                arr[i][j] = 0
                queue.add(Pair(i, j))
            }
        }
    }

    var cnt = 0
    if (!queue.isEmpty()) queue.add(Pair(-1, cnt))

    while (!queue.isEmpty()) {
        val (x, y) = queue.removeFirst()
        if (x == -1) {
            cnt++
            if (queue.isEmpty()) break

            queue.add(Pair(-1, cnt))
            continue
        }

        arr[x][y] = -1
        for (i in 0 until 8) {
            val nx = x + dx[i]
            val ny = y + dy[i]
            if (isWall(nx, ny) || arr[nx][ny] <= 0) continue

            arr[nx][ny]--
            if (arr[nx][ny] == 0) queue.add(Pair(nx, ny))
        }
    }

    print(cnt)
}
