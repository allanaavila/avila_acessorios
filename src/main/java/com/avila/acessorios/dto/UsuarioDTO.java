package com.avila.acessorios.dto;

import com.avila.acessorios.model.TipoUsuario;
import com.avila.acessorios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
    private String tipoUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getIdUsuario();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.telefone = usuario.getTelefone();
        this.dataNascimento = usuario.getDataNascimento();
        this.tipoUsuario = usuario.getTipoUsuario().name();
    }

    public UsuarioDTO() {

    }
}
