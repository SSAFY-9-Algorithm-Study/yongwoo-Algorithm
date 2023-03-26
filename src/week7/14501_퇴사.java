import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int N = Integer.parseInt(br.readLine());
        int[] T = new int[N+1];
        int[] P = new int[N+1];
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N+2]; // N번째부터 해당 상담 포함하여 최댓값 저장
        for (int i = N; i > 0; i--) {
            dp[i] = dp[i+1];

            if (i + T[i] > N+1) continue;// 이번 상담이 기간을 넘기면 continue
                
            dp[i] = Math.max(dp[i], P[i]+dp[i+T[i]]); // 이전 dp 결과와 이번 상담 포함한 최댓값 중 선택
        }

        System.out.println(dp[1]);
    }
}
