import java.util.*;
public class GoGame{
    
    private char[][] inputGraph =  {{'W','N','B','W','N','B'},
                                    {'N','W','B','N','B','N'},
                                    {'W','N','W','B','N','B'},
                                    {'N','B','B','B','W','B'},
                                    {'W','B','W','N','W','B'},
                                    {'W','N','W','B','W','W'}};
    
    private GameState gameRoot = new GameState();
    private int size = 0;
    
    // GAME STATE CLASS, THAT REPRESENTS EACH --gameNode-- IN GOGAME TREE
    public class GameState{
        private int alpha;
        private int beta;
        private Graph graph = new Graph();
        private List<GameState> children = new ArrayList<GameState>();
    }
    
    
    //reModel the inputGraph    
    public char[][] reModel(char[][] inputArray, int position, char replaceChar){
        int nCount = -1;
        for(int i  = 0 ; i<6; i++){
            for(int j= 0; j<6; j++){

                if(inputArray[i][j]=='N'){
                    nCount++;
                    if(position == nCount){
                        inputArray[i][j]=replaceChar;
                        return inputArray;
                    }
                    
                    
                }
                
            }
        }
        return inputArray;
    }
    // COPYING OF ARRAY
    public char[][] copyArray(char[][] source){
        char[][] dest = new char[6][6];
        for(int i = 0; i<6; i++)
            for(int j = 0; j<6; j++){
                dest[i][j] = source[i][j];
            }
        return dest;
    }
    // BUILDING THE GOGAME TREE
    public void buildGoGameTree(){
        GameState[] gameLevelZeroNode = new GameState[10];
        for(int i = 0; i<10; i++){
             gameLevelZeroNode[i] = new GameState();
             char[][] copyArray = copyArray(inputGraph);

             gameLevelZeroNode[i].graph.buildGraph(reModel(copyArray, i, 'B'));
             //System.out.println("Level 0:--- Node Number:"+ i);
             //gameLevelZeroNode[i].graph.printGraph();
             
            //Array of 9 gameState for each 10 parent elements.
            GameState[] gameLevelOneNode = new GameState[9];
            size++;
            for(int j = 0; j< 9; j++){
                gameLevelOneNode[j] = new GameState();
                copyArray = copyArray(gameLevelZeroNode[i].graph.getMatrix());
                gameLevelOneNode[j].graph.buildGraph(reModel(copyArray,j,'W'));
                //System.out.println("Level 1:--- Node Number:"+ j);
                //gameLevelOneNode[j].graph.printGraph();
                //Array of 8 gameState for each 9 parent elements.
                GameState[] gameLevelTwoNode = new GameState[8];
                size++;
                for(int k = 0; k<8; k++){
                    //Array of 7 gameState for each 8 parent elements.
                    gameLevelTwoNode[k] = new GameState();
                    copyArray = copyArray(gameLevelOneNode[j].graph.getMatrix());
                    gameLevelTwoNode[k].graph.buildGraph(reModel(copyArray,k,'B'));
                   // System.out.println("Level 2:--- Node Number:"+ k);
                   // gameLevelTwoNode[k].graph.printGraph();
                    GameState[] gameLevelThreeNode = new GameState[7];
                    size++;
                    for(int l =0 ; l<7; l++){
                        gameLevelThreeNode[l] = new GameState();
                        copyArray = copyArray(gameLevelTwoNode[k].graph.getMatrix());
                        gameLevelThreeNode[l].graph.buildGraph(reModel(copyArray,l,'W'));
                        //System.out.println("Level 3:--- Node Number:"+ l);
                        //gameLevelThreeNode[l].graph.printGraph();
                        gameLevelTwoNode[k].children.add(gameLevelThreeNode[l]);
                        size++;
                        
                    }
                    gameLevelOneNode[j].children.add(gameLevelTwoNode[k]);
                }
                gameLevelZeroNode[i].children.add(gameLevelOneNode[j]);
            }
            gameRoot.children.add(gameLevelZeroNode[i]);
        }
        
        
        System.out.println("Total Nodes: "+size);
    }
    
    // INIT
    public void go(){
        GameState gS = new GameState();
        gS.graph = new Graph();
        gS.graph.buildGraph(inputGraph);
        gS.graph.printGraph();
        buildGoGameTree();
        
    }  
    
    //MAIN METHOD 
    public static void main(String[] args){
        double startTime = System.currentTimeMillis();
        GoGame g = new GoGame();
        g.go();
        double endTime   = System.currentTimeMillis();
        double totalTime = endTime - startTime;
        System.out.println("Running Time: "+ totalTime/1000 + " seconds");
    }
}
