import java.io.*;
import java.util.*;

class Main {

	static int N; // 발전소 수
	static int W; // 남아있는 전선 수
	static double M; // 제한 길이
	
	static long[][] pos; // 발전소 위치
	static boolean[][] connected; // 발전소 간 거리
	static double[] dist;

	static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist = new double[N + 1];
		Arrays.fill(dist, Double.MAX_VALUE);
		dist[1] = 0;
		
		pq.add(new Node(1, 0.0));
		while (!pq.isEmpty()) {
			Node node = pq.poll();

			if (node.num == N) return;
			if (dist[node.num] < node.cost) continue;

			for (int i = 1; i <= N; i++) {
				double d = Math.sqrt((pos[i][0] - pos[node.num][0]) * (pos[i][0] - pos[node.num][0]) + (pos[i][1] - pos[node.num][1]) * (pos[i][1] - pos[node.num][1]));
				if (connected[node.num][i]) d = 0;
				
				if (d <= M && dist[i] > node.cost + d) {
					dist[i] = node.cost + d;
					pq.add(new Node(i, dist[i]));
				}

			}
		}
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		M = Double.parseDouble(br.readLine());

		pos = new long[N + 1][2];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			pos[i][0] = Long.parseLong(st.nextToken());
			pos[i][1] = Long.parseLong(st.nextToken());
		}

		connected = new boolean[N + 1][N + 1];
		for (int i = 0; i < W; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			connected[a][b] = true;
			connected[b][a] = true;
		}

		dijkstra();

		System.out.println((long) (dist[N] * 1_000));
    }
}

class Node implements Comparable<Node> {
	int num;
	double cost;
	Node (int n, double c) {
		num = n;
		cost = c;
	}

	@Override
	public int compareTo(Node n) {
		return Double.compare(this.cost, n.cost);
	}
}
