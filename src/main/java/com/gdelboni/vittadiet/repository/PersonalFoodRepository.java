package com.gdelboni.vittadiet.repository;

import com.gdelboni.vittadiet.entities.PersonalFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PersonalFoodRepository extends JpaRepository<PersonalFood,Long> {
    List<PersonalFood> findByUserId(String userId);

    // Sempre use este para buscar/editar/apagar por id vindo do cliente:
    // impede que um usuário acesse o alimento de outro (IDOR).
    Optional<PersonalFood> findByIdAndUserId(Long id, String userId);
}
