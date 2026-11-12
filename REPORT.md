### DAA Assignment3 - Report and Defense (50 points)

Solutions and theoretical analysis for **Design and Analysis of Algorithms — Assignment 3 (Graph Algorithms)** implemented in Java.

| Problem | Source Files | Topic | Algorithm | Time | Space |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **01 & 02** | [`Problem01.java`](src/Problem01.java), [`Problem02.java`](src/Problem02.java) | Number of Provinces | BFS / DFS (Connected Components) | $O(n^2)$ | $O(n)$ |
| **03 & 04** | [`Problem03.java`](src/Problem03.java), [`Problem04.java`](src/Problem04.java) | Min Cost Spanning Tree | Prim's Algorithm (Dense MST) | $O(n^2)$ | $O(n)$ |
| **05 & 06** | [`Problem05.java`](src/Problem05.java), [`Problem06.java`](src/Problem06.java) | Software Dependency Cycles | Kosaraju's & Tarjan's SCC | $O(V + E)$ | $O(V + E)$ |
| **07 & 08** | [`Problem07.java`](src/Problem07.java), [`Problem08.java`](src/Problem08.java) | Course Schedule | Kahn's BFS & 3-State DFS | $O(V + E)$ | $O(V + E)$ |
| **09 & 10** | [`Problem09.java`](src/Problem09.java), [`Problem10.java`](src/Problem10.java) | Course Schedule II | Kahn's BFS & Post-Order DFS | $O(V + E)$ | $O(V + E)$ |

---

##### Problem 1 and 2 - Number of Provinces (10 points)

* **Files:** [`src/Problem01.java`](src/Problem01.java), [`src/Problem02.java`](src/Problem02.java)
* **Graph Modeling:**
  * **Vertices ($V$):** $n$ cities indexed from $0$ to $n - 1$.
  * **Edges ($E$):** Undirected edges represented by an $n \times n$ symmetric adjacency matrix (`isConnected[i][j] == 1`).
  * **Goal:** Count the number of **Connected Components**, where each maximal connected subgraph corresponds to one province.
* **Algorithm & Approach:**
  * Track visited cities using a `boolean[] visited` array of size $n$.
  * Iterate through all vertices $i \in [0, n - 1]$. Each time an unvisited vertex is found, increment `provinces` and traverse its entire connected component using **BFS** (`Problem01`) or **DFS** (`Problem02`).
* **Complexity:**
  * **Time:** $O(n^2)$ — Every cell in the $n \times n$ adjacency matrix is inspected once across all row scans.
  * **Space:** $O(n)$ — Used by the `visited` array and the recursion stack / BFS queue.
* **Defense Notes:**
  * Converting the input matrix into an adjacency list would already take $O(n^2)$ time and extra memory, so traversing the matrix directly is optimal.
  * Self-loops (`isConnected[i][i] == 1`) are safely skipped by the `!visited[neighbor]` check.

---

##### Problem 3 and 4 - Min Cost Spanning Tree (10 points)

* **Files:** [`src/Problem03.java`](src/Problem03.java), [`src/Problem04.java`](src/Problem04.java)
* **Graph Modeling:**
  * **Vertices ($V$):** $n$ points on a 2D coordinate plane.
  * **Edges ($E$):** Complete undirected weighted graph ($K_n$) with $\frac{n(n - 1)}{2}$ edges, where edge weight is the Manhattan distance $w(i, j) = |x_i - x_j| + |y_i - y_j|$.
  * **Goal:** Find a **Minimum Spanning Tree (MST)** connecting all points with minimum total weight.
* **Algorithm & Approach:**
  * Uses **Prim's Algorithm optimized for dense graphs** (without a priority queue or pre-built edge list).
  * Maintain `minDist[]` (initialized to $\infty$, `minDist[0] = 0`) and `visited[]`.
  * Over $n$ steps: linearly pick the unvisited vertex `curr` with minimum `minDist[curr]`, add its cost to `totalCost`, and relax `minDist[next]` for all unvisited vertices by computing Manhattan distance on the fly.
* **Complexity:**
  * **Time:** $O(n^2)$ — $n$ iterations, each performing two $O(n)$ linear scans (selecting minimum vertex and relaxing neighbors).
  * **Space:** $O(n)$ — Only two 1D arrays (`minDist` and `visited`) are allocated; no $O(n^2)$ edge list is stored.
* **Defense Notes:**
  * Because the graph is complete ($E = \Theta(n^2)$), Kruskal's or heap-based Prim's takes $O(n^2 \log n)$ time and $O(n^2)$ space. Array-based Prim's removes the $\log n$ factor and keeps memory linear.

---

##### Problem 5 and 6 - Software Dependency Cycles (10 points)

