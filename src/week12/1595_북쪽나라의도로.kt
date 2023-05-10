import java.util.*

val dists = Array(10_001) { ArrayList<Pair<Int, Int>>() } // 노드 간 거리 인접 리스트
val visited = BooleanArray(10_001)

fun getDepth(node: Int): Pair<Int, Int> {
    visited[node] = true

    var max = Pair(0, 0)
    dists[node].forEach {
        val (n, d) = it
        if (!visited[n]) {
            val retPair = getDepth(n)
            val pair = if (retPair.first == 0) it else Pair(retPair.first, retPair.second + d)
            max = if (max.second < pair.second) pair else max
        }
    }

    return max
}

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    while (true) {
        val input = readLine()
        if (input.isNullOrBlank()) break

        val (a, b, dist) = input.split(" ").map { it.toInt() }
        dists[a].add(Pair(b, dist))
        dists[b].add(Pair(a, dist))

    }

    val pair = getDepth(1)

    visited.fill(false)
    val pair2 = getDepth(pair.first)

    println(pair2.second)
}
