package server;

import java.util.ArrayList;
import java.util.List;

public class Tray {
    private int trayId;
    private String partType;
    private double maxWeightCapacity;
    private List<AnimalPart> parts;

    public Tray(int trayId, String partType, double maxWeightCapacity) {
        this.trayId = trayId;
        this.partType = partType;
        this.maxWeightCapacity = maxWeightCapacity;
        this.parts = new ArrayList<>();
    }

    public int getTrayId() {
        return trayId;
    }

    public List<AnimalPart> getParts() {
        return parts;
    }

    public void addPart(AnimalPart part) {
        if (partType.equals(part.toString())) {
            parts.add(part);
        }
    }

    @Override
    public String toString() {
        return "TrayID: " + trayId + ", Type: " + partType + ", Max Capacity: " + maxWeightCapacity;
    }
}
