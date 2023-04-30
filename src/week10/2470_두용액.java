import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);

		int min = 0;
		int max = N - 1;
		int sum = Math.abs(arr[min] + arr[max]);

		int i = min;
		int j = max;
		while (i < j) {
			if (Math.abs(arr[i] + arr[j]) < sum) {
				sum = Math.abs(arr[i] + arr[j]);
				min = i;
				max = j;
			}

			if (arr[i] + arr[j] == 0) break;
			else if (arr[i] + arr[j] < 0) i++;
			else j--;
		}

		System.out.print(arr[min] + " " + arr[max]);
	}
}
