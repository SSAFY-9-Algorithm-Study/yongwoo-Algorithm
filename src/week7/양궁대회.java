class Solution {
    
    int max = 0; // 라이언이 획득할 수 있는 최대 점수
    int[] answer = new int[11];
    
    // cnt: 라이언이 쏜 화살수, idx: idx부터 원소 탐색, arr: 라이언 과녁 상황
    void getPoint(int cnt, int idx, int[] arr, int[] info, int n) {
        for (int i = idx; i < 10; i++) {
            int c = info[i]+1;
            
            if (cnt+c <= n) {
                arr[i] = c;
                getPoint(cnt+c, i+1, arr, info, n); // i번째 인덱스 선택
                arr[i] = 0;
            }
        }
        
        // 점수 계산
        int apeachPoint = 0; // 어피치의 점수 
        int lionPoint = 0; // 라이언 점수
        for (int i = 0; i < 10; i++) {
            if (arr[i] > 0) lionPoint += 10-i;
            else if (info[i] > 0) apeachPoint += 10-i;
        }
        int diff = lionPoint - apeachPoint;
        
        // 남은 화살 0에 넣고 max 비교
        arr[10] = n - cnt;
        if (max <= diff) {
            if (max == diff) {
                for (int i = 9; i >= 0; i--) {
                    if (arr[i] > answer[i]) {
                        answer = arr.clone();
                        break;
                    }
                    else if (arr[i] < answer[i]) break;
                }
            }
            else {
                answer = arr.clone();
            }
            max = diff;
        }
        arr[10] = 0;
    }
    
    public int[] solution(int n, int[] info) {
        int[] arr = new int[11];
        max = 0;
        
        getPoint(0, 0, arr, info, n);
        
        if (max == 0) {
            answer = new int[1];
            answer[0] = -1;
        }
        
        return answer;
    }
}
