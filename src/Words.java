import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Words {

    public static String toKeyword(String s,String t){
        int slength = s.length();
        int tlength = t.length();
        int i,j;
        String key="";
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                key="";
                int si=i,tj=j;
                if(i>=slength||j>=tlength)
                    return null;
                while(si<slength&&tj<tlength){
                    if(s.charAt(si)==t.charAt(tj)) {
                        key = key + s.charAt(si);
                        si++;
                        tj++;
                    }
                    else
                        break;
                }
                if(key.length()>=3&&slength-si<=3&&tlength-tj<=3) {
                    for(int beg=0;beg<Math.max(i,j);beg++)
                        key='*'+key;
                    for(int beg=0;beg<Math.max(slength-si,tlength-tj);beg++)
                        key=key+'*';
                    return key;
                }
            }
        }
        return null;
    }

    public static HashMap<String,Integer> getWords(HashMap<String,Integer> h,String in){
        int i = 0;
        String word;
        char[] input = in.toCharArray();
        while(i<in.length()){
            word="";
            while(Character.isLetter(input[i])){
                word=word+input[i];
                i++;
            }
            if(word.length()>=3) {
                word=word.toLowerCase();
                Iterator it = h.entrySet().iterator();
                boolean newEntry = true;
                while (it.hasNext()){
                    HashMap.Entry oldEntry = (Map.Entry)it.next();
                    String oldWord = oldEntry.getKey().toString();
                    String newWord = toKeyword(word,oldWord);
                    if(newWord!=null){
                        h.put(newWord,h.get(oldWord)+1);
                        h.remove(oldWord);
                        newEntry=false;
                        System.out.println(newWord);
                        break;
                    }

                }

                if(newEntry){
                    h.put(word,1);
                }
            }
            else{
                i++;
            }
        }
        return h;
    }

    public static void showDistribution(HashMap<String,Integer> h){
        int overall=0;
        for(Map.Entry<String,Integer> entry :h.entrySet()){
            overall+=entry.getValue();
        }
        for(Map.Entry<String,Integer> entry :h.entrySet()){
            if(entry.getValue()>5)
                System.out.println(entry.getKey()+":  "+entry.getValue()+" / "+ (double)entry.getValue()*100/overall+"%");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String,Integer> words = new HashMap<String,Integer>();
        FileInputStream file = new FileInputStream("text.txt");
        Scanner sc = new Scanner(file);
        String input = "";
        while(sc.hasNextLine()){
            input = input + sc.nextLine()+" ";
        }
        words = getWords(words,input);
        showDistribution(words);
    }
}
