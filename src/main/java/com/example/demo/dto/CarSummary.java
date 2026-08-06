package com.example.demo.dto;

public record CarSummary(
        String licensePlate,
        String brand,
        String model,
        String bodyType,
        String category,
        String fuelType,
        String color,
        Integer year,
        Integer power,
        Double price,
        Double mileageKm
) {
}
