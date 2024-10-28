package org.example.station2_cuttingandpartsregistration.grpc;

import com.cutting_partsRegistration.*;
import com.cutting_partsRegistration.CuttingAndPartRegistrationServiceGrpc;
import org.example.station2_cuttingandpartsregistration.model.AnimalPart;
import org.example.station2_cuttingandpartsregistration.model.Tray;
import org.example.station2_cuttingandpartsregistration.model.TrayPart;
import org.example.station2_cuttingandpartsregistration.repository.AnimalPartRepository;
import org.example.station2_cuttingandpartsregistration.repository.TrayRepository;
import org.example.station2_cuttingandpartsregistration.repository.TrayPartRepository; // Include this if you're using TrayPart
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuttingAndPartsRegistrationServiceImpl extends CuttingAndPartRegistrationServiceGrpc.CuttingAndPartRegistrationServiceImplBase {

    @Autowired
    private AnimalPartRepository animalPartRepository;

    @Autowired
    private TrayRepository trayRepository;

    @Autowired
    private TrayPartRepository trayPartRepository; // Include this if you're using TrayPart

    @Override
    public void addAnimalPart(AddAnimalPartRequest request, StreamObserver<AddAnimalPartResponse> responseObserver) {
        try {
            AnimalPart animalPart = new AnimalPart();
            animalPart.setAnimalId(request.getAnimalId());
            animalPart.setPartType(request.getPartType());
            animalPart.setWeight(request.getWeight());

            animalPartRepository.save(animalPart);
            responseObserver.onNext(AddAnimalPartResponse.newBuilder().setSuccess(true).build());
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(AddAnimalPartResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAnimalParts(GetAnimalPartsRequest request, StreamObserver<GetAnimalPartsResponse> responseObserver) {
        List<AnimalPart> animalParts = animalPartRepository.findByAnimalId(request.getAnimalId());
        List<Part> parts = new ArrayList<>();

        for (AnimalPart part : animalParts) {
            parts.add(Part.newBuilder()
                    .setPartId(part.getPartId())
                    .setPartType(part.getPartType())
                    .setWeight(part.getWeight())
                    .build());
        }

        GetAnimalPartsResponse response = GetAnimalPartsResponse.newBuilder().addAllParts(parts).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addTray(AddTrayRequest request, StreamObserver<AddTrayResponse> responseObserver) {
        try {
            Tray tray = new Tray();
            tray.setPartType(request.getPartType());
            tray.setMaxWeightCapacity(request.getMaxWeightCapacity());

            trayRepository.save(tray);
            responseObserver.onNext(AddTrayResponse.newBuilder().setSuccess(true).build());
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(AddTrayResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void addPartToTray(AddPartToTrayRequest request, StreamObserver<AddPartToTrayResponse> responseObserver) {
        try {
            // Find the tray and part
            Tray tray = trayRepository.findById(request.getTrayId()).orElse(null);
            AnimalPart part = animalPartRepository.findById(request.getPartId()).orElse(null);

            if (tray != null && part != null) {
                // Create and save the association if using TrayPart
                TrayPart trayPart = new TrayPart();
                trayPart.setTray(tray);
                trayPart.setAnimalPart(part);
                trayPartRepository.save(trayPart);
                responseObserver.onNext(AddPartToTrayResponse.newBuilder().setSuccess(true).build());
            } else {
                responseObserver.onNext(AddPartToTrayResponse.newBuilder().setSuccess(false).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(AddPartToTrayResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getTrays(GetTraysRequest request, StreamObserver<GetTraysResponse> responseObserver) {
        List<Tray> trays = trayRepository.findAll();
        List<com.cutting_partsRegistration.Tray> trayList = new ArrayList<>();

        for (Tray tray : trays) {
            trayList.add(com.cutting_partsRegistration.Tray.newBuilder()
                    .setTrayId(tray.getTrayId())
                    .setPartType(tray.getPartType())
                    .setMaxWeightCapacity(tray.getMaxWeightCapacity())
                    .build());
        }

        GetTraysResponse response = GetTraysResponse.newBuilder().addAllTrays(trayList).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPartsInTray(GetPartsInTrayRequest request, StreamObserver<GetPartsInTrayResponse> responseObserver) {
        // You would need to implement fetching parts in a tray using a custom method in TrayPartRepository
        try {
            // Example fetch parts logic from the TrayPartRepository if implemented
            List<TrayPart> trayParts = trayPartRepository.findByTray_TrayId(request.getTrayId()); // You would need to create this method
            List<Part> parts = new ArrayList<>();

            for (TrayPart trayPart : trayParts) {
                AnimalPart part = trayPart.getAnimalPart();
                parts.add(Part.newBuilder()
                        .setPartId(part.getPartId())
                        .setPartType(part.getPartType())
                        .setWeight(part.getWeight())
                        .build());
            }

            GetPartsInTrayResponse response = GetPartsInTrayResponse.newBuilder().addAllParts(parts).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }
}
