import java.io.*;
import java.util.*;

class Main {

	static int N; // 건물 개수
	static int L; // 왼쪽에서 보이는 건물 수
	static int R; // 오른쪽에서 보이는 건물 수

	static long[][] comb; // 조합 계산값 memo
	static long[] fac; // 팩토리얼 계산값 memo
	static long[][] divs; // div 계산값 memo

	static final int MOD = 1_000_000_007;

	// 조합 계산하여 반환
	static long getComb(int n, int r) {
		if (comb[n][r] == 0) {
			if (n == r || r == 0) comb[n][r] = 1;
			else comb[n][r] = (getComb(n - 1, r - 1) + getComb(n - 1, r)) % MOD;
		}
		return comb[n][r];
	}

	// 팩토리얼 계산하여 반환
	static long getFac(int n) {
		if (fac[n] == 0) fac[n] = (getFac(n - 1) * (long) n) % MOD;
		return fac[n];
	}

	// n개의 남은 자리에서 idx부터 선택 (한쪽에서 보이는 빌딩 개수(idx)를 맞춰주기 위함)
	static long div(int n, int idx) {
		if (idx == 0) {
			// 0개를 선택하는 경우는 불가능하지만 0개중에 0개 선택은 1개의 경우에 가능
			if (n == 0) return 1;
			else return 0;
		}
		if (n == idx) return 1; // 보이는 건물 개수와 남은 건물 수가 같음
		if (idx == 1) {
			if (n <= 2) return 1; // 젤 높은거 끝에 두고 한자리만 남음
			if (n > 2) return getFac(n - 1); // 젤 높은거 끝에 두고 안에 건물들 배치 경우의 수
		}

		if (divs[n][idx] != 0) return divs[n][idx];

		long cnt = 0;

		for (int i = idx; i <= n; i++) {
			long sum = getComb(n - 1, i - 1);
			sum = (sum * div(i - 1, idx - 1)) % MOD; // 왼쪽 케이스, 개수가 영향있는 쪽
			sum = (sum * div(n - i + 1, 1)) % MOD; // 오른쪽 케이스, 안보이는 쪽으로 자리가 상관없으므로 idx == 1
			cnt = (cnt + sum) % MOD;
		}

		divs[n][idx] = cnt;
		return cnt;
	}

	static long calculate() {
		long cnt = 0; // 경우의 수 합

		for (int i = L; i <= N - R + 1; i++) {
			long sum = getComb(N - 1, i - 1);
			sum = (sum * div(i - 1, L - 1)) % MOD; // 왼쪽 케이스
			sum = (sum * div(N - i, R - 1)) % MOD; // 오른쪽 케이스
			cnt = (cnt + sum) % MOD;
		}

		return cnt;
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		comb = new long[N + 1][N + 1];
		comb[1][1] = 1;
		comb[1][0] = 1;

		fac = new long[N + 1];
		fac[1] = 1;

		divs = new long[N][N];

		long res = calculate();

		System.out.println(res);
    }
}
