import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 학생 수
        int M = Integer.parseInt(st.nextToken()); // 가질 수 있는 최대 블록 수
        int H = Integer.parseInt(st.nextToken()); // 만들어야 하는 높이
        int[][] students = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int j = 0;
            while (st.hasMoreTokens()) {
                students[i][j++] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dp = new int[N][H+1]; // i번째 학생까지 j높이 블럭을 만들 수 있는 경우의 수
        for (int i = 0; i < students[0].length; i++) {
            // 0번째 학생 dp 초기화
            dp[0][students[0][i]]++;
        }

        for (int i = 1; i < N; i++) {
            dp[i] = dp[i-1].clone(); // 이전 경우의수 복사
            
            for (int j = 0; j < students[i].length; j++) {
                int block = students[i][j];
                if (block == 0) break;

                dp[i][block] = (dp[i][block] + 1) % 10_007; // 이번 블럭 혼자 사용했을 때

                for (int k = 1; block + k <= H; k++) {
                    // 이번 블럭 + 이전 경우의 수
                    dp[i][block + k] = (dp[i][block + k] + dp[i-1][k]) % 10_007;
                }
            }
        }
        
        System.out.println(dp[N-1][H]);

    }
}
