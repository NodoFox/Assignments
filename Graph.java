import java.util.*;
public class Graph{
    private Node[][] g = new Node[6][6];
    private char[][] matrix;
    //NODE CLASS
    public class Node{
        private char color;
        private List<Node> neighbors;
        
        //Getters
        public char getColor(){
            return this.color;
        }
        public List<Node> getNeighbors(){
            return this.neighbors;
        }
        public String toString(){
            return "--"+this.color+"--";
        }
    }
    
    public void setMatrix(char[][] matrix){
        this.matrix = matrix;
    }
    public char[][] getMatrix(){
        return matrix;
    }
    //RETURNING THE GRAPH
    public Node[][] getGraph(){
        return this.g;
    }
    
    //PRINTING THE WHOLE GRAPH
    public void printGraph(){
        for(Node[] each: g){
            for(Node eachElement: each){
                
                System.out.print(eachElement);
            }
            System.out.println("");
        }
    }
    
    //BUILDING THE GRAPH, GIVEN THE INPUTARRAY
    public void buildGraph(char[][] inputGraph){
        setMatrix(inputGraph);
        for(int i = 0 ; i < 6; i++){
            for(int j = 0; j < 6; j++){
                g[i][j] = new Node();
                g[i][j].color = inputGraph[i][j];
            }
        }
    }
}
