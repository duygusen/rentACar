package com.tobeto.rentACar.repositories;

import com.tobeto.rentACar.entities.concretes.Model;
import com.tobeto.rentACar.services.dtos.model.response.GetAllModelsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    boolean existsByName(String name);

    @Query("SELECT new com.tobeto.rentACar.services.dtos.model.response.GetAllModelsResponse" +
            "(m.name," +
            " new com.tobeto.rentACar.services.dtos.brand.response.GetBrandByIdResponse(b.id, b.name, b.logoPath))" +
            " FROM Model m INNER JOIN m.brand b")
    List<GetAllModelsResponse> getAll();
}
