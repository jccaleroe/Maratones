package ticSocket;

public class Controller implements Runnable {

    private TicClient view;
    private JudgeModel model;


    public Controller(TicClient a, JudgeModel judge) {
        this.view = a;
        this.model = judge;
    }

    public void go() {
        this.view.go();
    }

    public void run() {
        while (true) {
            String aux = model.whoWins(this.view.getResults(), this.view.getUsed());
            if (!(aux.equals("None")))
                this.view.resetGame(aux);
        }
    }
}