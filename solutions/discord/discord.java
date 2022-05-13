// Gabriel Pita
// 9/09/2021
// Solution to 2021 UCF Local Contest Problem: Discord Daisy Chain

import java.util.*;

public class discord {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int numChannels = in.nextInt();
        // Initialize adjacency list graph. The graph will be channels with edges between them. An edge exists from
        // channel A to channel B if there exists a bot in A that sends messages to channel B
        ArrayList<Integer>[] graph = new ArrayList[numChannels];
        for(int i =0; i < graph.length; i++){
            graph[i] = new ArrayList<Integer>();
        }

        int numBots = in.nextInt();

        for(int i=0; i < numBots; i++){
            // Subtracting 1 to convert to 0-based indices
            int listeningToChannel = in.nextInt() - 1;
            int numChannelsMessaged = in.nextInt();
            for(int j =0; j < numChannelsMessaged; j++){
                int channelId = in.nextInt() - 1;
                // Add edge to graph
                graph[listeningToChannel].add(channelId);
            }
        }

        // In a strongly connected component every node can reach every other node. Each component can be condensed into
        // a "meta" node. These meta nodes can be connected with edges and form a directed acyclic graph (DAG). For this
        // problem working with a DAG simplifies the problem significantly. A node N that can reach all other incoming
        // nodes in a DAG can not have any in-coming edges. If some edge from node P to node N exists then there is
        // no path from N to P since that would be a cycle in a DAG. Finding the component in the meta graph is then
        // a matter of checking for in-coming edges, with the caveat that if multiple meta nodes exist with in-degree 0
        // then no node can reach every other node.
        TarjanStronglyConnectedComponents stronglyConnectedComponents = new TarjanStronglyConnectedComponents(graph);
        ArrayList<Integer>[] metaGraph = stronglyConnectedComponents.buildMetaGraph();
        boolean[] hasInDegreeZeroInMetaGraph = new boolean[metaGraph.length];
        Arrays.fill(hasInDegreeZeroInMetaGraph, true);
        for(ArrayList<Integer> edgeList : metaGraph){
            for(int destinationMetaNode : edgeList){
                hasInDegreeZeroInMetaGraph[destinationMetaNode] = false;
            }
        }

        boolean isPossible = true;
        int potentialSourceComponent = -1;
        for(int i =0; i < hasInDegreeZeroInMetaGraph.length; i++){
            if(hasInDegreeZeroInMetaGraph[i]){
                if(potentialSourceComponent != -1){
                    // potentialSourceComponent was already set so multiple meta nodes exist with in-degree 0 and no
                    // individual node can reach every other node.
                    isPossible = false;
                }
                potentialSourceComponent = i;
            }
        }
        if(!isPossible){
            System.out.println(0);
        }
        else{
            int result =0;
            for(int i =0; i < numChannels; i++){
                // The source component that can reach all other nodes is identified. Loop through and count all the
                // nodes (channels) in that component.
                if(stronglyConnectedComponents.component[i] == potentialSourceComponent) result++;
            }
            System.out.println(result);
        }
    }

    public static class TarjanStronglyConnectedComponents{
        public ArrayList<Integer>[] adj;
        public int index, color;
        public int[] lowLink, visited, component;
        public ArrayDeque<Integer> stack;

        public TarjanStronglyConnectedComponents(ArrayList<Integer>[] graph){
            adj = graph;
            index = color = 0;
            lowLink = new int[adj.length]; component = new int[adj.length];
            visited = new int[adj.length]; // -1: in stack; -2: done
            stack = new ArrayDeque<Integer>();
            for(int i = 0; i < visited.length; i++)
                if(visited[i] == 0)
                    dfs(i);
        }

        public void dfs(int node) {
            int currentIndex = index++;
            lowLink[node] = currentIndex;
            visited[node] = -1;
            stack.add(node);
            for(int next : adj[node])
                if(visited[next] != -2) {
                    if(visited[next] == 0)
                        dfs(next);
                    lowLink[node] = Math.min(lowLink[node], lowLink[next]);
                }
            if(lowLink[node] == currentIndex) {
                int current = stack.removeLast();
                while(true) {
                    visited[current] = -2;
                    component[current] = color;
                    if(current == node) break;
                    current = stack.removeLast();
                }
                color++;
            }
        }

        public ArrayList<Integer>[] buildMetaGraph() {
            ArrayList<Integer>[] meta = new ArrayList[color];
            for(int i = 0; i < meta.length; i++)
                meta[i] = new ArrayList<Integer>();

            for(int i = 0; i < adj.length; i++) {
                for(int j : adj[i]) {
                    if(component[i] != component[j])
                        meta[component[i]].add(component[j]);
                }
            }
            return meta;
        }
    }
}
