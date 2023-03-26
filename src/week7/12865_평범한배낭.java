import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	    // 입력
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    int N = Integer.parseInt(st.nextToken()); // 물품 수
	    int K = Integer.parseInt(st.nextToken()); // 버틸 수 있는 무게
	    
	    int[][] info = new int[N+1][2]; // [i][0]/[i][1] : i번째 물건의 무게/가치
	    for (int i = 1; i <= N; i++) {
	    	st = new StringTokenizer(br.readLine());
	    	info[i][0] = Integer.parseInt(st.nextToken());
	    	info[i][1] = Integer.parseInt(st.nextToken());
	    }
	    
	    int[][] dp = new int[N+1][K+1];
	    for (int i = info[1][0]; i <= K; i++) {
	    	// 0번째 물품 dp 초기화
	    	dp[1][i] = info[1][1];
	    }
	    
	    for (int i = 2; i <= N; i++) {
	    	for (int j = 1; j <= K; j++) {
	    		if (j >= info[i][0]) dp[i][j] = info[i][1]; // 무게가 가능하다면 현재 물품 가치
	    		dp[i][j] = Math.max(dp[i][j], dp[i-1][j]); // 이전 dp값
	    		
	    		// 이전 dp의 현재 가능무게 - 현재 물품 무게에서의 가치 + 현재 물품가치
	    		int w = j - info[i][0];
	    		if (w > 0) dp[i][j] = Math.max(dp[i][j], dp[i-1][w] + info[i][1]);
	    	}
	    }
	    
	    System.out.println(dp[N][K]);
	}
}
