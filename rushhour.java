import java.io.*;
import java.util.*;
public class rushhour{
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
      String level = ".........13CXX.13CDD.13..EFF...EHH..";
      printBoard(level);
      if(level.length()!=36){
         System.out.println("Try inputting a different level!");
         return;
      }
      boardmatrix = convertString(level);
      readBoard(boardmatrix);
      System.out.println();
      printBoard(board);
      Board solution = solve(board);
      printPath(solution);    
   }
   public static void printPath(Board b){
      for(int x = 0; x < b.path.length(); x+=64){
         printBoard(convertString(b.path.substring(x,x+64)));
      }
   }
   public static void readBoard(String[][] state){
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
   public static String[][] convertString(String s){
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
   public static String convertBoard(String[] s){
      String out = "";
      for(int x = 0; x < s.length; x++){
         out+=s[x];
      }
      return out;
   }
   public static void printBoard(String s){
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
   public static void printBoard(String[][] s){
      for(int x = 0; x < s.length; x++){
         for(int y = 0; y < s[0].length; y++){
            System.out.print(s[x][y] + " ");
         }
         System.out.println();
      }
      System.out.println();
   }
   public static int getSpaces(String state, int index, String dir){
      int spacesMoved = amountToMove.get(dir);
      int out = 0;
      int curindex = index+spacesMoved;
      while((curindex >= 0 && curindex < 64) && state.substring(curindex,curindex+1).equals(".")){
         out++;
         curindex+=spacesMoved;
      }
      return out;
   }
   public static ArrayList<String> getChildren(String state){
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
   public static String move(String state, int index, String dir, int d){
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
   public static boolean solved(String state){
      if(state.substring(29,31).equals("XX"))
         return true;
      return false;
   }
   public static Board solve(String state){
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
}
class Board{
   public int moves;
   public String state,path;
   public Board(String s,String p,int x){
      state = s;
      moves = x;
      path = p;
   }
}