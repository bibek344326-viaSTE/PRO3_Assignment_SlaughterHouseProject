package org.example.station1_animalregistration.grpc;

import com.animalregistration.*;
import io.grpc.stub.StreamObserver;
import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnimalRegistrationServiceImpl extends AnimalRegistrationServiceGrpc.AnimalRegistrationServiceImplBase {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public void registerAnimal(AnimalRequest request, StreamObserver<AnimalResponse> responseObserver) {
        try {
            String registrationNumber = getNextRegistrationNumber();
            Animal animal = new Animal();
            animal.setRegistrationNumber(registrationNumber);
            animal.setWeight(request.getWeight());
            animal.setRegistrationDate(new Date());

            animalRepository.save(animal);

            AnimalResponse response = AnimalResponse.newBuilder()
                    .setMessage(registrationNumber)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void listRegisteredAnimals(EmptyRequest request, StreamObserver<AnimalListResponse> responseObserver) {
        AnimalListResponse.Builder responseBuilder = AnimalListResponse.newBuilder();
        List<Animal> animals = animalRepository.findAll();

        for (Animal animal : animals) {
            responseBuilder.addAnimals(com.animalregistration.Animal.newBuilder()
                    .setAnimalId(animal.getAnimalId())
                    .setRegistrationNumber(animal.getRegistrationNumber())
                    .setWeight(animal.getWeight())
                    .setRegistrationDate(animal.getRegistrationDate().toString())
                    .build());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private String getNextRegistrationNumber() {
        Animal lastAnimal = animalRepository.findTopByOrderByAnimalIdDesc();
        if (lastAnimal != null) {
            String lastRegNumber = lastAnimal.getRegistrationNumber();
            return incrementRegistrationNumber(lastRegNumber);
        } else {
            return "REG00001"; // First registration number
        }
    }

    private String incrementRegistrationNumber(String currentNumber) {
        String prefix = currentNumber.substring(0, 3);
        int number = Integer.parseInt(currentNumber.substring(3));
        number++;
        return prefix + String.format("%05d", number);
    }
}
