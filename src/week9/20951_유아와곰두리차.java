import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] dp = new int[7][N + 1];

		List<Integer>[] list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			list[u].add(v);
			list[v].add(u);
			dp[0][u]++;
			dp[0][v]++;
		}

		for (int i = 0; i < 6; i++) {
			for (int j = 1; j <= N; j++) {
				for (int k : list[j]) {
					dp[i + 1][k] = (dp[i + 1][k] + dp[i][j]) % 1_000_000_007;
				}
			}
		}

		int sum = 0;
		for (int i = 1; i <= N; i++) {
			sum = (sum + dp[6][i]) % 1_000_000_007; 
		}
		System.out.println(sum);
    }
}
