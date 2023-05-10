fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt() * 6 - 21
    if (N <= 2) print(0)
    else if (N == 3) print(210)
    else {
        val MOD = 1_000_000_007
        val dp = LongArray(N + 1)
        dp[0] = 1
        for (i in 1..N) {
            dp[i] = (dp[i - 1] + 1) % MOD
        }

        for (i in 2 .. N) {
            dp[i] =  (dp[i] + dp[i - 2]) % MOD
        }
        for (i in 3 .. N) {
            dp[i] =  (dp[i] + dp[i - 3]) % MOD
        }
        for (i in 4 .. N) {
            dp[i] =  (dp[i] + dp[i - 4]) % MOD
        }
        for (i in 5 .. N) {
            dp[i] =  (dp[i] + dp[i - 5]) % MOD
        }
        for (i in 6 .. N) {
            dp[i] =  (dp[i] + dp[i - 6]) % MOD
        }

        println((dp[N] * 30) % MOD)
    }
}
