package com.example.demo.service;

import com.example.demo.dto.CarSummary;
import com.example.demo.entity.Masina;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarSearchTools {

    private static final int MAX_RESULTS = 20;

    private final MasinaService masinaService;

    public CarSearchTools(MasinaService masinaService) {
        this.masinaService = masinaService;
    }

    @Tool(description = "Search AutoLix car listings by brand, model, body type, price range, year range, power range, "
            + "mileage range, fuel type and color. bodyType must be one of: Sedan, SUV, Hatchback, Compact, Convertible, "
            + "Coupe, Wagons. All parameters are optional - omit any you don't need to filter on. "
            + "Returns up to 20 matching cars, sorted by price ascending.")
    public List<CarSummary> searchCars(
            @ToolParam(description = "Car brand, e.g. Audi, Dacia, BMW", required = false) String brand,
            @ToolParam(description = "Car model, e.g. RS5, Bigster", required = false) String model,
            @ToolParam(description = "Body type: Sedan, SUV, Hatchback, Compact, Convertible, Coupe, or Wagons", required = false) String bodyType,
            @ToolParam(description = "Category/segment of the car", required = false) String category,
            @ToolParam(description = "Minimum price", required = false) Double minPrice,
            @ToolParam(description = "Maximum price", required = false) Double maxPrice,
            @ToolParam(description = "Fuel type, e.g. Benzina, Diesel, Electric, Hybrid", required = false) String fuelType,
            @ToolParam(description = "Color", required = false) String color,
            @ToolParam(description = "Minimum manufacturing year", required = false) Integer minYear,
            @ToolParam(description = "Maximum manufacturing year", required = false) Integer maxYear,
            @ToolParam(description = "Minimum engine power in horsepower", required = false) Integer minPower,
            @ToolParam(description = "Maximum engine power in horsepower", required = false) Integer maxPower,
            @ToolParam(description = "Minimum mileage in km", required = false) Double minMileageKm,
            @ToolParam(description = "Maximum mileage in km", required = false) Double maxMileageKm
    ) {
        Page<Masina> results = masinaService.filtrarePagini(
                brand, model, bodyType, category,
                minPrice, maxPrice,
                fuelType, color,
                minYear, maxYear,
                minPower, maxPower,
                minMileageKm, maxMileageKm,
                Sort.by(Sort.Direction.ASC, "pretul"),
                0, MAX_RESULTS
        );

        return results.getContent().stream()
                .map(CarSearchTools::toSummary)
                .toList();
    }

    @Tool(description = "Get full details for a single car by its license plate, as returned by searchCars.")
    public CarSummary getCarByLicensePlate(
            @ToolParam(description = "The car's license plate") String licensePlate
    ) {
        Masina masina = masinaService.getMasina(licensePlate);
        return masina == null ? null : toSummary(masina);
    }

    private static CarSummary toSummary(Masina masina) {
        return new CarSummary(
                masina.getNr_inmatriculare(),
                masina.getMarca(),
                masina.getModelul(),
                masina.getTip(),
                masina.getCategoria(),
                masina.getCombustibil(),
                masina.getCuloarea(),
                masina.getAnul(),
                masina.getPuterea(),
                masina.getPretul(),
                masina.getKilometraj()
        );
    }
}
