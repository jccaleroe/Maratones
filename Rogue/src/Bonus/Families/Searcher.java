package Bonus.Families;

class Searcher {

    Node backtrack(Node assignment){
        if(assignment.assignmentIsComplete()) return assignment;
        Node node;
        for (int i = 29; i >= 0; i--) {
            for (int j = 29; j >= 0; j--) {
                if (assignment.matrix[i][j] == 0) {
                    node = new Node(assignment);
                    node.assignVariable(i, j);
                    if(node.inference()) {
                        node = backtrack(node);
                        if (node != null) {
                            System.out.println("Assignation found: " + i + " with " + j);
                            return node;
                        }
                        else
                            assignment.restrict(i, j);
                    }
                    else
                        assignment.restrict(i, j);
                }
            }
        }
        return null;
    }
}
