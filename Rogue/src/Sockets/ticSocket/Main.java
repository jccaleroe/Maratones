package ticSocket;

public class Main {

    public static void main(String[] args) {
        TicClient a = new TicClient();
        JudgeModel model = new JudgeModel();
        Controller controller = new Controller(a, model);
        Thread thread = new Thread(controller);
        controller.go();
        thread.start();
    }
}