package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

}
