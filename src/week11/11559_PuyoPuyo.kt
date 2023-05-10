import java.util.*

const val ROW = 12
const val COL = 6

val dx = arrayOf(0, 1, 0, -1)
val dy = arrayOf(1, 0, -1, 0)

val arr = Array(ROW) { CharArray (COL) }
val visited = Array(ROW) { BooleanArray (COL) }
var isDeleted = false // 제거된 뿌요가 있는지

fun isWall(x: Int, y: Int): Boolean {
    if (x < 0 || x >= ROW || y < 0 || y >= COL) return true
    return false
}

fun bfs(x: Int, y: Int): ArrayList<Node> {
    val list = arrayListOf<Node>()
    val queue = ArrayDeque<Node>()
    queue.add(Node(x, y))
    visited[x][y] = true

    while (!queue.isEmpty()) {
        val node = queue.poll()
        list.add(node)

        for (i in 0 until 4) {
            val nx = node.x + dx[i]
            val ny = node.y + dy[i]

            if (isWall(nx, ny) || visited[nx][ny]) continue
            if (arr[node.x][node.y] != arr[nx][ny]) continue

            queue.add(Node(nx, ny))
            visited[nx][ny] = true
        }
    }

    return list
}

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    for (i in 0 until ROW) {
        val input = readLine()
        for (j in input.toCharArray().indices) {
            arr[i][j] = input[j]
        }
    }

    var combo = 0 // 연쇄
    while (true) {
        isDeleted = false

        // 닿아있는 뿌요 제거
        for (i in 0 until ROW) {
            for (j in 0 until COL) {
                if (arr[i][j] == ' ' || arr[i][j] == '.' || visited[i][j]) continue

                val list = bfs(i, j)
                if (list.size >= 4) {
                    isDeleted = true
                    list.forEach {
                        arr[it.x][it.y] = ' '
                    }
                }
            }
        }

        // 제거된 뿌요 내리기
        for (c in 0 until COL) {
            var i = ROW - 1
            var j = ROW - 1
            loop@ while (i >= 0) {
                if (arr[i][c] == '.') break
                if (arr[i][c] != ' ') {
                    i--
                    continue
                }

                if (j >= i) j = i - 1
                while (j >= 0) {
                    if (arr[j][c] == ' ') {
                        j--
                        continue
                    }
                    if (arr[j][c] == '.') break@loop

                    arr[i][c] = arr[j][c]
                    arr[j][c] = ' '
                    break
                }

                i--
            }

            // 남은 자리 .으로 바꾸기
            while (i >= 0 && arr[i][c] != '.') arr[i--][c] = '.'
        }
        if (!isDeleted) break

        combo++
        for (i in visited.indices) {
            visited[i].fill(false)
        }
    }

    print(combo)
}

data class Node (
    val x: Int,
    val y: Int
)
