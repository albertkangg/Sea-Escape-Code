import java.io.*;
import java.util.*;
public class turtlefreeplay{
   public static String board = "";
   public static HashSet<String> odds = new HashSet<String>();//up-down
   public static HashSet<String> evens = new HashSet<String>();//left-right
   public static HashSet<String> piece2 = new HashSet<String>();//string
   public static HashSet<String> piece3 = new HashSet<String>();//numbers
   public static String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
   public static Map<String,Integer> amountToMove = new HashMap<String,Integer>();
   public static void main(String[] args) throws IOException{
      for(int x = 1; x < symbols.length(); x+=2){
         odds.add(symbols.substring(x-1,x));
         evens.add(symbols.substring(x,x+1));
         if(x>26){
            piece3.add(symbols.substring(x,x+1));
            piece3.add(symbols.substring(x-1,x));
         }
         else{
            piece2.add(symbols.substring(x,x+1));
            piece2.add(symbols.substring(x-1,x));
         }
      }
      amountToMove.put("U",-8);
      amountToMove.put("D",8);
      amountToMove.put("R",1);
      amountToMove.put("L",-1);
      String[][] boardmatrix = new String[6][6];
      ArrayList<String> levels = generateRandomLevel();
//       for(int x = 0; x < levels.size(); x++){ //solves each level
//          board = "";
//          String curlevel = levels.get(x);
//          boardmatrix = convertString(curlevel);
//          readBoard(boardmatrix);
//          printBoard(board);
//          Board solution = solve(board);
//       }
   }
   public static void printPath(Board b){ //prints the path
      for(int x = 0; x < b.path.length(); x+=64){
         printBoard(convertString(b.path.substring(x,x+64)));
      }
   }
   public static void readBoard(String[][] state){//creates a board (theres a global varaible called board)
      board += "????????";
      for(int y = 0; y < 6; y++){
         board+="?";
         for(int x = 0; x < 6; x++){
            board+=state[y][x];
         }
         board+="?";
      }
      board+="????????";      
   }
   public static String[][] convertString(String s){//converts a board to string
      String[][] out = new String[6][6];
      int skips = 0;
      for(int x = 0; x < s.length(); x++){
         String cur = s.substring(x,x+1);
         if(cur.equals("?")){
            skips+=1;
            continue;
         }
         out[(int)(x-skips)/6][(x-skips)%6] = cur;
      }
      return out;
   }
   public static String convertBoard(String[] s){//converts a board to string
      String out = "";
      for(int x = 0; x < s.length; x++){
         out+=s[x];
      }
      return out;
   }
   public static void printBoard(String s){//prints board neatly (for debugging), takes in a string
      int l = s.length();
      int side = (int)Math.pow(l,0.5);
      for(int x = 0; x < side; x++){
         for(int y = 0; y < side; y++){
            System.out.print(s.substring((x*side+y),(x*side+y+1))+" ");
         }
         System.out.println();
      }
      System.out.println();
   }
   public static void printBoard(String[][] s){//prints board neatly (for debugging really), takes in matrix
      for(int x = 0; x < s.length; x++){
         for(int y = 0; y < s[0].length; y++){
            System.out.print(s[x][y] + " ");
         }
         System.out.println();
      }
      System.out.println();
   }
   public static int getSpaces(String state, int index, String dir){//number of blank spaces the piece can move
      int spacesMoved = amountToMove.get(dir);
      int out = 0;
      int curindex = index+spacesMoved;
      while((curindex >= 0 && curindex < 64) && state.substring(curindex,curindex+1).equals(".")){
         out++;
         curindex+=spacesMoved;
      }
      return out;
   }
   public static ArrayList<String> getChildren(String state){//get children
      ArrayList<String> children = new ArrayList<>();
      for(int i = 0; i < state.length(); i++){
         String curindex = state.substring(i,i+1);
         if(evens.contains(curindex)){
            for(int x = 1; x <= getSpaces(state,i,"L"); x++)
               children.add(move(state,i,"L",x));
            for(int x = 1; x <= getSpaces(state,i,"R"); x++)
               children.add(move(state,i,"R",x));
         }
         if(odds.contains(curindex)){
            for(int x = 1; x <= getSpaces(state,i,"U"); x++)
               children.add(move(state,i,"U",x));
            for(int x = 1; x <= getSpaces(state,i,"D"); x++)
               children.add(move(state,i,"D",x));
         }
      }
      return children;
   }
   public static String move(String state, int index, String dir, int d){//moving piece
      String[] curboard = state.split("");
      String curpiece = curboard[index];
      int spacesMoved = amountToMove.get(dir);
      curboard[index] = ".";
      curboard[index - spacesMoved] = ".";
   //       System.out.println(curpiece);
      if(piece3.contains(curpiece))
         curboard[index-2*spacesMoved] = ".";
      curboard[index + spacesMoved * d] = curpiece;
      curboard[index + spacesMoved * (d-1)] = curpiece;
      if(piece3.contains(curpiece))
         curboard[index + spacesMoved * (d-2)] = curpiece;
      return convertBoard(curboard);
      
   }
   public static boolean solved(String state){//goalstate check
      if(state.substring(29,31).equals("XX"))
         return true;
      return false;
   }
   public static Board solve(String state){ //code to solve, all methods here are just for solving (this is the BFS)
      HashSet<String> visited = new HashSet<String>();
      Queue<Board> q = new LinkedList<Board>();
      q.add(new Board(state,state,0));
      while(!q.isEmpty()){
         Board curboard = q.remove();
         String curstate = curboard.state;
      //          printBoard(curstate);
         String curpath = curboard.path;
         int movenumber = curboard.moves;
         if(solved(curstate))
            return curboard;
         for(String child: getChildren(curstate)){
            if(!visited.contains(child)){
               visited.add(child);
               q.add(new Board(child,curpath+child,movenumber+1));
            }
         }
      }
      return null;
   }
   public static ArrayList<String> generateRandomLevel(){ //creates a list of levels and shuffles it
      ArrayList<String> possiblelevels = new ArrayList<String>();
      possiblelevels.add("000..1..A..1XXA..1C.1.BBC...E.888.E.");//1
      possiblelevels.add("........A35.XXA35.BBC35..EC....EDD..");//2
      possiblelevels.add("ABBDD.A.C...XXC57....57.44457.......");//3
      possiblelevels.add(".........13AXX.13ABB.13..CDD...CFF..");//4
      possiblelevels.add("..1.BB..1..3XX1..3.A2223.ADDC.....C.");//5
      possiblelevels.add("..13BB..13A.XX13A..C..E..CBBE.......");//6
      possiblelevels.add("A..C..A..C..XX.E.7222E.I.GBB.I.GDD..");//7
      possiblelevels.add(".......000.AXXC5.A..C5BB4445.E.....E");//8
      possiblelevels.add("2223..CDD3FFCXX3.1C.ABB1..A.E1444.E.");//9
      possiblelevels.add("A222..A4443..5XX3..5BB3..5.CDD000C..");//10
      possiblelevels.add("......C..EG7CXXEGIFFDDI..KA666.KABB.");//11
      possiblelevels.add("E.BB..E222GIXX.7GIDD.7IA.C.7IA.C888.");//12
      possiblelevels.add(".BBA...DDA3..5XX3..5FF37.5C..7HHCJJ7");//13
      possiblelevels.add("BBA..CE.ADDECXXIOKFF.IOKHH..M.JJLLM.");//14
      possiblelevels.add("BBDDAC..E.ACXXE7GI4447GI...7JJ......");//15
      possiblelevels.add("..AC....AC.EXXGI1EBBGI1.KMDD1.KM....");//16
   //       possiblelevels.add("");//17
   //       possiblelevels.add("");//18
   //       possiblelevels.add("");//19
   //       possiblelevels.add("");//20
      Collections.shuffle(possiblelevels);
      return possiblelevels;
      // just copy levels and math.random it out
   }
   public static String suggestNextMove(String state){//suggestsnextmove
      Board solution = solve(state);
      String start = solution.path.substring(0,64);
      String suggest = solution.path.substring(64,128);
      String[][] startboard = convertString(start);
      String[][] suggestboard = convertString(suggest);
      return findDifference(startboard,suggestboard);
   }
   public static String findDifference(String[][] startboard, String[][] suggestboard){//suggests next move based on how many spaces to move
      for(int x = 0; x < startboard.length; x++){
         for(int y = 0; y < startboard[0].length; y++){
            if(!startboard[x][y].equals(suggestboard[x][y])){
               String piece;
               int direction = 1; //leftorup = -1, downorright = 1
               if(!startboard[x][y].equals(".")){
                  piece = startboard[x][y];
               }
               else{
                  piece = suggestboard[x][y];
                  direction = -1;
               }
               if(odds.contains(piece)){//up-down
                  int xindex1 = x+(-1*direction);
                  while(!startboard[xindex1][y].equals(piece))
                     xindex1+=(-1*direction);
                  int xindex2 = xindex1+(1*direction);
                  while(!suggestboard[xindex2][y].equals(piece))
                     xindex2 += (1*direction);
                  if(direction == -1){
                     return "Try moving the piece " + x + " space(s) away from the top and " + y + " space(s) from the left " + Math.abs(xindex2-xindex1) + " space(s) up.";
                  }
                  else{
                     return "Try moving the piece " + x + " space(s) away from the top and " + y + " space(s) from the left " + Math.abs(xindex2-xindex1) + " space(s) down.";
                  }
               }
               else{//left-right
                  int yindex1 = y+(-1*direction);
                  while(!startboard[x][yindex1].equals(piece))
                     yindex1+=(-1*direction);
                  int yindex2 = yindex1+(1*direction);
                  while(!suggestboard[x][yindex2].equals(piece))
                     yindex2 += (1*direction);
                  if(direction == -1){
                     return "Try moving the piece " + x + " space(s) away from the top and " + y + " space(s) from the left " + Math.abs(yindex2-yindex1) + " space(s) left.";
                  }
                  else{
                     return "Try moving the piece " + x + " space(s) away from the top and " + y + " space(s) from the left " + Math.abs(yindex2-yindex1) + " space(s) right.";
                  }
               }
            }
         }
      }
      return "Try resetting the board";
   }
}
class Board{ //class to store stuff
   public int moves;
   public String state,path;
   public Board(String s,String p,int x){ //s is the curstate, p is just a long string of all the states added together, x is curmoves
      state = s;
      moves = x;
      path = p;
   }
}