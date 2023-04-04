import java.io.*;
import java.util.*;

class Main {

	static int N; // 배열 크기
	static int M; // 파이어볼 개수
	static int K; // 이동 명령 횟수
	static List<int[]> fireballInfo; // 파이어볼 정보

	// 이동 방향
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		fireballInfo = new ArrayList<>();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()); // 행 위치
			int c = Integer.parseInt(st.nextToken()); // 열 위치
			int m = Integer.parseInt(st.nextToken()); // 질량
			int s = Integer.parseInt(st.nextToken()); // 속력
			int d = Integer.parseInt(st.nextToken()); // 방향
			int[] fireball = { r, c, m, s, d };
			fireballInfo.add(fireball);
		}
		// 입력 끝

		// k번 명령
		for (int k = 0; k < K; k++) {
			// 파이어볼 이동
			for (int[] fireball : fireballInfo) {
				int nx = (fireball[0] + dx[fireball[4]] * fireball[3]) % N;
				int ny = (fireball[1] + dy[fireball[4]] * fireball[3]) % N;
				
				// 범위 바깥 되돌리기
				if (nx < 1) nx += N;
				if (ny < 1) ny += N;

				fireball[0] = nx;
				fireball[1] = ny;
			}

			// 겹치는 파이어볼 찾아서 나누기
			for (int i = 0; i < M; i++) {
				int[] fireball = fireballInfo.get(i);
				int r = fireball[0];
				int c = fireball[1];
				int m = fireball[2];
				int s = fireball[3];
				int d = fireball[4];

				if (r == -1) continue;
				
				int cnt = 1; // 합쳐진 파이어볼 개수
				int direction = 0; // 합쳐진 파이어볼 방향
				for (int j = i + 1; j < M; j++) {
					int[] fireball2 = fireballInfo.get(j);
					if (fireball2[0] == -1) continue;
					
					if (r == fireball2[0] && c == fireball2[1]) {
						m += fireball2[2];
						if (direction == 0) {
							if (d % 2 != fireball2[4] % 2)
								direction = 1;
						}
						s += fireball2[3];
						cnt++;

						fireball2[0] = -1;
					}
				}

				// 파이어볼 나누기
				if (cnt > 1) {
					fireball[0] = -1;
					
					int nm = m / 5;
					int ns = s / cnt;

					if (nm == 0) continue; // 질량 0이면 소멸

					int[] newFireball1 = { r, c, nm, ns, direction };
					int[] newFireball2 = { r, c, nm, ns, direction + 2 };
					int[] newFireball3 = { r, c, nm, ns, direction + 4 };
					int[] newFireball4 = { r, c, nm, ns, direction + 6 };
					fireballInfo.add(newFireball1);
					fireballInfo.add(newFireball2);
					fireballInfo.add(newFireball3);
					fireballInfo.add(newFireball4);
				}
				
			}

			// 사라진 파이어볼 제거
			for (int i = 0; i < M; i++) {
				if (fireballInfo.get(i)[0] == -1) {
					fireballInfo.remove(i);
					i--;
					M--;
				}
			}

			M = fireballInfo.size();
		}

		// 질량 합 출력
		int mSum = 0;
		for (int[] fireball : fireballInfo) {
			mSum += fireball[2];
		}
		System.out.println(mSum);
    }
}
