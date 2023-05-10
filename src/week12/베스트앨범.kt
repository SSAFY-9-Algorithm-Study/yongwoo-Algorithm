class Solution {
    fun solution(genres: Array<String>, plays: IntArray): IntArray {
        // 장르별 전체 재생횟수
        val genreSumMap = hashMapOf<String, Int>()
        
        // 장르별 재생횟수 top 2
        val genrePlayTop2 = hashMapOf<String, Array<Pair<Int, Int>>>()
        
        // map 계산
        for (i in genres.indices) {
            val genre = genres[i]
            val genreSum = genreSumMap[genres[i]] ?: 0
            
            genreSumMap[genres[i]] = genreSum + plays[i]
            
            if (genreSum == 0) {
                // 장르가 등록되지 않음
                genrePlayTop2[genre] = Array(2) { Pair(0, 0) }
                genrePlayTop2[genre]!![0] = Pair(i, plays[i])
            } 
            else {
                // 등록된 장르, 큰 순으로 배열 비교
                val playArr = genrePlayTop2[genre]!!
                if (playArr[0].second < plays[i]) {
                    playArr[1] = playArr[0]
                    playArr[0] = Pair(i, plays[i])
                }
                else if (playArr[1].second < plays[i]) {
                    playArr[1] = Pair(i, plays[i])
                }
            }
        }
        
        // 장르별 재생횟수 내림차순 리스트 생성
        val genreSumList = genreSumMap.toList().sortedByDescending { it.second }
        
        val answerList = arrayListOf<Int>()
        for (pair in genreSumList) {
            val genre = pair.first
            val playArr = genrePlayTop2[genre]!!
            
            answerList.add(playArr[0].first)
            if (playArr[1].second > 0) answerList.add(playArr[1].first)
        }
        
        val answer = answerList.toIntArray()
        return answer
    }
}
