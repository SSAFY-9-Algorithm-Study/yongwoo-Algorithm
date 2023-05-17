var N = 0
lateinit var list: Array<ArrayList<Int>>
lateinit var visited: BooleanArray
lateinit var dp: Array<Array<Int>>

fun dfs(node: Int, isPol: Boolean): Int {
    var polCnt = if (isPol) 1 else 0
    if (dp[node][polCnt] >= 0) return dp[node][polCnt]

    visited[node] = true

    list[node].forEach {
        if (!visited[it]) {
            polCnt += if (isPol) {
                dfs(it, true).coerceAtMost(dfs(it, false))
            }
            else {
                dfs(it, true)
            }
        }
    }

    if (isPol) dp[node][1] = polCnt
    else dp[node][0] = polCnt

    visited[node] = false
    return polCnt
}

fun main() = with(System.`in`.bufferedReader()) {
    N = readLine().toInt()
    list = Array(N + 1) { arrayListOf() }
    visited = BooleanArray(N + 1)
    dp = Array(N + 1) { arrayOf(-1, -1) }
    repeat(N - 1) {
        val (u, v) = readLine().split(" ").map { it.toInt() }
        list[u].add(v)
        list[v].add(u)
    }

    print(dfs(1, true).coerceAtMost(dfs(1, false)))
}
