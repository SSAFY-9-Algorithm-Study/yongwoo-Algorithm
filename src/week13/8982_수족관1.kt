fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val floorList = IntArray(40_000) // 각 위치별 높이(깊이) 저장
    var by = 0 // 이전 y값
    for (i in 0 until N) {
        val (y, x) = readLine().split(" ").map { it.toInt() }
        if (y == by) continue // 꼭짓점 위치 사이가 세로일 때

        for (j in by until y) {
            floorList[j] = x
        }

        by = y
    }

    val maxWidth = by // 가로 최대길이

    val K = readLine().toInt()
    val holeList = BooleanArray(maxWidth) // 해당 위치에 구멍이 있는지 여부
    repeat(K) {
        val (y1, x1, y2, x2) = readLine().split(" ").map { it.toInt() }
        for (i in y1 until y2) {
            holeList[i] = true
        }
    }

    val waterList = IntArray(maxWidth) // 해당 위치의 물 높이
    // 왼쪽부터 구멍 높이 기준으로 채워지는 물 확인
    var holeHeight = 0
    for (i in 0 until maxWidth) {
        if (!holeList[i] && holeHeight < floorList[i]) {
            // 구멍 위치가 아니고 내 높이가 구멍보다 깊게 있으면
            waterList[i] = floorList[i] - holeHeight
        }
        else {
            // 구멍이거나 구멍이 내 위치보다 높으면
            // 구멍이 아니더라도 가장 최근 구멍 위치보다 현재 위치가 더 높으면 구멍위치가 재설정되어야 함
            waterList[i] = 0
            holeHeight = floorList[i]
        }
    }

    // 오른쪽부터 ,,
    holeHeight = 0
    for (i in maxWidth - 1 downTo 0) {
        if (!holeList[i] && holeHeight < floorList[i]) {
            waterList[i] = waterList[i].coerceAtMost(floorList[i] - holeHeight)
        }
        else {
            waterList[i] = 0
            holeHeight = floorList[i]
        }
    }

    // 넓이 계산
    var area = 0
    waterList.forEach {
        area += it
    }

    print(area)
}
