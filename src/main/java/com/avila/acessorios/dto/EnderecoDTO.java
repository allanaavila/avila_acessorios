package com.avila.acessorios.dto;


import com.avila.acessorios.model.Endereco;
import com.avila.acessorios.model.TipoEndereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnderecoDTO {
    private Long idEndereco;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    private String tipoEndereco;

    private Long idUsuario;


    public EnderecoDTO() {}

    public EnderecoDTO(Endereco endereco) {
        this.idEndereco = endereco.getIdEndereco();
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
        this.tipoEndereco = endereco.getTipoEndereco().name();
        this.idUsuario = endereco.getUsuario().getIdUsuario();
    }
}
