import java.util.ArrayList;
import java.util.List;

public class Problem08 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        int[] state = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && hasCycle(adj, state, i)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(List<List<Integer>> adj, int[] state, int course) {
        state[course] = 1;
        for (int next : adj.get(course)) {
            if (state[next] == 1) {
                return true;
            }
            if (state[next] == 0 && hasCycle(adj, state, next)) {
                return true;
            }
        }
        state[course] = 2;
        return false;
    }
}
