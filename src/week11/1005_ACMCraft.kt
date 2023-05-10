import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val T = readLine().toInt()
    repeat(T) {
        val (N, K) = readLine().split(" ").map { it.toInt() }
        val buildTimes = readLine().split(" ").map { it.toInt() }

        val list = Array(N) { ArrayList<Int>() }
        val cntArr = IntArray(N)

        repeat(K) {
            val (X, Y) = readLine().split(" ").map { it.toInt() - 1 }
            list[X].add(Y)
            cntArr[Y]++
        }
        val W = readLine().toInt() - 1

        val nodeQueue = ArrayDeque<Int>()
        val timeQueue = ArrayDeque<Int>()

        for (i in 0 until N) {
            if (cntArr[i] == 0) {
                nodeQueue.add(i)
                timeQueue.add(buildTimes[i])
            }
        }

        val startTimeArr = IntArray(N)
        while (cntArr[W] > 0) {
            val node = nodeQueue.poll()
            val time = timeQueue.poll()

            list[node].forEach {
                startTimeArr[it] = startTimeArr[it].coerceAtLeast(time)
                cntArr[it]--

                if (cntArr[it] == 0) {
                    nodeQueue.add(it)
                    timeQueue.add(startTimeArr[it] + buildTimes[it])
                }
            }
        }

        println(startTimeArr[W] + buildTimes[W])
    }
}
