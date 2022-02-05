package frc.robot.path;

import java.util.List;

public class Paths {
    public static List<Move> Test = List.of(
        new Move(MoveType.FWD, 1),
        new Move(MoveType.ROT, 90),
        new Move(MoveType.FWD, 1)
    );
}
