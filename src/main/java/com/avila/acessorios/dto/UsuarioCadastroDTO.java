package com.avila.acessorios.dto;


import com.avila.acessorios.model.TipoUsuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "O tipo de usuário é obrigatório")
    private TipoUsuario tipoUsuario;
}
