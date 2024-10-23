package server;

public class AnimalPart {
    private int partId;
    private String partType;
    private double weight;

    public AnimalPart(int partId, String partType, double weight) {
        this.partId = partId;
        this.partType = partType;
        this.weight = weight;
    }

    public int getPartId() {
        return partId;
    }

    @Override
    public String toString() {
        return "PartID: " + partId + ", Type: " + partType + ", Weight: " + weight;
    }
}

