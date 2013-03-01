import java.util.*;
import java.util.List;
public class AStar{
    private final Integer SIZE = 12;
    private final Integer T = 5;
    private Movie[] movie = new Movie[SIZE];
        private Integer[][] input ={{1,0,1,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,1,0,0,0,1,0,0,0,0},
            {0,0,1,0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,1,0,0,0},
            {0,0,0,1,0,0,1,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,1,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0,0,0,0},
            {0,1,0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,1,0,0,0},
            {0,0,0,1,0,0,1,0,0,0,0,0},
            {1,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,1,0,0},
            {0,1,0,0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,1,0,0,0},
            {0,0,1,0,0,1,0,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,1,0,0,0,0},
            {1,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,1,0,0},
            {1,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,1,0},
            {1,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,1},
            {0,0,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,0,1,0,1}};
    private Integer[] heuristic = {12,10,9,10,7,6,7,6,4,4,3,0};
    public class Movie{        
        private Integer heu=0;        
        private Integer pathCost = 5000;
        private Integer movieNum;
        private Movie parent = null;
        private Set<Movie> neighbors;
        private Boolean addFlag = false;
        private Boolean flag = false;
        
        public Movie(Integer i, Integer h){
            neighbors = new LinkedHashSet<Movie>();
            movieNum = i;
            heu = h;  
        }
        public String toString(){
            return "m"+movieNum;//+","+"PathCost:"+pathCost+", Heu="+heu+")";//"("+sim+","+heu+")"+"";
        }
        public boolean equals(Object o){
            if(o instanceof Movie){
                Movie c = (Movie) o;
                return movieNum.equals(c.movieNum);
            }
            return false;                
        }
    }
    public class MyComparator implements Comparator<Movie>{
        public int compare(Movie a, Movie b){
            if(a.heu > b.heu)
                return 1;
            if(a.heu == b.heu)
                return 0;
            else
                return -1;
        }
    }
    public class MyComparator2 implements Comparator<Movie>{
        public int compare(Movie a, Movie b){
            if((a.pathCost + a.heu) > (b.pathCost + b.heu))
                return 1;
            if((a.pathCost + a.heu) == (b.pathCost + b.heu))
                return 0;
            else
                return -1;
        }
    }
    
    public void buildGraph(){
        for(int i = 0; i<12; i++)
            movie[i] = new Movie(i+1,heuristic[i]);
        for(int i = 0; i<30; i++){
            int index1 = 0;
            int index2 = 0;
            int j=0;
            
            for(j=0; j<SIZE; j++){
                if(input[i][j] == 1){
                    index1 = j;
                    break;
                }
            }
            //System.out.print("User:"+(i+1)+"Index1 : "+index1+"("+j+")-");
            j++;            
            while(j<SIZE){
                if(input[i][j] == 1){
                    index2 = j;
                    break;
                }
                j++;
            }
            //System.out.println("Index2: "+index2+"("+j+")");
            if(j<=SIZE && index2>0){
            movie[index1].neighbors.add(movie[index2]);            
            movie[index2].neighbors.add(movie[index1]);
            //System.out.print("Added:"+index1+"-"+index2+"\n");
            }
        }
    }
    public void greedy(){
        LinkedList<Movie> q = new LinkedList<Movie>();
        LinkedList<Movie> path = new LinkedList<Movie>();
        LinkedList<Movie> traversal = new LinkedList<Movie>();
        Integer dS = 0;
        MyComparator comp = new MyComparator();
        q.add(movie[0]);
        movie[0].addFlag = true;
        while(q!=null){
            Movie temp = q.removeFirst();
            temp.flag = true;
            Set<Movie> s = temp.neighbors;
            for(Movie each: s){
                if(each.addFlag!=true){
                    each.addFlag=true;
                    each.parent = temp;
                    q.add(each);                    
                    Collections.sort(q,comp);
                    
                }                
            }
            
           traversal.add(temp);
            if(temp.movieNum == 12)
                break;
            Movie first = q.peek();
            dS = dS + computeDS(temp.movieNum-1, q.peek().movieNum-1);
           // System.out.println(dS + ":between: "+temp+" and "+q.peek());
        }
        System.out.print("\nGREEDY SEARCH\n=================");
        System.out.print("\n---------------\nTraversal\n---------------\n"+traversal.removeFirst()); 
        for(Movie each: traversal)
            System.out.print(","+each);
        
        System.out.print("\nDissimalarity: "+dS);
        Movie temp = movie[11];
        path.add(temp);
        while(temp.parent!=null){
            path.addFirst(temp.parent);
            temp = temp.parent;
        }
        
        System.out.print("\n---------------\nPath\n---------------\n"+path.removeFirst()); 
        for(Movie each: path)
            System.out.print(","+each);
    }
    public int productResult(Integer x, Integer y){
        int sum = 0;
        for(int i = 0; i<30 ; i++){        
            sum = sum + (input[i][x] *input[i][y]);
        }
        return sum; 
    }
    public int computeDS(Integer x, Integer y){
        Integer sum = 0;
        int Vm = productResult(x,y);
        return (((x+y+2)%T+1)*(T-Vm));
    }
    public void Asearch(){
        LinkedList<Movie> q = new LinkedList<Movie>();
        LinkedList<Movie> path = new LinkedList<Movie>();
        LinkedList<Movie> traversal = new LinkedList<Movie>();
        MyComparator2 comp = new MyComparator2();
        q.add(movie[0]);
        movie[0].pathCost = 0;
        while(q!=null){
            Movie temp = q.removeFirst();
            temp.flag = true;
            Set<Movie> s = temp.neighbors;
            for(Movie each: s){
                int tempDS = computeDS(temp.movieNum-1, each.movieNum-1);
                if((each.flag==false) && (tempDS + temp.pathCost < each.pathCost)){
                    q.remove(new Movie(each.movieNum, each.heu));
                    //System.out.println(temp+"AND"+each);
                    q.add(each);
                    each.parent = temp;
                    each.pathCost = temp.pathCost + tempDS; 
                    //System.out.println("NEW:"+temp+"AND"+each+"\nYOUR QUEUE IS:"+q);                  
                    //q.remove(new Movie(each.movieNum, each.heu));
                }
            }
            //System.out.println("Traversal"); // Printing the Traversal
            traversal.add(temp);
            if(temp.movieNum ==12)
                break;
            //System.out.println("BEFORE SORTING:"+q);
            Collections.sort(q,comp);
            //+"\nSORTED QUEUE IS: "+q); 
                    
        }
        System.out.print("\n=================\nA* SEARCH\n=================");
        System.out.print("\n---------------\nTraversal\n---------------\n"
            +traversal.removeFirst()); 
        for(Movie each: traversal)
            System.out.print(","+each);
              
        Movie temp = movie[11];
        path.add(temp);
        while(temp.parent!=null){
            path.addFirst(temp.parent);
            temp = temp.parent;
        }
        
        System.out.print("\nDissimilarity: "+movie[11].pathCost);
        System.out.print("\n---------------\nPath\n---------------\n"+path.removeFirst()); 
        
        for(Movie each: path)
            System.out.print(","+each);
        
    }
    public void go(){
        buildGraph();
        //printGraph();
        greedy();
        buildGraph();
        Asearch();
        //printGraph();
    }
    public static void main(String[] args){
        AStar start = new AStar();
        start.go();
        System.out.println("");    
    }
}
