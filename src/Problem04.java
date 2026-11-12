import java.util.Arrays;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int[] minDist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0;

        int totalCost = 0;

        for (int i = 0; i < n; i++) {
            int curr = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (curr == -1 || minDist[j] < minDist[curr])) {
                    curr = j;
                }
            }

            visited[curr] = true;
            totalCost += minDist[curr];

            for (int next = 0; next < n; next++) {
                if (!visited[next]) {
                    int dist = Math.abs(points[curr][0] - points[next][0]) +
                               Math.abs(points[curr][1] - points[next][1]);
                    if (dist < minDist[next]) {
                        minDist[next] = dist;
                    }
                }
            }
        }

        return totalCost;
    }
}
