import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class HamiltonianCycle {
		private Graph graph;

		private int[] finalSolution;
		private ExecutorService service;
		private AtomicBoolean found;

		public HamiltonianCycle(Graph graph) {
				this.graph = graph;
		}

		public boolean findHamCycle(int nrThreads) throws ExecutionException, InterruptedException {
				int[] path = new int[graph.getVertexNr()];
				found = new AtomicBoolean(false);
				for (int i = 0; i < graph.getVertexNr(); i++) {
						path[i] = -1;
				}
				service = Executors.newFixedThreadPool(nrThreads);
				//put the first vertex in the path for start
				path[0] = graph.getVetices().get(0);
				if (!hamiltonianCycleUtil(1, nrThreads, path)) {
						return false;
				}

				service.shutdownNow();
				printPath(finalSolution);
				return true;
		}

		/**
		 * Recursive function to solve the hamiltonian cycle problem
		 *
		 * @param pos - to start
		 * @return
		 */
		private boolean hamiltonianCycleUtil(int pos, int nrThreads, int[] pathParam) throws ExecutionException,
				InterruptedException {
				if (found.equals(new AtomicBoolean(true))) {
						finalSolution = Arrays.copyOf(pathParam, graph.getVertexNr());
						return true;
				}
				//check if all vertices are included in the Ham Cycle
				if (pos == graph.getVertexNr()) {
						StringBuilder s = new StringBuilder("sol : ");
						for (int i = 0; i < pos; i++) {
								s.append(" ").append(pathParam[i]).append(" ");
						}
						System.out.println(s.toString());
						//and if there is an edge from the last included vertex to the first included vertex
						if (graph.hasEdge(pathParam[pos - 1], pathParam[0])) {
								System.out.println("here");
								finalSolution = Arrays.copyOf(pathParam, graph.getVertexNr());
								printPath(pathParam);
								return true;
						} else {
								return false;
						}
				}

				if (nrThreads > 1) {
						//try diff vertices as a next candidate in Ham Cycle
						//the first was included as a starting point already
						List<Future<Boolean>> resultList = new ArrayList<>();
						for (Integer v : graph.getVetices()) {
								if (canAddToPath(pos, v, pathParam)) {
										pathParam[pos] = v;

										StringBuilder s = new StringBuilder("sol : ");
										for (int i = 0; i < pos; i++) {
												s.append(" ").append(pathParam[i]).append(" ");
										}
										System.out.println(s.toString());

										int[] copyPath = Arrays.copyOf(pathParam, graph.getVertexNr());
										resultList.add(service.submit(() -> hamiltonianCycleUtil(pos + 1, nrThreads / graph.getVertexNr(),
												copyPath)));
								}
						}

						for (Future<Boolean> f : resultList) {
								Boolean res = f.get();
								if (res) {
										found = new AtomicBoolean(true);
										finalSolution = Arrays.copyOf(pathParam, graph.getVertexNr());
										return true;
								}
						}
				} else {
						for (Integer v : graph.getVetices()) {
								if (canAddToPath(pos, v, pathParam)) {

										StringBuilder s = new StringBuilder("sol else: ");
										for (int i = 0; i < pos; i++) {
												s.append(" ").append(pathParam[i]).append(" ");
										}
										System.out.println(s.toString());

										pathParam[pos] = v;
										if (hamiltonianCycleUtil(pos + 1, 1, pathParam)) {
												found = new AtomicBoolean(true);
												finalSolution = Arrays.copyOf(pathParam, graph.getVertexNr());
												return true;
										}
								}
						}
				}

				return false;
		}

		private boolean canAddToPath(int pos, int current, int[] path) {
				/* Check if this vertex is an adjacent vertex of the previously added vertex. */
				if (!graph.hasEdge(path[pos - 1], current)) {
						return false;
				}
				//check if this vertex has already been included in the path
				for (int i = 0; i < pos; i++) {
						if (path[i] == current) {
								return false;
						}
				}
				return true;
		}

		private void printPath(int[] path) {
				StringBuilder str = new StringBuilder("Solution exists - the hamiltonian cycle is: ");
				for (int i = 0; i < graph.getVertexNr(); i++) {
						str.append(" ").append(path[i]).append(" ");
				}
				str.append(" ").append(path[0]).append(" ");
				System.out.println(str.toString());
		}
}
