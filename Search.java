import java.util.*;

public class Search{

    private final Integer ROWS=10;
    private final Integer COLUMNS=7;
    private Node[][] graph = new Node[ROWS][COLUMNS];
    private Node[][] bfsGraph = new Node[ROWS][COLUMNS];
    private Integer[][] gM = new Integer[][]{{0,0,0,0,0,0,0},
              {0,12,16,20,19,18,0},
              {0,11,17,10,20,15,0},
              {0,17,9,14,13,19,0},
              {0,16,15,8,7,10,0},
              {0,7,15,17,6,8,0},
              {0,13,7,9,9,9,0},
              {0,12,6,13,15,14,0},
              {0,15,12,16,9,12,0},
              {0,0,0,0,0,0,0}};

    // NODE CLASS REPRESENTING EACH NODE IN THE GRAPH
    public class Node{
        private  Integer x;
        private  Integer y;
        private  Integer data;
        private Boolean flag=false;
        private Boolean addFlag=false;
        private Set<Node> neighbors;

        public Node(Integer x, Integer y, Integer data){
            this.x = x;
            this.y = y;
            this.data = data;
        }
        //SETTERS
        public void setX(Integer x){
            this.x = x;
        }
        public void setY(Integer y){
            this.y = y;
        }
        public void resetFlags(){
            flag = false;
            addFlag=false;
        }
        public void setFlag(){
            flag = true;
        }
        public void setAddFlag(){
            addFlag=true;
        }
        public void setNeighbor(Set<Node> neighbors){
            this.neighbors = neighbors;
        }

        //GETTERS
        public Integer getX(){
            return x;
        }
        public Integer getY(){
            return y;
        }
        public Integer getData(){
            return data;
        }
        public Boolean getFlag(){
            return flag;
        }
        public Boolean getAddFlag(){
            return addFlag;
        }
        public Set<Node> getNeighbors(){
            return neighbors;
        }

        @Override
        public String toString(){
            return data+"("+x+","+y+")"+flag+","+addFlag;
        }
    }

    // OVER_RIDDEN COMPARATOR FOR TREESET
    public class MyComparator implements Comparator<Node>{
        Node[][] tiedGraph;

        public MyComparator(Node[][] graph){
            this.tiedGraph = graph;
        }

        public int compare(Node a, Node b){
            if(a.data > b.data)
                return 1;            
            else
                return -1;
        }
    }

    // RESETTING THE GRAPH VISITED FLAGS
    public void resetGraph(Node[][] generic){
        for(Node[] outer: generic)
            for(Node each: outer){
                each.resetFlags();
            }         
    }

    // Building the Whole Graph
    public void buildGraph(){
        MyComparator comp = new MyComparator(graph);

        //Creating all nodes
        for(int i = 0 ; i<=9; i++)
            for(int j = 0; j<=6; j++){
                graph[i][j]= new Node(i,j,gM[i][j]);
                bfsGraph[i][j] = new Node(i,j,gM[i][j]);
            }
        //Adding their neighbors
        for(int i = 1 ; i<9; i++)
            for(int j = 1; j<6; j++){
                Set<Node> treeSet = new TreeSet<Node>(comp);
                Set<Node> linkedHash = new LinkedHashSet<Node>();
                if(gM[i+1][j]!=0)treeSet.add(graph[i+1][j]);
                if(gM[i][j-1]!=0)treeSet.add(graph[i][j-1]);
                if(gM[i-1][j]!=0)treeSet.add(graph[i-1][j]);
                if(gM[i][j+1]!=0)treeSet.add(graph[i][j+1]);
                graph[i][j].setNeighbor(treeSet);
                if(gM[i][j+1]!=0)linkedHash.add(bfsGraph[i][j+1]);
                if(gM[i-1][j]!=0)linkedHash.add(bfsGraph[i-1][j]);
                if(gM[i][j-1]!=0)linkedHash.add(bfsGraph[i][j-1]);
                if(gM[i+1][j]!=0)linkedHash.add(bfsGraph[i+1][j]);
                bfsGraph[i][j].setNeighbor(linkedHash);
            }
    }

    // BREADTH FIRST SEARCH
    public void bfs(int x1, int y1, int x2, int y2){
        Queue<Node> q = new LinkedList<Node>();
        Set<Node> s;
        Boolean reached;
        String[][] path = new String[ROWS][COLUMNS];
        q.add(graph[x1][y1]);
        graph[x1][y1].setAddFlag();
        path[x1][y1] = "("+y1+","+x1+")";
        System.out.print("\nTraversal Path\n");
        while(q!=null){
            Node temp = q.remove();
            s=temp.getNeighbors();
            //System.out.print(s);
            for(Node each:s){
                if(each.getFlag()==false && each.getAddFlag()==false){
                    q.add(each);
                    path[each.getX()][each.getY()]=path[temp.getX()][temp.getY()]
                            +","+"("+each.getY()+","+each.getX()+")";
                    each.setAddFlag();
                }                
            }
            temp.setFlag();
            System.out.print("("+temp.getY()+","+temp.getX()+")");
            //System.out.println(temp+"\n");
            if(temp.getX()==x2 && temp.getY()==y2) break;
            System.out.print(",");
        }
        System.out.print("\nStitching Curve\n");
        System.out.print(path[x2][y2]+"\n\n");
    }

    // DEPTH FIRST SEARCH
    public void dfs(int x1, int y1, int x2, int y2){
        Stack<Node> s = new Stack<Node>();
        Set<Node> set;
        String[][] path = new String[ROWS][COLUMNS];
        s.push(graph[x1][y1]);
        graph[x1][y1].setFlag();
        path[x1][y1] = "("+y1+","+x1+")";
        System.out.print("DFS \nTraversal Path\n");
        System.out.print("("+y1+","+x1+")");
        while(s!=null){
            Node temp = s.peek();
            set = temp.getNeighbors();
            Boolean flag=true;
            if(temp.getX()==8 && temp.getY()==4) break;

            for(Node each: set){
               if(each.getFlag()==true){
                    flag=true;
                    continue;
               }
               else{
                    each.setFlag();
                    s.push(each);
                    System.out.print(",("+each.getY()+","+each.getX()+")");
                    path[each.getX()][each.getY()]=path[temp.getX()][temp.getY()]
                            +","+"("+each.getY()+","+each.getX()+")";
                    flag=false;
                    break;
               }
            }
            if(flag==true){
               s.pop();
            }
        }
        System.out.print("\nStitching curve\n");
        System.out.print(path[x2][y2]+"\n\n");
    }
    public void go(){
        
        System.out.print("\nBFS");
        bfs(1,2,8,4);
        resetGraph(bfsGraph);
        resetGraph(graph);
        dfs(1,2,8,4);
    }
    // MAIN
    public static void main(String[] args){
        Search s = new Search();
        Integer x1 = 1, y1 = 2;
        Integer x2 = 8, y2 = 4;
        s.buildGraph();
        s.go();
        System.out.println("");
        //s.resetGraph();
    }
}

