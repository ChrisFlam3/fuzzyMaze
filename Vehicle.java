//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
        this.leftSen = new Vector2d(Math.cos(1.5707963267948966D) * move.x - Math.sin(1.5707963267948966D) * move.y, Math.sin(1.5707963267948966D) * move.x + Math.cos(1.5707963267948966D) * move.y);
        this.rightSen = new Vector2d(Math.cos(-1.5707963267948966D) * move.x - Math.sin(-1.5707963267948966D) * move.y, Math.sin(-1.5707963267948966D) * move.x + Math.cos(-1.5707963267948966D) * move.y);
        this.cellSize = cellSize;
    }

    public void rotate(double angle) {
        double rad = angle * 0.017453292519943295D;
        this.rotation += rad;
        this.rotation %= 6.283185307179586D;
        this.move = new Vector2d(Math.cos(rad) * this.move.x - Math.sin(rad) * this.move.y, Math.sin(rad) * this.move.x + Math.cos(rad) * this.move.y);
        this.leftSen = new Vector2d(Math.cos(rad) * this.leftSen.x - Math.sin(rad) * this.leftSen.y, Math.sin(rad) * this.leftSen.x + Math.cos(rad) * this.leftSen.y);
        this.rightSen = new Vector2d(Math.cos(rad) * this.rightSen.x - Math.sin(rad) * this.rightSen.y, Math.sin(rad) * this.rightSen.x + Math.cos(rad) * this.rightSen.y);
    }

    public void move() {
        Vector2d var10000 = this.position;
        var10000.x += this.move.x;
        var10000 = this.position;
        var10000.y -= this.move.y;
        if (this.position.x >= (double)this.cellSize) {
            this.position.x = 0;
            ++this.cell.x;
        } else if (this.position.x < 0) {
            this.position.x = (double)(this.cellSize - 1);
            --this.cell.x;
        }

        if (this.position.y >= (double)this.cellSize) {
            this.position.y = 0;
            ++this.cell.y;
        } else if (this.position.y < 0) {
            this.position.y = (double)(this.cellSize - 1);
            --this.cell.y;
        }

    }

    public double getDistance(Vector2d dir, Maze maze) {
        double maxcos = 0;
        double dist = 1000;
        if (0.3D < dir.angle(this.left)) {
            maxcos = dir.angle(this.left);
            if (!maze.maze[this.cell.x][this.cell.y].sides[0] || (this.position.x <= (double)(this.cellSize / 4) || this.position.x >= 0.75D * (double)this.cellSize || this.position.y <= (double)(this.cellSize / 4) || this.position.y >= 0.75D * (double)this.cellSize) && !dir.equals(this.move)) {
                dist = Math.min(dist, (this.position.x - 0.25D * (double)this.cellSize) / maxcos);
            } else if (maxcos > 0.7D) {
                return 999;
            }
        }

        if (0.3D < dir.angle(this.up)) {
            maxcos = dir.angle(this.up);
            if (maze.maze[this.cell.x][this.cell.y].sides[3] && (this.position.x > (double)(this.cellSize / 4) && this.position.x < 0.75D * (double)this.cellSize && this.position.y > (double)(this.cellSize / 4) && this.position.y < 0.75D * (double)this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7D) {
                    return 999;
                }
            } else {
                dist = Math.min(dist, (this.position.y - 0.25D * (double)this.cellSize) / maxcos);
            }
        }

        if (0.3D < dir.angle(this.right)) {
            maxcos = dir.angle(this.right);
            if (maze.maze[this.cell.x][this.cell.y].sides[2] && (this.position.x > (double)(this.cellSize / 4) && this.position.x < 0.75D * (double)this.cellSize && this.position.y > (double)(this.cellSize / 4) && this.position.y < 0.75D * (double)this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7D) {
                    return 999;
                }
            } else {
                dist = Math.min(dist, ((double)this.cellSize * 0.75D - this.position.x) / maxcos);
            }
        }

        if (0.3D < dir.angle(this.down)) {
            maxcos = dir.angle(this.down);
            if (maze.maze[this.cell.x][this.cell.y].sides[1] && (this.position.x > (double)(this.cellSize / 4) && this.position.x < 0.75D * (double)this.cellSize && this.position.y > (double)(this.cellSize / 4) && this.position.y < 0.75D * (double)this.cellSize || dir.equals(this.move))) {
                if (maxcos > 0.7D) {
                    return 999;
                }
            } else {
                dist = Math.min(dist, ((double)this.cellSize * 0.75D - this.position.y) / maxcos);
            }
        }

        return dist;
    }

    public void updateDistances(Maze maze) {
        this.distFront = this.getDistance(this.move, maze);
        this.distLeft = this.getDistance(this.leftSen, maze);
        this.distRight = this.getDistance(this.rightSen, maze);
    }
}
