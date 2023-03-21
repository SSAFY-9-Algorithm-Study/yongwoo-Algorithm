import java.io.*;
import java.util.*;

public class Main {
	
	static int N; // 행
	static int M; // 열
	static int[][] arr; // 입력 배열
	
	static boolean[][][] visited; // [0][i][j] / [1][i][j] : 벽을 안부수고/부수고 도달 여부
	static int res = -1; // 결과
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	static boolean isOutOfBoundary (int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= M) return true;
		return false;
	}
	
	
	static void bfs() {
		Deque<Point> queue = new ArrayDeque<>();
		queue.add(new Point(0, 0, 1, 0));
		visited[0][0][0] = true;
		
		while (!queue.isEmpty()) {
			Point p = queue.poll();
			if (p.x == N-1 && p.y == M-1) {
				// 목적지 도달
				res = p.dist;
				return;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				int dist = p.dist + 1;
				int wallBroken = p.wallBroken;
				
				if (isOutOfBoundary(nx, ny) || visited[wallBroken][nx][ny]) continue;
				if (wallBroken == 1 && arr[nx][ny] == 1) continue; // 벽을 부수고 왔는데 벽을 만나면
				
				if (wallBroken == 0 && arr[nx][ny] == 1) wallBroken = 1; // 벽을 부수지 않았는데 벽을 만나면
				
				visited[wallBroken][nx][ny] = true;
				queue.add(new Point(nx, ny, dist, wallBroken));
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		for (int i = 0; i < N; i++) {
			String inp = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = inp.charAt(j) - '0';
			}
		}
		
		visited = new boolean[2][N][M]; 
		
		bfs();
		
		System.out.println(res);
	}
}

class Point {
	int x;
	int y;
	int dist; // 이동거리
	int wallBroken; // 0/1 : 벽을 안부수고/부수고 지나왔음(visited 인덱스 처리를 위해 int)
	Point (int x, int y, int dist, int wallBroken) {
		this.x = x;
		this.y = y;
		this.dist = dist;
		this.wallBroken = wallBroken;
	}
}
