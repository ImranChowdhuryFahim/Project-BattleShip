package Model.Player;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @org.junit.jupiter.api.Test
    void getTotalShipNumbers() {
        TestPlayer po = new TestPlayer();
        po.loadShips();
        Assertions.assertEquals(28, po.getTotalShipNumbers() );
    }
}