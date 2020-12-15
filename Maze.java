
package sample;


import java.util.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class Maze {
    private int size;
    public Node[][] maze;
    private Vehicle vehicle;

    public Maze(int size, int width, int height) {
        this.size = size;
        this.maze = new Node[size][size];


        for(int i = 0; i < size; i++) {
            for(int y = 0; y < size; y++) {
                this.maze[i][y] = new Node();
            }
        }

        this.generate();
        this.vehicle = new Vehicle(new Vector2(0, 0), new Vector2d(Math.min(width, height) / size / 2, 60), new Vector2d(-1, 0), Math.min(width, height) / size);
    }

    private void generate() {
        Vector<Vector2> frontier = new Vector<>();
        frontier.add(new Vector2((int)(Math.random() * this.size), (int)(Math.random() * this.size)));

        while(!frontier.isEmpty()) {
            int rand = (int)(Math.random() * frontier.size());
            int x = frontier.elementAt(rand).x;
            int y = frontier.elementAt(rand).y;
            frontier.remove(rand);
            Node node = this.maze[x][y];
            node.isEmpty = true;
            Vector<Vector2> nonEmptyNeighbors = new Vector<>();
            Vector<Vector2> emptyNeighbors = new Vector<>();
            if (x + 1 < this.size && this.maze[x + 1][y].isEmpty) {
                emptyNeighbors.add(new Vector2(x + 1, y));
            } else if (x + 1 < this.size) {
                nonEmptyNeighbors.add(new Vector2(x + 1, y));
            }

            if (x - 1 >= 0 && this.maze[x - 1][y].isEmpty) {
                emptyNeighbors.add(new Vector2(x - 1, y));
            } else if (x - 1 >= 0) {
                nonEmptyNeighbors.add(new Vector2(x - 1, y));
            }

            if (y + 1 < this.size && this.maze[x][y + 1].isEmpty) {
                emptyNeighbors.add(new Vector2(x, y + 1));
            } else if (y + 1 < this.size) {
                nonEmptyNeighbors.add(new Vector2(x, y + 1));
            }

            if (y - 1 >= 0 && this.maze[x][y - 1].isEmpty) {
                emptyNeighbors.add(new Vector2(x, y - 1));
            } else if (y - 1 >= 0) {
                nonEmptyNeighbors.add(new Vector2(x, y - 1));
            }

            int rand2 = (int)(Math.random() * emptyNeighbors.size());
            if (emptyNeighbors.size() > 0) {
                Vector2 pick = emptyNeighbors.get(rand2);
                if (x + 1 < this.size && pick.x == x + 1) {
                    node.sides[2] = true;
                    this.maze[x + 1][y].sides[0] = true;
                } else if (x - 1 >= 0 && pick.x == x - 1) {
                    node.sides[0] = true;
                    this.maze[x - 1][y].sides[2] = true;
                } else if (y + 1 < this.size && pick.y == y + 1) {
                    node.sides[1] = true;
                    this.maze[x][y + 1].sides[3] = true;
                } else if (y - 1 >= 0) {
                    node.sides[3] = true;
                    this.maze[x][y - 1].sides[1] = true;
                }
            }

            for(Vector2 v:nonEmptyNeighbors) {
                frontier.remove(v);
                frontier.add(v);
            }
        }

    }

    public void visualizeGrid(GraphicsContext context, int width, int height) {
        int pxSize = Math.min(width, height) / this.size;
        context.setFill(Color.GRAY);
        int offsetX = width / 2 - this.size / 2 * pxSize;
        int offsetY = height / 2 - this.size / 2 * pxSize;
        context.fillRect(offsetX, offsetY, this.size * pxSize, this.size * pxSize);

        for(int i = 0; i < this.size; i++) {
            for(int y = 0; y < this.size; y++) {
                context.setFill(Color.WHITE);
                context.fillRect(offsetX + i * pxSize + pxSize / 4, offsetY + y * pxSize + pxSize / 4, pxSize / 2, pxSize / 2);
                if (this.maze[i][y].sides[0]) {
                    context.fillRect(offsetX + i * pxSize - pxSize / 4, offsetY + y * pxSize + pxSize / 4, pxSize / 2, pxSize / 2);
                }

                if (this.maze[i][y].sides[3]) {
                    context.fillRect(offsetX + i * pxSize + pxSize / 4, offsetY + y * pxSize - pxSize / 4, pxSize / 2, pxSize / 2);
                }
            }
        }

    }

    public void visualizeVehicle(Canvas canvas, int width, int height) {
        int pxSize = Math.min(width, height) / this.size;
        int offsetX = width / 2 - this.size / 2 * pxSize;
        int offsetY = height / 2 - this.size / 2 * pxSize;
        GraphicsContext context = canvas.getGraphicsContext2D();
        canvas.toFront();
        context.setFill(Color.RED);
        context.fillRect(offsetX + this.vehicle.cell.x * pxSize + this.vehicle.position.x, offsetY + this.vehicle.cell.y * pxSize + this.vehicle.position.y, 2, 2);
    }

    public void visualizeUtils(Canvas canvas, Label front, Label right, Label left, int width, int height) {
        int pxSize = Math.min(width, height) / this.size;
        int offsetX = width / 2 - this.size / 2 * pxSize;
        int offsetY = height / 2 - this.size / 2 * pxSize;
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, width, height);
        canvas.toFront();
        front.setText(String.format("distance front: %.3f", this.vehicle.distFront));
        right.setText(String.format("distance right: %.3f", this.vehicle.distRight));
        left.setText(String.format("distance left: %.3f", this.vehicle.distLeft));
        double startX = offsetX + this.vehicle.cell.x * pxSize + this.vehicle.position.x;
        double startY = offsetY + this.vehicle.cell.y * pxSize + this.vehicle.position.y;
        double endFrontX = startX + this.vehicle.move.x * this.vehicle.distFront;
        double endFrontY = startY - this.vehicle.move.y * this.vehicle.distFront;
        double endLeftX = startX + this.vehicle.leftSen.x * this.vehicle.distLeft;
        double endLeftY = startY - this.vehicle.leftSen.y * this.vehicle.distLeft;
        double endRightX = startX + this.vehicle.rightSen.x * this.vehicle.distRight;
        double endRightY = startY - this.vehicle.rightSen.y * this.vehicle.distRight;
        if (this.vehicle.distFront < 900) {
            context.setStroke(Color.BLUE);
            context.beginPath();
            context.moveTo(startX, startY);
            context.lineTo(endFrontX, endFrontY);
            context.stroke();
        }

        if (this.vehicle.distLeft < 900) {
            context.setStroke(Color.GREEN);
            context.beginPath();
            context.moveTo(startX, startY);
            context.lineTo(endLeftX, endLeftY);
            context.stroke();
        }

        if (this.vehicle.distRight < 900) {
            context.setStroke(Color.MAGENTA);
            context.beginPath();
            context.moveTo(startX, startY);
            context.lineTo(endRightX, endRightY);
            context.stroke();
        }

    }

    public void simulate(FuzzyRuleSet fuzzyRuleSet) {
        double lastDistFront = this.vehicle.distFront;
        double lastDistLeft = this.vehicle.distLeft;
        double lastDistRight = this.vehicle.distRight;
        this.vehicle.updateDistances(this);
        fuzzyRuleSet.setVariable("distFront", this.vehicle.distFront);
        fuzzyRuleSet.setVariable("distLeft", this.vehicle.distLeft);
        fuzzyRuleSet.setVariable("distRight", this.vehicle.distRight);
        fuzzyRuleSet.setVariable("ddistFront", this.vehicle.distFront - lastDistFront);
        fuzzyRuleSet.setVariable("ddistLeft", this.vehicle.distLeft - lastDistLeft);
        fuzzyRuleSet.setVariable("ddistRight", this.vehicle.distRight - lastDistRight);
        fuzzyRuleSet.evaluate();
        double turn = fuzzyRuleSet.getVariable("turn").defuzzify();
        this.vehicle.rotate(turn);
        this.vehicle.move();
    }
}
