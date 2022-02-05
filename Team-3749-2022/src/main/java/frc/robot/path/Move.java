package frc.robot.path;

public class Move {
    private double target;
    private MoveType type;
    
    public Move (MoveType type, double target) {
        this.target = target;
        this.type = type;
    }

    public double getTarget() {
        return target;
    }

    public MoveType getType() {
        return type;
    }
}