* **Files:** [`src/Problem05.java`](src/Problem05.java), [`src/Problem06.java`](src/Problem06.java)
* **Graph Modeling:**
  * **Vertices ($V$):** $n$ software modules ($0$ to $n - 1$).
  * **Edges ($E$):** Directed edges $a \to b$ indicating that module $a$ depends on module $b$.
  * **Goal:** Count the number of **Strongly Connected Components (SCCs)**, including isolated single-module components.
* **Algorithm & Approach:**
  * **Kosaraju's Algorithm (`Problem05.java`):**
    1. Run iterative DFS on the original graph `adj` to record vertices in post-order (`order[]`).
    2. Construct the transposed graph `revAdj` (reversing edges $a \to b$ into $b \to a$) and run iterative DFS in reverse finishing order (`n - 1` down to `0`). Each DFS tree forms one SCC.
  * **Tarjan's Algorithm (`Problem06.java`):**
    1. Run a single-pass iterative DFS tracking discovery timestamps (`ids[]`), lowest reachable ancestor (`low[]`), and active SCC membership (`sccStack[]`, `onStack[]`).
    2. When backtracking from $u$, if `low[u] == ids[u]`, pop nodes from `sccStack` down to $u$ to extract one complete SCC.
* **Complexity:**
  * **Time:** $O(V + E)$ — Both algorithms visit every vertex and directed edge a constant number of times.
  * **Space:** $O(V + E)$ — Adjacency lists and auxiliary vertex-indexed arrays.
* **Defense Notes:**
  * Since $n \le 10^5$, standard recursive DFS risks `StackOverflowError` on deep chains. Both solutions use **iterative DFS** backed by primitive `int[]` stacks and `edgeIdx[]` cursors.
  * Kosaraju is simpler to reason about via two passes, while Tarjan finds all SCCs in a single pass without building a reversed graph.

---

##### Problem 7 and 8 - Course Schedule (10 points)

* **Files:** [`src/Problem07.java`](src/Problem07.java), [`src/Problem08.java`](src/Problem08.java)
* **Graph Modeling:**
  * **Vertices ($V$):** `numCourses` courses ($0$ to `numCourses - 1`).
  * **Edges ($E$):** Directed edges $b_i \to a_i$ for each prerequisite pair $[a_i, b_i]$ (taking $b_i$ unlocks $a_i$).
  * **Goal:** Determine whether the directed graph is a **Directed Acyclic Graph (DAG)** (i.e., contains no cycles).
* **Algorithm & Approach:**
  * **Kahn's Algorithm — BFS (`Problem07.java`):** Compute `inDegree[]` for all nodes, enqueue vertices with `inDegree == 0`, and iteratively peel vertices while decrementing neighbors' in-degrees. All courses can be finished iff `completed == numCourses`.
  * **3-State DFS — Cycle Detection (`Problem08.java`):** Classify vertices into `0` (unvisited), `1` (visiting — on active recursion path), and `2` (visited — verified acyclic). Encountering a neighbor in state `1` indicates a back-edge (cycle).
* **Complexity:**
  * **Time:** $O(V + E)$ — Each course and prerequisite edge is processed once.
  * **Space:** $O(V + E)$ — Adjacency list plus $O(V)$ for `inDegree`/queue or `state`/recursion stack.
* **Defense Notes:**
  * Three states (`0, 1, 2`) are required instead of two in directed graphs so that diamond structures ($0 \to 1, 0 \to 2, 1 \to 3, 2 \to 3$) do not falsely trigger cycle detection when reaching node $3$ via a second valid path.

---

##### Problem 9 and 10 - Course Schedule II (10 points)

* **Files:** [`src/Problem09.java`](src/Problem09.java), [`src/Problem10.java`](src/Problem10.java)
* **Graph Modeling:**
  * **Vertices ($V$):** `numCourses` courses ($0$ to `numCourses - 1`).
  * **Edges ($E$):** Directed edges $b_i \to a_i$ for each prerequisite $[a_i, b_i]$.
  * **Goal:** Return a valid **Topological Ordering** of all vertices, or an empty array `new int[0]` if a cycle exists.
* **Algorithm & Approach:**
  * **Kahn's Algorithm — BFS Topological Sort (`Problem09.java`):** Extends Problem 07 by appending each dequeued zero-in-degree vertex into `order[idx++]`. Returns `order` if `idx == numCourses`, otherwise `new int[0]`.
  * **3-State DFS — Reverse Post-Order Topological Sort (`Problem10.java`):** Extends Problem 08 by writing each finished vertex (`state[course] = 2`) from the end of the array (`order[idx--] = course`). Since all dependent courses finish before their prerequisites, reverse post-order produces a valid topological sort.
* **Complexity:**
  * **Time:** $O(V + E)$ — Linear in vertices and edges.
  * **Space:** $O(V + E)$ — Adjacency list and $O(V)$ auxiliary arrays.
* **Defense Notes:**
  * Kahn's algorithm constructs the ordering forward from independent root courses (`inDegree == 0`), whereas DFS constructs it backward via post-order completion and uses a descending index pointer (`idx--`) to avoid reversing the result afterward.
