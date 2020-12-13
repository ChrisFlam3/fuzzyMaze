//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sample;

public class Vector2d {
    public double x = 0;
    public double y = 0;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d sum(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d multiply(Vector2d other) {
        return new Vector2d(this.x * other.x, this.y * other.y);
    }

    public Vector2d div(Vector2d other) {
        return new Vector2d(this.x / other.x, this.y / other.y);
    }

    public double angle(Vector2d other) {
        return (this.x * other.x + this.y * other.y) / Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)) * Math.sqrt(Math.pow(other.x, 2) + Math.pow(other.y, 2));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Vector2d)) {
            return false;
        } else {
            Vector2d that = (Vector2d)other;
            return this.x == that.x && this.y == that.y;
        }
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    public int hashCode() {
        int hash = 13;
        hash = (int)((double)hash + this.x * 31);
        hash = (int)((double)hash + this.y * 17);
        return hash;
    }
}
