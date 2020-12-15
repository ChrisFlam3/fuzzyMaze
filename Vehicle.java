
package sample;

public class Vehicle {
    public double rotation;
    public Vector2 cell;
    public Vector2d position;
    public Vector2d move;
    public Vector2d leftSen;
    public Vector2d rightSen;
    public double distFront;
    public double distLeft;
    public double distRight;
    private int cellSize;
    private Vector2d up = new Vector2d(0, 1);
    private Vector2d down = new Vector2d(0, -1);
    private Vector2d right = new Vector2d(1, 0);
    private Vector2d left = new Vector2d(-1, 0);

    public Vehicle(Vector2 cell, Vector2d position, Vector2d move, int cellSize) {
        this.cell = cell;
        this.position = position;
        this.move = move;
        this.leftSen = new Vector2d(Math.cos(Math.PI/2) * move.x - Math.sin(Math.PI/2) * move.y, Math.sin(Math.PI/2) * move.x + Math.cos(Math.PI/2) * move.y);
        this.rightSen = new Vector2d(Math.cos(-Math.PI/2) * move.x - Math.sin(-Math.PI/2) * move.y, Math.sin(-Math.PI/2) * move.x + Math.cos(-Math.PI/2) * move.y);
        this.cellSize = cellSize;
    }

    public void rotate(double angle) {
        double rad = angle * Math.PI/180;
        this.rotation += rad;
        this.rotation =this.rotation%(2*Math.PI);
        this.move = new Vector2d(Math.cos(rad) * this.move.x - Math.sin(rad) * this.move.y, Math.sin(rad) * this.move.x + Math.cos(rad) * this.move.y);
        this.leftSen = new Vector2d(Math.cos(rad) * this.leftSen.x - Math.sin(rad) * this.leftSen.y, Math.sin(rad) * this.leftSen.x + Math.cos(rad) * this.leftSen.y);
        this.rightSen = new Vector2d(Math.cos(rad) * this.rightSen.x - Math.sin(rad) * this.rightSen.y, Math.sin(rad) * this.rightSen.x + Math.cos(rad) * this.rightSen.y);
    }

    public void move() {
        this.position.x += this.move.x;
        this.position.y -= this.move.y;
        if (this.position.x >= this.cellSize) {
            this.position.x = 0;
            this.cell.x++;
        } else if (this.position.x < 0) {
            this.position.x = this.cellSize - 1;
            this.cell.x--;
        }

        if (this.position.y >= this.cellSize) {
            this.position.y = 0;
            this.cell.y++;
        } else if (this.position.y < 0) {
            this.position.y = this.cellSize - 1;
            this.cell.y--;
        }

    }

    public double getDistance(Vector2d dir, Maze maze) {
        double maxcos = 0;
        double dist = 1000;
        if (0.3 < dir.angle(this.left)) {
            maxcos = dir.angle(this.left);
            if (maze.maze[this.cell.x][this.cell.y].sides[0] && (this.position.x > this.cellSize / 4 && this.position.x < 0.75 * this.cellSize && this.position.y > this.cellSize / 4 && this.position.y < 0.75 * this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7)
                    return 999;
            }else
                dist = Math.min(dist, (this.position.x - 0.25 * this.cellSize) / maxcos);

        }

        if (0.3 < dir.angle(this.up)) {
            maxcos = dir.angle(this.up);
            if (maze.maze[this.cell.x][this.cell.y].sides[3] && (this.position.x > this.cellSize / 4 && this.position.x < 0.75 * this.cellSize && this.position.y > this.cellSize / 4 && this.position.y < 0.75 * this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7)
                    return 999;

            } else
                dist = Math.min(dist, (this.position.y - 0.25 * this.cellSize) / maxcos);

        }

        if (0.3 < dir.angle(this.right)) {
            maxcos = dir.angle(this.right);
            if (maze.maze[this.cell.x][this.cell.y].sides[2] && (this.position.x > this.cellSize / 4 && this.position.x < 0.75 * this.cellSize && this.position.y > this.cellSize / 4 && this.position.y < 0.75 * this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7)
                    return 999;

            } else
                dist = Math.min(dist, (this.cellSize * 0.75 - this.position.x) / maxcos);

        }

        if (0.3 < dir.angle(this.down)) {
            maxcos = dir.angle(this.down);
            if (maze.maze[this.cell.x][this.cell.y].sides[1] && (this.position.x > this.cellSize / 4 && this.position.x < 0.75 * this.cellSize && this.position.y > this.cellSize / 4 && this.position.y < 0.75 * this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7)
                    return 999;

            } else
                dist = Math.min(dist, (this.cellSize * 0.75 - this.position.y) / maxcos);

        }

        return dist;
    }

    public void updateDistances(Maze maze) {
        this.distFront = this.getDistance(this.move, maze);
        this.distLeft = this.getDistance(this.leftSen, maze);
        this.distRight = this.getDistance(this.rightSen, maze);
    }
}
