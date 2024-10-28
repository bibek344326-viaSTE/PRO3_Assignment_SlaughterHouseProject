package org.example.station2_cuttingandpartsregistration.repository;

import org.example.station2_cuttingandpartsregistration.model.TrayPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrayPartRepository extends JpaRepository<TrayPart, Integer> {
    // Custom method to find TrayParts by trayId
    List<TrayPart> findByTray_TrayId(Integer trayId);  // Correct method signature
}
