import java.io.*;
import java.util.*;

class Main {

	static int N; // 배열 크기
	static int[][] arr; // 입력 배열

	static int res; // 밖으로 나간 모래 양

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { -1, 0, 1, 0 };

	static boolean isOutOfBoundary(int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= N) return true;
		return false;
	}

	// 토네이도 이동에 따른 모래 흩날리기, x,y: 토네이도 도착 위치, dir: 이동방향
	static void tornadoMove(int x, int y, int dir) {
		// 내 이동방향 기준 각 방향
		int dirLeft = (dir + 1) % 4;
		int dirRight = (dir + 3) % 4;
		int dirBack = (dir + 2) % 4;

		int sand = arr[x][y];
		
		// 나눠질 모래 양
		int sand10per = (int) (sand * 0.1);
		int sand7per = (int) (sand * 0.07);
		int sand5per = (int) (sand * 0.05);
		int sand2per = (int) (sand * 0.02);
		int sand1per = (int) (sand * 0.01);

		// 토네이도 기준 모래가 흩날릴 위치에 모래 추가
		if (sand10per > 0) {
			int[] per10Left = { x + dx[dir] + dx[dirLeft], y + dy[dir] + dy[dirLeft] };		// 10퍼센트 왼쪽방향 위치
			int[] per10Right = { x + dx[dir] + dx[dirRight], y + dy[dir] + dy[dirRight] }; 	// 10퍼센트 오른쪽방향 위치

			if (isOutOfBoundary(per10Left[0], per10Left[1])) res += sand10per; 	// 범위 바깥이면 결과값에 추가
			else arr[per10Left[0]][per10Left[1]] += sand10per;					// 범위 안이면 배열에 추가

			if (isOutOfBoundary(per10Right[0], per10Right[1])) res += sand10per;
			else arr[per10Right[0]][per10Right[1]] += sand10per;

			arr[x][y] -= sand10per * 2; // 모래 이동값 제거
		}
		if (sand7per > 0) {
			int[] per7Left = { x + dx[dirLeft], y + dy[dirLeft] };
			int[] per7Right = { x + dx[dirRight], y + dy[dirRight] };
			
			if (isOutOfBoundary(per7Left[0], per7Left[1])) res += sand7per;
			else arr[per7Left[0]][per7Left[1]] += sand7per;

			if (isOutOfBoundary(per7Right[0], per7Right[1])) res += sand7per;
			else arr[per7Right[0]][per7Right[1]] += sand7per;

			arr[x][y] -= sand7per * 2;
		}
		if (sand5per > 0) {
			int[] per5 = { x + dx[dir] * 2, y + dy[dir] * 2 };
			
			if (isOutOfBoundary(per5[0], per5[1])) res += sand5per;
			else arr[per5[0]][per5[1]] += sand5per;

			arr[x][y] -= sand5per;
		}
		if (sand2per > 0) {
			int[] per2Left = { x + dx[dirLeft] * 2, y + dy[dirLeft] * 2 };
			int[] per2Right = { x + dx[dirRight] * 2, y + dy[dirRight] * 2 };

			if (isOutOfBoundary(per2Left[0], per2Left[1])) res += sand2per;
			else arr[per2Left[0]][per2Left[1]] += sand2per;

			if (isOutOfBoundary(per2Right[0], per2Right[1])) res += sand2per;
			else arr[per2Right[0]][per2Right[1]] += sand2per;

			arr[x][y] -= sand2per * 2;
		}
		if (sand1per > 0) {
			int[] per1Left = { x + dx[dirBack] + dx[dirLeft], y + dy[dirBack] + dy[dirLeft] };
			int[] per1Right = { x + dx[dirBack] + dx[dirRight], y + dy[dirBack] + dy[dirRight] };

			if (isOutOfBoundary(per1Left[0], per1Left[1])) res += sand1per;
			else arr[per1Left[0]][per1Left[1]] += sand1per;

			if (isOutOfBoundary(per1Right[0], per1Right[1])) res += sand1per;
			else arr[per1Right[0]][per1Right[1]] += sand1per;

			arr[x][y] -= sand1per * 2;
		}

		// 남은 모래 이동
		int[] remain = { x + dx[dir], y + dy[dir] };
		
		if (isOutOfBoundary(remain[0], remain[1])) res += arr[x][y];
		else arr[remain[0]][remain[1]] += arr[x][y];

		arr[x][y] = 0;
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		res = 0;

		// 시작점 
		int x = N / 2;
		int y = N / 2;

		// 한 방향 당 이동
		for (int move = 0; move < (N - 1) * 2 + 1; move++) {
			int dir = move % 4; // 이동 방향

			for (int i = 0; i < move / 2 + 1; i++) {
				// 같은 방향으로 (move / 2 + 1)번 이동
				x += dx[dir];
				y += dy[dir];	

				tornadoMove(x, y, dir);

				if (x == 0 && y == 0) break; // 마지막 한칸 더 이동하므로 break
			}
		}

		System.out.println(res);
    }
}
