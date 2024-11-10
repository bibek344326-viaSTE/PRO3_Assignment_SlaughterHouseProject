package org.example.station2_cuttingandpartsregistration.model;


import jakarta.persistence.*;


@Entity
@Table(name = "trays")
public class Tray {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trayId;  // Ensure this field exists

    private String partType;

    @Column(name = "max_weight_capacity")
    private Double maxWeightCapacity;

    // Getters and Setters
    public Integer getTrayId() {
        return trayId;
    }

    public void setTrayId(Integer trayId) {
        this.trayId = trayId;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public Double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public void setMaxWeightCapacity(Double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }
}
