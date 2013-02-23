import java.util.*;
public class AStar{
    private final Integer SIZE = 12;
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
            {0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,0,1,0,1}};
    public class Movie{
        private Integer sim=0;
        private Integer heu=0;
        private Integer movieNum;
        private Set<Movie> neighbors;
        
        public Movie(Integer i){
            neighbors = new LinkedHashSet<Movie>();
            movieNum = i;  
        }
        public String toString(){
            return movieNum+"";//"("+sim+","+heu+")"+"";
        }
    }
    public void printGraph(){
        for(Movie each: movie)
            System.out.println(each+"->"+each.neighbors);
    }
    public void buildGraph(){
        for(int i = 0; i<12; i++)
            movie[i] = new Movie(i+1);
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
            System.out.print("User:"+(i+1)+"Index1 : "+index1+"("+j+")-");
            j++;
            
            while(j<SIZE){
                if(input[i][j] == 1){
                    index2 = j;
                    break;
                }
                j++;
            }
            System.out.println("Index2: "+index2+"("+j+")");
            if(j<=SIZE && index2>0){
            movie[index1].neighbors.add(movie[index2]);
            
            movie[index2].neighbors.add(movie[index1]);
            System.out.print("Added:"+index1+"-"+index2+"\n");
            }
        }
    }
    public void go(){
        buildGraph();
        printGraph();
    }
    public static void main(String[] args){
        AStar start = new AStar();
        start.go();    
    }
}
