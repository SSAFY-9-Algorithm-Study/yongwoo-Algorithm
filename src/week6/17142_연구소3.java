import java.io.*;
import java.util.*;

public class 17142_연구소3.java {
	
	static int N; // 연구소 크기
	static int M; // 놓을 수 있는 바이러스 개수
	static int[][] arr; // 연구소 배치
	
	static int minTime = Integer.MAX_VALUE; // 번지는데 걸리는 최소시간
	static Point[] selectedVirus; // 활성화된 바이러스 위치 배열
	static boolean[][] visited;
	
	static int wallCnt = 0; // 벽 개수
	static int virusCnt = 0; // 바이러스 개수

	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	// 입력값이 벽일때도 같이 예외처리
	static boolean isWall(int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y] == 1) return true;
		return false;
	}
	
	// 바이러스 퍼뜨리기
	static void bfs() {
		Deque<Point> queue = new ArrayDeque<>();
		visited = new boolean[N][N];
		
		for (int i = 0; i < M; i++) {
			queue.add(selectedVirus[i]);
			
			// 바이러스 위치 visited 처리
			int x = selectedVirus[i].x;
			int y = selectedVirus[i].y;
			visited[x][y] = true;
		}

		int time = 0; // 번지는데 걸린 시간
		int spreadCnt = 0; // 바이러스가 번진 공간 수
		
		while (!queue.isEmpty()) {
			Point p = queue.poll();
			time = p.time;

			if (arr[p.x][p.y] == 0) spreadCnt++;
			
			// 더이상 번질곳이 없다면 bfs 종료
			if (spreadCnt + wallCnt + virusCnt == N * N) break;
			
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				
				if (isWall(nx, ny) || visited[nx][ny]) continue; // 벽이거나 이미 활성 바이러스가 있을때
				
				
				visited[nx][ny] = true;
				queue.add(new Point(nx, ny, time+1));
			}
		}
		
		// 바이러스가 다 퍼졌는지 확인
		if (spreadCnt + wallCnt + virusCnt != N * N) return;
		
		// 바이러스가 다 퍼졌으면 최솟값 비교
		minTime = time < minTime ? time : minTime;
	}
	
	// 활성화할 바이러스 선택
	static void selectVirus(int x, int y, int cnt) {
		if (cnt == M) {
			// 선택 완료
			bfs();
			return;
		}
		
		for (int i = x; i < N; i++) {
			for (int j = y; j < N; j++) {
				if (arr[i][j] == 2) {
					selectedVirus[cnt] = new Point(i, j, 0);
					selectVirus(i, j+1, cnt+1);
				}
			}
			y = 0;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if (arr[i][j] == 1) wallCnt++;
				else if (arr[i][j] == 2) virusCnt++;
			}
		}
		
		// 전체에 이미 빈공간이 없으면 0 출력
		if (wallCnt + virusCnt == N * N) {
			System.out.println(0);
			return;
		}
		
		selectedVirus = new Point[M];
		
		selectVirus(0, 0, 0);
		
		if (minTime == Integer.MAX_VALUE) minTime = -1;
		System.out.println(minTime);
		
	}
}

class Point {
	int x;
	int y;
	int time; // 해당 위치까지 오는데 걸린 시간
	Point(int x, int y, int time) {
		this.x = x;
		this.y = y;
		this.time = time;
	}
}
