package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
}
