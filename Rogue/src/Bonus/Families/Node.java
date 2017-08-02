package Bonus.Families;

public class Node {
    private static int  fMuebles = 0, Francesa = 5,  Porsche = 10,   oneHijo1Hija = 15,    edad41 = 20, casa13 = 25,
                        abogado = 1,  Alemana = 6,   Jaguar = 11,    oneHija2Hijos = 16,   edad39 = 21, casa15 = 26,
                        actor = 2,    Espanola = 7,  BMW = 12,       twoHijas1Hijo = 17,   edad43 = 22, casa16 = 27,
                        gine = 3,     Sueca = 8,     Mercedenz = 13, twoHijos2Hijas = 18,  edad45 = 23, casaX = 28,
                        aBolsa = 4,   Italiana = 9,  Ferrari = 14,   NHijosNHijas = 19,    edad47 = 24, casaY = 29;

    int[][] matrix = new int[30][30];
    private int[] rows2s = new int[30];

    Node(Node node){
        System.arraycopy(node.rows2s, 0, rows2s, 0, 30);
        for(int i = 0; i < 30; i++)
                System.arraycopy(node.matrix[i], 0, matrix[i], 0, 30);
    }

    Node(){
        sameType(0, 4);
        sameType(5, 9);
        sameType(10, 14);
        sameType(15, 19);
        sameType(20, 24);
        sameType(25, 29);
        assignVariable(fMuebles, edad41);
        restrict(Porsche, oneHija2Hijos);
        assignVariable(Espanola, oneHija2Hijos);
        restrict(Espanola, abogado);
        restrict(Espanola, Porsche);
        assignVariable(edad45, Sueca);
        restrict(edad45, Jaguar);
        restrict(Sueca, Jaguar);
        assignVariable(aBolsa, edad39);
        assignVariable(aBolsa, Francesa);
        assignVariable(Alemana,casa15);
        restrict(Alemana, oneHijo1Hija);
        assignVariable(actor, casa13);
        assignVariable(actor, BMW);
        restrict(actor, edad43);
        restrict(actor, Italiana);
        assignVariable(twoHijos2Hijas, Mercedenz);
        restrict(Jaguar, edad43);
        restrict(Jaguar, twoHijas1Hijo);
        restrict(edad43, twoHijas1Hijo);
        restrict(gine, oneHijo1Hija);
        restrict(aBolsa, oneHijo1Hija);
        restrict(edad43, twoHijas1Hijo);
        restrict(gine, Porsche);
        restrict(aBolsa, Porsche);
        restrict(Francesa, BMW);
        System.out.println(this);
    }

    private void assign(int I, int J){
        int aux = J / 5;
        aux *= 5;

        for(int i = aux; i < aux+5; i++)
            if(matrix[I][i] != 2) {
                rows2s[I]++;
                matrix[I][i] = 2;
            }
        if(matrix[I][J] == 2)
            rows2s[I]--;
        matrix[I][J] = 1;
    }

    void restrict(int I, int J){
        if(matrix[I][J] != 2){
            matrix[I][J] = 2;
            rows2s[I]++;
        }
        if(matrix[J][I] != 2){
            matrix[J][I] = 2;
            rows2s[J]++;
        }
    }

    void assignVariable(int I, int J){

        if(matrix[I][J] != 0)
            return;

        assign(I, J);

        for(int i = 0;  i < 30; i++)
            if(matrix[I][i] == 1)
                assignVariable(i, J);

        assignVariable(J, I);
    }

    private boolean disjunction(int I, int J){
        for(int i = 0; i < 30; i++)
            if(matrix[I][i] == 1 && matrix[J][i] == 1)
                return false;
        return true;
    }

    boolean inference(){
        for(int i = 0; i < 30; i++)
            if(rows2s[i] > 25)
                return false;

        for(int i = 0; i < 30; i++){
            for(int j = i+1; j < 30; j++){
                if(matrix[i][j] == 2)
                    if(!disjunction(i, j))
                        return false;
            }
        }

        int pCasa = -1, onehijoHijaCasa = -1, v39Casa = -1, gineCasa = -1, v43Casa = -1, twohijas1hijoCasa = -1,
                jaguarCasa = -1;

        for (int i = 25; i <= 27; i++) {
            if (matrix[Porsche][i] == 1)
                pCasa = i;
            if (matrix[oneHijo1Hija][i] == 1)
                onehijoHijaCasa = i;
            if(matrix[edad39][i] == 1)
                v39Casa = i;
            if(matrix[gine][i] == 1)
                gineCasa = i;
            if(matrix[edad43][i] == 1)
                v43Casa = i;
            if(matrix[twoHijas1Hijo][i] == 1)
                twohijas1hijoCasa = i;
            if(matrix[Jaguar][i] == 1)
                jaguarCasa = i;
        }
        if (pCasa != -1 && onehijoHijaCasa != -1)
            if (pCasa > onehijoHijaCasa)
                return false;
        if(v39Casa != -1 && gineCasa != -1)
            if(v39Casa > gineCasa)
                return false;
        if(v39Casa != -1 && onehijoHijaCasa != -1)
            if(v39Casa < onehijoHijaCasa)
                return false;
        if(v43Casa != -1 && twohijas1hijoCasa != -1)
            if(v43Casa < twohijas1hijoCasa)
                return false;
        if(v43Casa != -1 && jaguarCasa != -1)
            if(v43Casa > jaguarCasa)
                return false;
        return true;
    }

    boolean assignmentIsComplete(){
        int count = 0;
        for(int i = 0; i < 30; i++)
            for(int j = 0; j < 30; j++) {
                if (matrix[i][j] == 0)
                    return false;
                if (matrix[i][j] == 1)
                    count++;
            }
        return count == 150;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("   ");
        for(int i = 1; i <= 30; i++) {
            builder.append(i);
            builder.append(" ");
            if (i < 10)
                builder.append(" ");
        }
        builder.append(" Restricciones");
        builder.append("\n");
        for(int i = 0; i < 30; i++) {
            builder.append(i+1);
            builder.append(" ");
            if(i < 9)
                builder.append(" ");
            for (int j = 0; j < 30; j++){
                builder.append(matrix[i][j]);
                builder.append("  ");
            }
            builder.append(" ");
            builder.append(rows2s[i]);
            builder.append("\n");
        }
        return builder.toString();
    }

    private void sameType(int I, int J){
        for(int i = I; i <= J; i++)
            for (int j = I; j <= J; j++){
                matrix[i][j] = 2;
                matrix[j][i] = 2;
                rows2s[i]++;
            }
    }
}
