// 입력값
var N = 0
lateinit var list: Array<ArrayList<Int>> // 인접 리스트

lateinit var visited: BooleanArray // dfs 조회 여부
var deepNode = 0
var deepDepth = 0

fun dfs(num: Int, depth: Int) {
    visited[num] = true
    if (depth > deepDepth) {
        deepDepth = depth
        deepNode = num
    }

    list[num].forEach {
        if (!visited[it]) {
            dfs(it, depth + 1)
        }
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    N = readLine().toInt()
    list = Array(N + 1) { ArrayList() }
    visited = BooleanArray(N + 1)

    repeat(N - 1) {
        val (u, v) = readLine().split(" ").map { it.toInt() }
        list[u].add(v)
        list[v].add(u)
    }

    dfs(1, 0)

    deepDepth = 0
    visited.fill(false)
    dfs(deepNode, 0)

    print((deepDepth + 1) / 2)
}
