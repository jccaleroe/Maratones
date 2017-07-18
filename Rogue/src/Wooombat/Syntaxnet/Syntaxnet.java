package Wooombat.Syntaxnet;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Syntaxnet {

    private static StringBuilder txt = new StringBuilder(), ann = new StringBuilder(), relations = new StringBuilder();
    private static long txtIndex = 0, boundIndex = 1, relationsIndex = 1;

    static int start(){
        String s;
        int exit = -1;
        try {
            Process process = Runtime.getRuntime().exec("docker start syntaxnet");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
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

    private static void convert(String str){
        String[] tmp = str.split("\\s+");
        txt.append(tmp[1]);
        txt.append(" ");

        ann.append("T");
        ann.append(boundIndex++);
        ann.append("\t");
        ann.append(tmp[3]);
        ann.append(" ");
        ann.append(txtIndex);
        ann.append(" ");
        txtIndex += tmp[1].length();
        ann.append(txtIndex++);
        ann.append("\t");
        ann.append(tmp[1]);
        ann.append("\n");

        String aux = tmp[6];
        if (aux.equals("0")) return;

        relations.append("R");
        relations.append(relationsIndex++);
        relations.append("\t");
        relations.append(tmp[7]);
        relations.append(" Arg1:T");
        relations.append(aux);
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return exit;
    }

    static int execute(String text, String outName){
        String str;
        txt.delete(0, txt.length());
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
        int exit = execute("yo declaro que Ivan es el mejor bailarin", "foo3");
        System.out.println("Process finished with exit code " + exit);
    }
}
