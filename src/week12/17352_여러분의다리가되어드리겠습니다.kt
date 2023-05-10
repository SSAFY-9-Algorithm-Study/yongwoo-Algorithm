var N = 0
lateinit var parent: IntArray

fun getParent(n: Int): Int {
    if (parent[n] != n) parent[n] = getParent(parent[n])
    return parent[n]
}

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    N = readLine().toInt()
    parent = IntArray(N + 1) { i -> i }
    repeat(N - 2) {
        val (a, b) = readLine().split(" ").map { it.toInt() }
        parent[getParent(a)] = getParent(b)
    }

    val p1 = getParent(1)
    var p2 = 0
    for (i in 2 .. N) {
        p2 = getParent(i)
        if (p1 != p2) break
    }

    print("$p1 $p2")
}
