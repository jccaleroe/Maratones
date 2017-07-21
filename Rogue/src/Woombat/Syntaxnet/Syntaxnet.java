package Woombat.Syntaxnet;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Syntaxnet {

    private static StringBuilder txt = new StringBuilder(), ann = new StringBuilder(), relations = new StringBuilder(),
    phrases = new StringBuilder(), names = new StringBuilder(), verbs = new StringBuilder(),
    tmpBuilder = new StringBuilder(), phrase_name = new StringBuilder(), phrase_verb = new StringBuilder();

    private static boolean phraseTracker;

    private static long txtIndex = 0, boundIndex = 1, relationsIndex = 1, namesID = 1, phrasesID = 1, verbsID = 0,
    notesIndex = 1;

    private static HashMap<String, Long> namesMap = new HashMap<>(), verbsMap = new HashMap<>();

    private static void reset(){
        txt.delete(0, txt.length());
        ann.delete(0, ann.length());
        relations.delete(0, relations.length());
        txtIndex = 0;
        boundIndex = relationsIndex = 1;
        namesID = verbsID = phrasesID = 1;
        phrases.delete(0, phrases.length());
        phrase_name.delete(0, phrases.length());
        phrase_verb.delete(0, phrases.length());
        names.delete(0, phrases.length());
        verbs.delete(0, phrases.length());
        tmpBuilder.delete(0, tmpBuilder.length());
        notesIndex = 1;
    }

    public static int start(){
        String s;
        int exit = -1;
        try {
            Process process = Runtime.getRuntime().exec("docker start syntaxnet");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = br.readLine()) != null) System.out.println(s);
            process.waitFor();
            br.close();
            process.destroy();
            process = Runtime.getRuntime().exec("docker start brat");
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = br.readLine()) != null) System.out.println(s);
            process.waitFor();
            exit = process.exitValue();
            br.close();
            process.destroy();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return exit;
    }

    private static void updatePhrases(){
        tmpBuilder.insert(0, phrasesID + " ");
        tmpBuilder.append("\n");
        phrasesID++;
        phrases.append(tmpBuilder);
        tmpBuilder.delete(0, tmpBuilder.length());
        phraseTracker = true;
    }

    private static void convert(String str){
        String[] tmp = str.split("\\s+");
        String POSTag = tmp[3], form = tmp[1], head = tmp[6], dependency = tmp[7], features = tmp[5];
        txt.append(form);
        txt.append(" ");

        tmpBuilder.append(form);
        tmpBuilder.append(" ");

        if (POSTag.equals("PROPN") || POSTag.equals("NOUN")){
            if (!namesMap.containsKey(form)){
                namesMap.put(form, namesID);
                names.append(namesID);
                names.append(" ");
                names.append(form);
                names.append("\n");
                namesID++;
            }
            phrase_name.append(phrasesID);
            phrase_name.append(" ");
            phrase_name.append(namesMap.get(form));
            phrase_name.append("\n");
        }
        else if (POSTag.equals("VERB") || POSTag.equals("AUX")) {
            if (!verbsMap.containsKey(form)) {
                verbsMap.put(form, verbsID);
                verbs.append(verbsID);
                verbs.append(" ");
                verbs.append(form);
                verbs.append("\n");
                verbsID++;
            }
            phrase_verb.append(phrasesID);
            phrase_verb.append(" ");
            phrase_verb.append(verbsMap.get(form));
            phrase_verb.append("\n");
        }

        phraseTracker = false;
        if (form.equals(".")) updatePhrases();

        ann.append("T");
        ann.append(boundIndex++);
        ann.append("\t");
        ann.append(POSTag);
        ann.append(" ");
        ann.append(txtIndex);
        ann.append(" ");
        txtIndex += form.length();
        ann.append(txtIndex++);
        ann.append("\t");
        ann.append(form);
        ann.append("\n");

        ann.append("#");
        ann.append(notesIndex++);
        ann.append("\tAnnotatorNotes T");
        ann.append(boundIndex - 1);
        ann.append("\t");
        ann.append(features);
        ann.append("\n");

        if (head.equals("0")) return;

        relations.append("R");
        relations.append(relationsIndex++);
        relations.append("\t");
        relations.append(dependency);
        relations.append(" Arg1:T");
        relations.append(head);
        relations.append(" Arg2:T");
        relations.append(boundIndex - 1);
        relations.append("\n");
    }

    private static int writeFiles(String fileName){
        String str;
        int exit = -1;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "exec", "brat", "sh",
                    "bratdata/copy.sh", txt.toString(), "bratdata/"+fileName+".txt"});
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((str = br.readLine()) != null) System.out.println(str);
            process.waitFor();
            br.close();
            process.destroy();
            process = Runtime.getRuntime().exec(new String[]{"docker", "exec", "brat", "sh",
                    "bratdata/copy.sh", ann.toString() + relations.toString(), "bratdata/"+fileName+".ann"});
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((str = br.readLine()) != null) System.out.println(str);
            process.waitFor();
            exit = process.exitValue();
            br.close();
            if (!phraseTracker)
                updatePhrases();

            System.out.println(phrases);
            System.out.println(names);
            System.out.println(verbs);
            System.out.println(phrase_name);
            System.out.println(phrase_verb);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return exit;
    }

    public static int execute(String text, String outName){
        String str;
        reset();
        int exit = -1;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "exec", "syntaxnet", "sh",
                    "syntaxnet/spanish.sh", text});
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((str = br.readLine()) != null && !str.isEmpty()) convert(str);
            process.waitFor();
            exit = process.exitValue();
            br.close();
            process.destroy();
            return writeFiles(outName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return exit;
    }

    public static void main(String[] args) {
        //System.out.println("Process finished with exit code " + start());
        int exit = execute("Ivan es el mejor bailarin", "foo");
        System.out.println("Process finished with exit code " + exit);
    }
}
