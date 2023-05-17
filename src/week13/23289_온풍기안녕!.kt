import java.util.*
import kotlin.math.abs

var R = 0
var C = 0
var K = 0

val dx = arrayOf(0, 0, 0, -1, 1)
val dy = arrayOf(0, 1, -1, 0, 0)
val dw = arrayOf(0, 2, 3, 5, 7)

fun isWall(x: Int, y: Int): Boolean {
    if (x < 0 || x >= R || y < 0 || y >= C) return true
    return false
}

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    readLine().split(" ").apply {
        R = this[0].toInt()
        C = this[1].toInt()
        K = this[2].toInt()
    }
    val checkPosList = arrayListOf<Pair<Int, Int>>()
    val arr = Array(R) { IntArray(C) }
    repeat(R) { i ->
        val input = readLine().split(" ")
        repeat(C) { j ->
            arr[i][j] = input[j].toInt()
            if (arr[i][j] == 5) checkPosList.add(Pair(i, j))
        }
    }
    val W = readLine().toInt()
    val wallArr = Array(R) { IntArray(C) { 1 } } // dw 순으로 곱한 값을 저장
    repeat(W) {
        val (x, y, t) = readLine().split(" ").map { it.toInt() }
        if (t == 0) {
            wallArr[x - 1][y - 1] *= dw[3]
            wallArr[x - 2][y - 1] *= dw[4]
        }
        else {
            wallArr[x - 1][y - 1] *= dw[1]
            wallArr[x - 1][y] *= dw[2]
        }
    }

    // 바람이 나오면 생성되는 온도 배열 만들기
    val airGenerateArr = Array(R) { IntArray(C) }
    for (i in 0 until R) {
        for (j in 0 until C) {
            if (arr[i][j] !in 1..4 || wallArr[i][j] % dw[arr[i][j]] == 0 || isWall(i + dx[arr[i][j]], j + dy[arr[i][j]])) continue

            val airTmpArr = Array(R) { IntArray(C) }
            val dir = arr[i][j]

            val queue = ArrayDeque<Pair<Int, Int>>()
            if (!isWall(i + dx[dir] * 2, j + dy[dir] * 2)) queue.add(Pair(i + dx[dir], j + dy[dir]))
            airTmpArr[i + dx[dir]][j + dy[dir]] = 5

            val sideMove = if (dir < 3) 3..4 else 1..2
            while (!queue.isEmpty()) {
                val (x, y) = queue.poll()
                for (k in sideMove) {
                    if (wallArr[x][y] % dw[k] == 0) continue // 현재 위치 옆 벽 확인
                    
                    var nx = x + dx[k]
                    var ny = y + dy[k]
                    if (isWall(nx, ny) || wallArr[nx][ny] % dw[dir] == 0) continue // 옆 위치 정면 방향 벽 확인

                    nx += dx[dir]
                    ny += dy[dir]
                    if (airTmpArr[nx][ny] > 0) continue // 이미 값이 들어갔으면 continue
                    airTmpArr[nx][ny] = airTmpArr[x][y] - 1
                    if (airTmpArr[nx][ny] > 1 && !isWall(nx + dx[dir], ny + dy[dir])) queue.add(Pair(nx, ny))
                }
                
                // 정면 이동
                if (wallArr[x][y] % dw[dir] == 0) continue // 정면 벽 확인
                val nx = x + dx[dir]
                val ny = y + dy[dir]
                if (airTmpArr[nx][ny] > 0) continue
                airTmpArr[nx][ny] = airTmpArr[x][y] - 1
                if (airTmpArr[nx][ny] > 1 && !isWall(nx + dx[dir], ny + dy[dir])) queue.add(Pair(nx, ny))
            }

            repeat(R) { r ->
                repeat(C) { c ->
                    airGenerateArr[r][c] += airTmpArr[r][c]
                }
            }
        }
    }

    var chocolate = 0
    val tempArr = Array(R) { IntArray(C) }
    val downAndRight = arrayOf(1, 4)
    while (chocolate <= 100) {
        // 집에 있는 모든 온풍기에서 바람이 한 번 나옴
        repeat(R) { i ->
            repeat(C) { j ->
                tempArr[i][j] += airGenerateArr[i][j]
            }
        }

        // 온도가 조절됨(닿은곳 온도 조절)
        val copyArr = Array(R) { IntArray(C) }
        for (i in 0 until R) {
            copyArr[i] = tempArr[i].clone()
        }
        for (i in 0 until R) {
            for (j in 0 until C) {
                for (k in downAndRight) {
                    if (wallArr[i][j] % dw[k] == 0) continue
                    val nx = i + dx[k]
                    val ny = j + dy[k]
                    if (isWall(nx, ny)) continue

                    val sub = abs(copyArr[i][j] - copyArr[nx][ny]) / 4
                    if (sub == 0) continue
                    if (copyArr[i][j] > copyArr[nx][ny]) {
                        tempArr[i][j] -= sub
                        tempArr[nx][ny] += sub
                    }
                    else {
                        tempArr[i][j] += sub
                        tempArr[nx][ny] -= sub
                    }
                }
            }
        }

        // 온도가 1 이상인 가장 바깥쪽 칸의 온도가 1씩 감소
        for (i in 0 until C) {
            // 가로 이동
            if (tempArr[0][i] > 0) tempArr[0][i]--
            if (tempArr[R - 1][i] > 0) tempArr[R - 1][i]--
        }
        for (i in 1 until R - 1) {
            // 세로 이동
            if (tempArr[i][0] > 0) tempArr[i][0]--
            if (tempArr[i][C - 1] > 0) tempArr[i][C - 1]--
        }

        // 초콜릿을 하나 먹는다.
        chocolate++

        // 조사하는 모든 칸의 온도가 K 이상이 되었는지 검사. 모든 칸의 온도가 K이상이면 테스트를 중단하고, 아니면 1부터 다시 시작한다.
        var i = 0
        while (i < checkPosList.size) {
            val x = checkPosList[i].first
            val y = checkPosList[i].second
            if (tempArr[x][y] < K) break
            i++
        }
        if (i == checkPosList.size) break
    }

    print(chocolate)
}
