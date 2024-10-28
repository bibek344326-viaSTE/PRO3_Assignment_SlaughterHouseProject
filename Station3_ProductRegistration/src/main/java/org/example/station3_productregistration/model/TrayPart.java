package org.example.station3_productregistration.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tray_parts")
public class TrayPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tray_id")
    private Tray tray;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private AnimalPart animalPart;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tray getTray() {
        return tray;
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }

    public AnimalPart getAnimalPart() {
        return animalPart;
    }

    public void setAnimalPart(AnimalPart animalPart) {
        this.animalPart = animalPart;
    }
}
