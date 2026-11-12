import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> adj = new ArrayList<>(n);
        List<List<Integer>> revAdj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            revAdj.add(new ArrayList<>());
        }

        for (int[] dep : dependencies) {
            adj.get(dep[0]).add(dep[1]);
            revAdj.get(dep[1]).add(dep[0]);
        }

        boolean[] visited = new boolean[n];
        int[] order = new int[n];
        int orderIdx = 0;
        int[] stack = new int[n];
        int[] edgeIdx = new int[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int top = 0;
                stack[top] = i;
                visited[i] = true;

                while (top >= 0) {
                    int u = stack[top];
                    List<Integer> neighbors = adj.get(u);
                    if (edgeIdx[u] < neighbors.size()) {
                        int v = neighbors.get(edgeIdx[u]++);
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[++top] = v;
                        }
                    } else {
                        order[orderIdx++] = u;
                        top--;
                    }
                }
            }
        }

        Arrays.fill(visited, false);
        int groups = 0;

        for (int i = n - 1; i >= 0; i--) {
            int start = order[i];
            if (!visited[start]) {
                groups++;
                int top = 0;
                stack[top] = start;
                visited[start] = true;

                while (top >= 0) {
                    int u = stack[top--];
                    for (int v : revAdj.get(u)) {
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[++top] = v;
                        }
                    }
                }
            }
        }

        return groups;
    }
}
