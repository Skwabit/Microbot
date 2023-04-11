package net.runelite.client.plugins.microbot.scripts.cannon;

import net.runelite.api.VarPlayer;
import net.runelite.api.coords.WorldArea;
import net.runelite.client.plugins.cannon.CannonPlugin;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.scripts.Scripts;
import net.runelite.client.plugins.microbot.util.gameobject.GameObject;
import net.runelite.client.plugins.microbot.util.math.Random;
import net.runelite.client.plugins.microbot.util.menu.Menu;

import java.util.concurrent.TimeUnit;

public class CannonScript extends Scripts {
    public boolean run() {
        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            super.run();
            net.runelite.api.GameObject brokenCannon = GameObject.findGameObject(14916);
            if (brokenCannon != null) {
                Menu.doAction("Repair", brokenCannon.getCanvasTilePoly(), new String[] {"Broken multicannon"});
                return;
            }
            int cannonBallsLeft = Microbot.getClientThread().runOnClientThread(() -> Microbot.getClient().getVarpValue(VarPlayer.CANNON_AMMO));
            if (cannonBallsLeft < Random.random(10, 15)) {
                net.runelite.api.GameObject cannon = GameObject.findGameObject(6);
                if (cannon == null) return;
                WorldArea cannonLocation = new WorldArea(cannon.getWorldLocation().getX() - 1, cannon.getWorldLocation().getY() - 1, 3, 3, cannon.getWorldLocation().getPlane());
                if (!cannonLocation.toWorldPoint().equals(CannonPlugin.getCannonPosition().toWorldPoint())) return;
                Menu.doAction("Fire", cannon.getCanvasTilePoly(), new String[] {"Dwarf multicannon"});
                sleep(2000, 4000);
            }
        }, 0, 2000, TimeUnit.MILLISECONDS);
        return true;
    }
}
