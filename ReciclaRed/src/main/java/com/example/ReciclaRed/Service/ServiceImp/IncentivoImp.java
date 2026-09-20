package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Incentivo;
import com.example.ReciclaRed.Repository.IncentivoRepository;
import com.example.ReciclaRed.Service.IncentivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncentivoImp implements IncentivoService {

    private final IncentivoRepository incentivoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Incentivo> consultarIncentivosPorUsuario(Long usuarioId) {
        return incentivoRepository.findByUsuarioId(usuarioId);
    }
}
