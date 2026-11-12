import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] dep : dependencies) {
            adj.get(dep[0]).add(dep[1]);
        }

        int[] ids = new int[n];
        Arrays.fill(ids, -1);
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        int[] sccStack = new int[n];
        int sccTop = -1;
        int[] callStack = new int[n];
        int[] edgeIdx = new int[n];

        int timer = 0;
        int sccCount = 0;

        for (int i = 0; i < n; i++) {
            if (ids[i] == -1) {
                int callTop = 0;
                callStack[callTop] = i;
                ids[i] = low[i] = timer++;
                sccStack[++sccTop] = i;
                onStack[i] = true;

                while (callTop >= 0) {
                    int u = callStack[callTop];
                    List<Integer> neighbors = adj.get(u);
                    if (edgeIdx[u] < neighbors.size()) {
                        int v = neighbors.get(edgeIdx[u]++);
                        if (ids[v] == -1) {
                            ids[v] = low[v] = timer++;
                            sccStack[++sccTop] = v;
                            onStack[v] = true;
                            callStack[++callTop] = v;
                        } else if (onStack[v]) {
                            if (ids[v] < low[u]) {
                                low[u] = ids[v];
                            }
                        }
                    } else {
                        callTop--;
                        if (callTop >= 0) {
                            int parent = callStack[callTop];
                            if (low[u] < low[parent]) {
                                low[parent] = low[u];
                            }
                        }
                        if (low[u] == ids[u]) {
                            sccCount++;
                            while (true) {
                                int node = sccStack[sccTop--];
                                onStack[node] = false;
                                if (node == u) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return sccCount;
    }
}
