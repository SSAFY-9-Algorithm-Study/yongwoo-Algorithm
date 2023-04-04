import java.io.*;
import java.util.*;

class Main {

    static int N; // 배열 크기
    static int M; // 승객 수
    static int O; // 초기 연료
    static int[][] arr; // 입력 배열
    static int[] startPos; // 시작 위치
    static int[][] passPos; // 승객 위치, 승객 행열/도착지 행열

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static boolean isWall(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y] == 1) return true;
        return false;
    }

	// 시작점에서 도착점까지의 거리 반환
    static int bfsStartToEnd(int sx, int sy, int ex, int ey) {
        boolean[][] visited = new boolean[N][N];
        Deque<int[]> queue = new ArrayDeque<>();

        int dist = 0; // 거리

        int[] start = {sx, sy};
        queue.add(start);
        visited[sx][sy] = true;
		
		int[] distArr = {-1, -1};
		queue.add(distArr);

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

			// 이동거리 증가
			if (x == -1) {
				dist++;
				if (dist > O) return -1; // 연료 부족

				queue.add(distArr);
				continue;
			}

            if (x == ex && y == ey) {
				O -= dist;
				return dist;
			}

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isWall(nx, ny) || visited[nx][ny]) continue;
                visited[nx][ny] = true;

                int[] nextPos = {nx, ny};
                queue.add(nextPos);
            }
        }

        return -1;
    }

	// 시작점에서 가장 가까운 사람까지의 거리 반환
	static int bfsFindPeople(int x, int y) {
        boolean[][] visited = new boolean[N][N];
        Deque<int[]> queue = new ArrayDeque<>();

        int dist = 0; // 거리
		int selectPass = -1; // 최단거리 사람 인덱스

        int[] start = {x, y};
        queue.add(start);
        visited[x][y] = true;
		
		int[] distArr = {-1, -1};
		queue.add(distArr);

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            x = pos[0];
            y = pos[1];

			// 거리 증가할때 찾은 승객 있으면 리턴
			if (x == -1) {
				if (selectPass > -1) {
					O -= dist;
					return selectPass;
				}

				dist++;
				if (dist > O) return -1; // 연료 부족
				
				queue.add(distArr);
				continue;
			}

			// 승객 위치 찾으면 저장
			for (int i = 0; i < M; i++) {
				if (passPos[i][0] == x && passPos[i][1] == y) {
					if (selectPass == -1
						|| passPos[i][0] < passPos[selectPass][0]
						|| passPos[i][0] == passPos[selectPass][0] && passPos[i][1] < passPos[selectPass][1]) {
						selectPass = i;
						break;
					}
				}
			}

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isWall(nx, ny) || visited[nx][ny]) continue;
                visited[nx][ny] = true;

                int[] nextPos = {nx, ny};
                queue.add(nextPos);
            }
        }

		return -1;
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        O = Integer.parseInt(st.nextToken());

        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        startPos = new int[2];
        startPos[0] = Integer.parseInt(st.nextToken()) - 1;
        startPos[1] = Integer.parseInt(st.nextToken()) - 1;

        passPos = new int[M][4];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                passPos[i][j] = Integer.parseInt(st.nextToken()) - 1;
            }
        }
        // 입력 끝

		int sx = startPos[0];
		int sy = startPos[1];
		for (int i = 0; i < M; i++) {
			// 가장 가까운 승객 찾기
			int passIdx = bfsFindPeople(sx, sy);
			if (passIdx == -1) {
				O = -1;
				break;
			}
			
			// 도착지까지 거리 계산
			sx = passPos[passIdx][0];
			sy = passPos[passIdx][1];
			int ex = passPos[passIdx][2];
			int ey = passPos[passIdx][3];
			int dist = bfsStartToEnd(sx, sy, ex, ey);

			if (dist == -1) {
				O = -1;
				break;
			}

			passPos[passIdx][0] = passPos[passIdx][1] = -1; // 승객 도착처리

			O += dist * 2; //연료 충전

			sx = ex;
			sy = ey;
		}

		System.out.println(O);
    }
}
