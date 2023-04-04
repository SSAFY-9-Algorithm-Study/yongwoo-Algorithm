import java.io.*;
import java.util.*;

class Main {

	static int N; // 배열 크기
	static int M; // 일반 블록 색상 수
	static int[][] arr; // 입력 배열

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static boolean[][] selectedGroup; // 선택된 그룹 배열
	static int selectedBlockCnt; // 선택된 블록 그룹의 크기
	static int selectedRainbowBlockCnt; // 선택된 블록 그룹 무지개 블록 수
	static int res;

	static boolean[][] visited;

	// 선택된 블록 제거
	static void deleteBlock() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (selectedGroup[i][j]) arr[i][j] = -2;
			}
		}
	}

	// 90도 반시계 회전
	static void turn() {
		int[][] tmp = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tmp[i][j] = arr[j][N - i - 1];
			}
		}

		arr = tmp;
	}

	// 중력 작용
	static void gravity() {
		// 열 별로 해당 위치가 -2가 아니면 stack 위치와 변경, -1은 중력 받지 않음
		for (int i = 0; i < N; i++) {
			int stack = N - 1; // 다음에 쌓일 수 있는 위치

			for (int j = N - 1; j >= 0; j--) {
				if (arr[j][i] == -2) continue; // 빈공간

				if (j == stack || arr[j][i] == -1) {
					// 검은색 블록이거나 바꾸고자 하는 위치와 stack이 같을 경우
					stack = j - 1;
					continue;
				}

				int tmp = arr[j][i];
				arr[j][i] = arr[stack][i];
				arr[stack][i] = tmp;
				stack--;
			}
		}
	}

	static boolean isWall(int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= N) return true;
		return false;
	}

	// 기준 블록에 따라 블록 그룹 찾기
	static void bfs(int sx, int sy) {
		Queue<int[]> queue = new ArrayDeque<>();
		int[] start = { sx, sy };
		queue.add(start);
		
		boolean[][] group = new boolean[N][N]; // 선택된 그룹
		
		visited[sx][sy] = true;
		group[sx][sy] = true;

		int blockCnt = 1; // 블록 그룹의 블록 수
		int rainbowBlockCnt = 0; // 블록 그룹의 무지개 블록 수
		
		while (!queue.isEmpty()) {
			int[] pos = queue.poll();
			int x = pos[0];
			int y = pos[1];

			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];

				if (isWall(nx, ny) || group[nx][ny]) continue;
				if (arr[nx][ny] != 0 && arr[nx][ny] != arr[sx][sy]) continue; // 무지개색 or 같은 색 아니면 continue

				int[] next = { nx, ny };
				queue.add(next);

				visited[nx][ny] = true;
				group[nx][ny] = true;
				blockCnt++;
				if (arr[nx][ny] == 0) rainbowBlockCnt++;
			}
		}

		// 그룹 생성이 가능한지 확인 및 가장 큰 블록인지 확인
		if (blockCnt < 2) return;
		if (blockCnt > selectedBlockCnt || (blockCnt == selectedBlockCnt && rainbowBlockCnt >= selectedRainbowBlockCnt)) {
			selectedBlockCnt = blockCnt;
			selectedRainbowBlockCnt = rainbowBlockCnt;
			selectedGroup = group;
		}
	}

    public static void main(String[] args) throws IOException {
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
			}
		}

		while (true) {
			visited = new boolean[N][N];
			selectedBlockCnt = 0;
			selectedRainbowBlockCnt = 0;
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr[i][j] <= 0 || visited[i][j]) continue; // 기준 블록이 될 수 없으면 continue

					bfs(i, j);
				}
			}

			// 생성 가능한 블록 그룹 없으면 종료
			if (selectedBlockCnt == 0) break;

			// 점수 획득-제거-중력-회전-중력
			res += selectedBlockCnt * selectedBlockCnt;
			deleteBlock();
			gravity();
			turn();
			gravity();
		}

		System.out.println(res);
    }

	// 테스트용 출력
	static void test() {
		System.out.println();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j] == -2) System.out.print("    ");
				else System.out.printf("%3s ", arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
