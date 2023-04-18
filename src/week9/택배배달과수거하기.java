class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        int lastDel = n - 1; // 배달할게 남은 마지막 집
        int lastPic = n - 1; // 수거할게 남은 마지막 집
        
        while (lastDel >= 0 && deliveries[lastDel] == 0) lastDel--;
        while (lastPic >= 0 && pickups[lastPic] == 0) lastPic--;
        
        long answer = 0;
        
        while (lastDel >= 0 || lastPic >= 0) {
            answer += Math.max(lastDel + 1, lastPic + 1) * 2;
            
            // 끝부터 배달 최대한 처리해버리기
            int i = cap;
            while (lastDel >= 0 && i >= deliveries[lastDel]) {
                i -= deliveries[lastDel--];
            }
            if (lastDel >= 0) deliveries[lastDel] -= i; // 남은 배달 처리하기
            
            // 끝부터 수거 최대한 처리해버리기
            i = cap;
            while (lastPic >= 0 && i >= pickups[lastPic]) {
                i -= pickups[lastPic--];
            }
            if (lastPic >= 0) pickups[lastPic] -= i; // 남은 수거 처리하기
        }
        
        return answer;
    }
}
