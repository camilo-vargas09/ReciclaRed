package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Incentivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncentivoRepository extends JpaRepository<Incentivo, Long> {

    List<Incentivo> findByUsuarioId(Long usuarioId);
}