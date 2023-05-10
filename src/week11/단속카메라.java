import java.util.*;

public class Solution {

    static class Route implements Comparable<Route> {
        int start;
        int end;

        @Override
        public int compareTo(Route o) {
            return this.end - o.end;
        }
    }

    public int solution(int[][] routes) {
        int answer = 0;
        List<Route> list = new ArrayList<>();
        List<Route> tempList = new ArrayList<>();

        for (int i = 0; i < routes.length; i++) {
            Route route = new Route();
            route.start = routes[i][0];
            route.end = routes[i][1];

            list.add(route);
        }

        Collections.sort(list);

        while(!list.isEmpty()) {
            int curPoint = list.get(0).end;
            answer++;
            while(!list.isEmpty() && list.get(0).start<=curPoint && list.get(0).end >= curPoint) {
                list.remove(0);
            }
        }

        return answer;

    }

}
