package com.skaotico.servicio.rest.rol.model;

/**
 * Enumeración que representa los diferentes tipos de roles
 * disponibles para los usuarios en el sistema.
 */
public enum RolUsuarioEnum {
    ADMIN("admin"),
    USUARIO("usuario"),
    SUPERVISOR("supervisor");

    private final String value;

    RolUsuarioEnum(String value) {
        this.value = value;
    }

    /**
     * Convierte un String en un RolUsuarioEnum correspondiente.
     *
     * @param nombre el nombre del rol
     * @return el RolUsuarioEnum correspondiente
     * @throws IllegalArgumentException si no existe un rol con el nombre proporcionado
     */
    public static RolUsuarioEnum fromString(String nombre) {
        for (RolUsuarioEnum rol : RolUsuarioEnum.values()) {
            if (rol.getValue().equalsIgnoreCase(nombre)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("No existe un rol con nombre: " + nombre);
    }

    /**
     * Obtiene el valor asociado al rol.
     *
     * @return el valor del rol como String
     */
    public String getValue() {
        return value;
    }
}
