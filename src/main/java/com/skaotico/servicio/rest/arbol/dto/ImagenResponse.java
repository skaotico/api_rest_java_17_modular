package com.skaotico.servicio.rest.arbol.dto;

public class ImagenResponse {
    private String id;
    private String url;

    public ImagenResponse(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
