import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		int aMax = 0; // a 전봇대의 최댓값
		int bMax = 0; // b 전봇대의 최댓값

		int n = Integer.parseInt(br.readLine());
		int[][] input = new int[n][2];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
	
			aMax = Math.max(a, aMax);
			bMax = Math.max(b, bMax);
			input[i][0] = a;
			input[i][1] = b;
		}

		// 전깃줄이 연결되어 있는 위치 1로 초기화
		int[][] dp = new int[aMax + 1][bMax + 1];
		for (int i = 0; i < n; i++) {
			int a = input[i][0];
			int b = input[i][1];
			
			dp[a][b] = 1;
		}

		for (int i = 1; i <= aMax; i++) {
			for (int j = 1; j <= bMax; j++) {
				if (dp[i][j] == 0) {
					// 해당 위치에 전깃줄이 연결되어 있지 않으면 이전 인덱스 중 최댓값
					dp[i][j] = Math.max(dp[i - 1][j - 1], Math.max(dp[i][j - 1], dp[i - 1][j]));
				}
				else {
					// 전깃줄이 있으면 나를 포함한 상황에서 최댓값
					dp[i][j] = 1 + dp[i - 1][j - 1];
				}
			}
		}

		System.out.println(n - dp[aMax][bMax]);
    }
}
