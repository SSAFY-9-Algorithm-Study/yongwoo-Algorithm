import java.io.*;
import java.util.*;

class Main {

	static int N; // 행
	static int M; // 열
	static int K; // 이동 횟수
	static int[][] arr; // 입력 배열

	// 동 남 서 북 순서 방향벡터
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};

	static int[] point; // 해당 위치의 획득 가능 점수를 저장할 배열
	static int[][] visited; // 0: 방문하지 않음, 1이상: 방문하였고 해당 위치의 획득 가능 점수 인덱스

	static boolean isWall(int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= M) return true;
		return false;
	}
	
	static void dfs(int x, int y, int pointIdx) {
		visited[x][y] = pointIdx;
		point[pointIdx] += arr[x][y];
	
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (isWall(nx, ny) || visited[nx][ny] != 0 || arr[nx][ny] != arr[x][y]) continue;

			dfs(nx, ny, pointIdx);
		}
	}

	public static void main(String[] args) throws Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		point = new int[N*M+1];
		visited = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int num = 6; // 현재 주사위 숫자
		int[] dice = {3, 5, 4, 2}; // 방향마다의 주사위 숫자, 동 남 서 북 순서

		int dir = 0; // 이동 방향
		int nx = 0, ny = 0; // 현재 위치

		int pointSum = 0; // 출력 총합 점수
		int pointIdx = 1; // point배열을 확인할 인덱스

		// K번 주사위 이동
		for (int i = 0; i < K; i++) {
			nx += dx[dir];
			ny += dy[dir];

			// 주사위 값 변경
			int prevNum = num;
			num = dice[dir];

			// 이동방향의 반대는 이전의 나, 이동방향은 나의 반대숫자
			dice[(dir+2)%4] = prevNum;
			dice[dir] = 7 - prevNum;
			
			// 점수 획득
			if (visited[nx][ny] == 0) dfs(nx, ny, pointIdx++);
			pointSum += point[visited[nx][ny]];

			// 다음 방향 설정
			if (num > arr[nx][ny]) dir = (dir+1) % 4;
			else if (num < arr[nx][ny]) dir = (dir+3) % 4;
			
			// 다음 방향이 벽일 경우 반대방향
			if (isWall(nx+dx[dir], ny+dy[dir])) dir = (dir+2) % 4;
		}

		System.out.println(pointSum);
	}
}
