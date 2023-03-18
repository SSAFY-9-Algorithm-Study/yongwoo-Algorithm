import java.io.*;
import java.util.*;

class Main {

	static int N; // 원판 수
	static int M; // 원판 당 숫자 수
	static int T; // 회전 수
	static int[][] arr; // 원판에 적힌 숫자, x의 배수번째 원판을 움직이니까 1부터 시작

	static boolean isAdj = false; // 인접한 수가 있는지
	static int sum = 0; // 원판 숫자 합
	static int cnt = 0; // 원판 숫자 개수

	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};

	// x번째 원판 d방향으로 k칸 회전
	static void spin(int x, int d, int k) {
		int[] tmp = arr[x].clone();

		for (int i = 0; i < M; i++) {
			int spinIdx = 0;
			if (d == 0) spinIdx = (i + M - k) % M; 	// 시계
			else spinIdx = (i + k) % M; 			// 반시계

			arr[x][i] = tmp[spinIdx];
		}
	}

	// 인접한 수 0으로 변경
	static void dfs(int x, int y, int num) {
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (nx >= N || nx < 0) continue; // 범위 바깥
			
			// 원판 안에서 순회할 수 있도록
			if (ny == -1) ny = M-1;
			else if (ny == M) ny = 0;

			if (arr[nx][ny] == num) {
				// 전체 합 감소
				sum -= arr[x][y];
				sum -= arr[nx][ny];

				// 숫자 개수 감소
				if (arr[x][y] != 0) cnt--;
				if (arr[nx][ny] != 0) cnt--;

				arr[x][y] = 0; // 최초 dfs를 시작하는 원점에서 값을 처리하지 않았으므로 여기서 처리 
				arr[nx][ny] = 0;
				isAdj = true;
				dfs(nx, ny, num);
			}
		}	
	}

	public static void main(String[] args) throws Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		cnt = N * M;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				sum += arr[i][j];
			}
		}

		for (int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());

			// x의 배수인 원판 d방향으로 k칸 회전
			for (int i = 1; x*i <= N; i++) {
				spin(x*i-1, d, k);
			}

			// 인접한 같은 수가 있는지 찾고 있으면 dfs
			isAdj = false;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(arr[i][j] != 0) dfs(i, j, arr[i][j]);
				}
			}

			// 모든 숫자가 0이면 종료
			if (sum == 0) break;

			// 인접한 수가 하나도 없었으면 평균 구해서 평균보다 큰 수 1 빼고 작은 수 1 더하기
			if (!isAdj) {
				double avg = (double) sum / cnt;
				
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < M; j++) {
						if (arr[i][j] == 0) continue;
						
						if (arr[i][j] > avg) {
							arr[i][j]--;
							sum--;
						}
						else if (arr[i][j] < avg) {
							arr[i][j]++;
							sum++;
						}
					}
				}
			}
		}

		System.out.println(sum);
	}
}
