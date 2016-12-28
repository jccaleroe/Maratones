package ticSocket;

public class JudgeModel {

    private boolean tie;

    public String whoWins(String[] a, boolean[] b) {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String a00 = a[0];
        String a01 = a[1];
        String a02 = a[2];
        String a10 = a[3];
        String a11 = a[4];
        String a12 = a[5];
        String a20 = a[6];
        String a21 = a[7];
        String a22 = a[8];

        String r1 = a00.concat(a01.concat(a02));
        if (r1.equals("XXX")) {
            return "X Wins";
        }
        if (r1.equals("OOO")) {
            return "O Wins";
        }
        String r2 = a00.concat(a10.concat(a20));
        if (r2.equals("XXX")) {
            return "X Wins";
        }
        if (r2.equals("OOO")) {
            return "O Wins";
        }
        String r3 = a00.concat(a11.concat(a22));
        if (r3.equals("XXX")) {
            return "X Wins";
        }
        if (r3.equals("OOO")) {
            return "O Wins";
        }
        String r4 = a01.concat(a11.concat(a21));
        if (r4.equals("XXX")) {
            return "X Wins";
        }
        if (r4.equals("OOO")) {
            return "O Wins";
        }
        String r5 = a02.concat(a12.concat(a22));
        if (r5.equals("XXX")) {
            return "X Wins";
        }
        if (r5.equals("OOO")) {
            return "O Wins";
        }
        String r6 = a10.concat(a11.concat(a12));
        if (r6.equals("XXX")) {
            return "X Wins";
        }
        if (r6.equals("OOO")) {
            return "O Wins";
        }
        String r7 = a20.concat(a21.concat(a22));
        if (r7.equals("XXX")) {
            return "X Wins";
        }
        if (r7.equals("OOO")) {
            return "O Wins";
        }
        String r8 = a02.concat(a11.concat(a20));
        if (r8.equals("XXX")) {
            return "X Wins";
        }
        if (r8.equals("OOO")) {
            return "O Wins";
        }
        tie = true;
        for (int i = 0; i < 9; i++) {
            if (b[i] == false) {
                tie = false;
                break;
            }
        }
        if (tie)
            return ("Empate");
        return "None";
    }
}