import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine()); // 우유 도시 영역
		
		// 입력 배열 0번째 행, 0번쩌 열은 -1로 초기화
		int[][] arr = new int[N + 1][N + 1];
		for (int i = 0; i < N + 1; i++) {
			arr[0][i] = -1;
			arr[i][0] = -1;
		}
		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// dp 배열 0번째 행, 0번째 열은 0으로 초기화
		int[][] dp = new int[N + 1][N + 1];
		for (int i = 0; i < N + 1; i++) {
			dp[0][i] = 0;
			dp[i][0] = 0;
		}
		
		// 북쪽, 서쪽 중 최댓값 받아오기, 3을 나눈 나머지값이 현재 위치 arr값과 같으면 +1
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				
				if (dp[i][j] % 3 == arr[i][j]) dp[i][j]++;
			}
		}
		
		System.out.println(dp[N][N]);
	}
}
