import java.io.*;
import java.util.*;

public class Main {
	
	static int N;
	
	static boolean[] isNotPrime;
	static List<Integer> primes;
	
	static void setPrimes() {
		for (int i = 2; i <= N; i++) {
			if (isNotPrime[i]) continue;
			
			primes.add(i);
			for (int j = i; j <= N; j += i) {
				isNotPrime[j] = true; 
			}
		}
	}
	
	static int getSum() {
		int cnt = 0;
		if (primes.get(primes.size() - 1) == N) cnt++;
		
		int sum = primes.get(0);
		int i = 0;
		int j = 0;
		while (j < primes.size() - 1) {
			if (sum == N) cnt++;
			
			if (sum > N) sum -= primes.get(i++);
			else sum += primes.get(++j);
		}
		
		return cnt;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		N = Integer.parseInt(br.readLine());
		
		isNotPrime = new boolean[N + 1];
		primes = new ArrayList<Integer>();
		setPrimes();
		
		int res = 0;
		if (N > 1) res = getSum();
		
		System.out.println(res);
	}
}
