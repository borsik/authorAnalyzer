import java.io.*;
import java.util.*;

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
                        if (!oldWord.equals(newWord))
                            h.remove(oldWord);
                        newEntry=false;
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

    public static void showDistibution(HashMap<String,Integer> h){
        int overall=0;
        for(Map.Entry<String,Integer> entry :h.entrySet()){
            overall+=entry.getValue();
        }
        for(Map.Entry<String,Integer> entry :h.entrySet()){
            if(entry.getValue()>0)
                System.out.println(entry.getKey()+":  "+entry.getValue()+" / "+ (double)entry.getValue()*100/overall+"%");
        }
    }

    public static HashMap<String,Integer> analiseText(String text) throws FileNotFoundException {
        HashMap<String,Integer> words = new HashMap<String,Integer>();
        FileInputStream file = new FileInputStream(text+".txt");
        Scanner sc = new Scanner(file);
        String input = "";
        while(sc.hasNextLine()){
            input = input + sc.nextLine()+" ";
        }
        words = getWords(words,input);
        //showDistibution(words);
        return words;
    }

    public static void divideDostoevsky() throws IOException {
        FileInputStream file = new FileInputStream("dostoevsky.txt");
        Scanner sc = new Scanner(file);
        for(int i=0;i<10;i++){
            Writer wr = new FileWriter("dostoevsky_"+(i+1)+".txt");
            int numwords=0;
            while(numwords<20000) {
                if (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    for (int j = 1; j < line.length(); j++) {
                        if (!Character.isLetter(line.charAt(j)) && Character.isLetter(line.charAt(j - 1))) {
                            numwords++;
                            if (numwords >= 20000) {
                                line = line.substring(0, j);
                                break;
                            }
                        }
                    }

                    wr.write(line + '\n');
                }
                else {
                    System.out.println("Too short file to show correct result");
                    break;
                }
            }
            wr.close();
        }
    }

    public static void extractPart(String s) throws IOException {
        FileInputStream file = new FileInputStream(s+".txt");
        Scanner sc = new Scanner(file);
        Writer wr = new FileWriter(s+"_.txt");
            int numwords=0;
            while(numwords<20000) {
                if (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    for (int j = 1; j < line.length(); j++) {
                        if (!Character.isLetter(line.charAt(j)) && Character.isLetter(line.charAt(j - 1))) {
                            numwords++;
                            if (numwords >= 20000) {
                                line = line.substring(0, j);
                                break;
                            }
                        }
                    }

                    wr.write(line + '\n');
                }
                else{
                    System.out.println("Too short file to show correct result");
                    break;
                }
            }
            wr.close();
    }

    public static HashMap<String,Integer>[] analyseTenChapters(String name) throws FileNotFoundException {
        HashMap<String,Integer>[] chapters = new HashMap[10];
        for(int i=0;i<10;i++){
            chapters[i] = analiseText(name+"_"+(i+1));
        }
        return chapters;
    }




    public static void normalizeChapters(HashMap<String,Integer>[] h){
        for(int i=0;i<10;i++){
            Iterator it = h[i].entrySet().iterator();
            HashMap<String,Integer> newH = new HashMap<String,Integer>();
            while(it.hasNext()) {
                HashMap.Entry en = (Map.Entry)it.next();
                String word = en.getKey().toString();
                char[] wordCh=word.toCharArray();
                if(wordCh.length>=9) {
                    for (int j = 0; j < 3; j++) {
                        wordCh[j] = '*';
                        wordCh[wordCh.length - 1 - j] = '*';
                    }
                    String newWord = "";
                    for (int j = 0; j < wordCh.length; j++) {
                        newWord = newWord + wordCh[j];
                    }
                    Integer val = (Integer) en.getValue();
                    newH.put(newWord, val);
                    it.remove();
                }
                else{
                    newH.put(word, (Integer)en.getValue());
                }
            }
            h[i]=newH;
        }
    }

    public static HashMap<String,Integer>[] intersectChapters(HashMap<String,Integer>[] h){
        normalizeChapters(h);
        for(int i=0;i<10;i++) {
            Iterator it = h[i].entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry en = (Map.Entry)it.next();
                boolean remove = false;
                for (int j = 0; j < 10; j++) {
                    if (!h[j].containsKey(en.getKey()))
                        remove=true;
                }
                if(remove){
                    it.remove();
                }
            }
        }
        return h;
    }

    public static void showHashmaps(HashMap<String,Integer>[] h){
        for(int i=0;i<10;i++){
            Iterator it = h[i].entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry en = (Map.Entry)it.next();
                System.out.println(en.getKey()+": "+en.getValue());
            }
            System.out.println("------");
        }
    }

    public static HashMap<String,Integer> prepareToComparison(HashMap<String,Integer> h,HashMap<String,Integer> orig){
        for (Map.Entry<String,Integer> entry : orig.entrySet()) {
            if (h.get((String) entry.getKey()) == null) {
                h.put((String) entry.getKey(), 0);
            }
        }
        return h;
    }

    public static void main(String[] args) throws IOException {
        divideDostoevsky();
        HashMap<String,Integer>[] originalParts;
        showHashmaps(originalParts = intersectChapters(analyseTenChapters("dostoevsky")));
        HashMap<String,Integer> original = SimilarityRate.averageWord(originalParts);
        Double g = SimilarityRate.similarRatio(originalParts);
        showDistibution(original);
        System.out.println(g);
        extractPart("dostoevskyPart");
        HashMap<String,Integer> newPart = prepareToComparison(analiseText("dostoevskyPart_"),original);
        Double checkG = SimilarityRate.similarRatioOther(original,newPart);
        System.out.println(checkG);
        extractPart("belyaevPart");
        HashMap<String,Integer> newPart2 = prepareToComparison(analiseText("belyaevPart_"),original);
        Double checkG2 = SimilarityRate.similarRatioOther(original,newPart2);
        System.out.println(checkG2);
        extractPart("sholohovPart");
        HashMap<String,Integer> newPart3 = prepareToComparison(analiseText("sholohovPart_"),original);
        Double checkG3 = SimilarityRate.similarRatioOther(original,newPart3);
        System.out.println(checkG3);
    }
}
