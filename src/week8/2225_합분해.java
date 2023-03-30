import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 0부터 N까지의 정수 K개를 더해서 합이 N이 되는 경우의 수
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); 
		int K = Integer.parseInt(st.nextToken());
		
		// i-1,j의 결과와 i,j-1의 결과의 합
		long[][] dp = new long[K][N + 1];
		
		// 0번째 행, 0번째 열 1로 초기화
		for (int i = 0; i < K; i++) {
			dp[i][0] = 1;
		}
		for (int i = 0; i < N + 1; i++) {
			dp[0][i] = 1;
		}
		
		for (int i = 1; i < K; i++) {
			for (int j = 1; j <= N; j++) {
				dp[i][j] = (dp[i - 1][j]  + dp[i][j - 1]) % 1_000_000_000;
			}
		}
		
		System.out.println(dp[K - 1][N]);
	}
}
