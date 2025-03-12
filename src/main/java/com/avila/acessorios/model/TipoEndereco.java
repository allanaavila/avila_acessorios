package com.avila.acessorios.model;

public enum TipoEndereco {
    RESIDENCIAL, COMERCIAL, ENTREGA;

    public static TipoEndereco fromString(String tipo) {
        for (TipoEndereco t : TipoEndereco.values()) {
            if (t.name().equalsIgnoreCase(tipo)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de endereço inválido: " + tipo);
    }
}
