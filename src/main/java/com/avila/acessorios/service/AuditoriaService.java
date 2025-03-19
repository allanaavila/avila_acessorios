package com.avila.acessorios.service;

import com.avila.acessorios.model.Auditoria;
import com.avila.acessorios.repository.AuditoriaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public void registrar(String entidade, String acao, String detalhes, String usuario) {
        if (usuario == null || usuario.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usuario = auth != null ? auth.getName() : "Desconhecido";
        }

        Auditoria auditoria = new Auditoria();
        auditoria.setEntidade(entidade);
        auditoria.setAcao(acao);
        auditoria.setDetalhes(detalhes);
        auditoria.setUsuario(usuario);
        auditoria.setDataHora(LocalDateTime.now());
        auditoriaRepository.save(auditoria);
    }

    public String obterUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "Sistema";
        }
        return authentication.getName();
    }


    public List<Auditoria> listarTodas() {
        return auditoriaRepository.findAll();
    }
}
