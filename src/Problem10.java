import java.util.ArrayList;
import java.util.List;

public class Problem10 {
    private int idx;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        int[] state = new int[numCourses];
        int[] order = new int[numCourses];
        idx = numCourses - 1;

        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && !dfs(adj, state, order, i)) {
                return new int[0];
            }
        }

        return order;
    }

    private boolean dfs(List<List<Integer>> adj, int[] state, int[] order, int course) {
        state[course] = 1;
        for (int next : adj.get(course)) {
            if (state[next] == 1) {
                return false;
            }
            if (state[next] == 0 && !dfs(adj, state, order, next)) {
                return false;
            }
        }
        state[course] = 2;
        order[idx--] = course;
        return true;
    }
}
