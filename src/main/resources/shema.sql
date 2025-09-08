-- =====================================================
-- Enum: tipo_area_enum
-- Descripción: Tipos de área permitidos
-- =====================================================
CREATE TYPE tipo_area_enum AS ENUM (
    'invernadero',
    'exterior',
    'interior',
    'hidroponico'
);

-- =====================================================
-- Enum: rol_usuario_enum
-- Descripción: Roles de usuario permitidos
-- =====================================================
CREATE TYPE rol_usuario_enum AS ENUM (
    'admin',
    'usuario',
    'supervisor'
);

-- =====================================================
-- Tabla: recinto
-- Descripción: Contiene los recintos que albergan áreas con tutores
-- =====================================================
CREATE TABLE recinto (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    ubicacion TEXT,
    capacidad_areas INT,
    descripcion TEXT
);

-- =====================================================
-- Tabla: area
-- Descripción: Áreas dentro de un recinto
-- =====================================================
CREATE TABLE area (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    recinto_id BIGINT NOT NULL REFERENCES recinto(id),
    superficie_m2 NUMERIC(8,2),
    tipo_area tipo_area_enum
);

-- =====================================================
-- Tabla: arbol
-- Descripción: Árboles plantados en un área
-- =====================================================
CREATE TABLE arbol (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    especie TEXT,
    area_id BIGINT NOT NULL REFERENCES area(id),
    gps_point POINT,
    metadata JSONB
);
-- =====================================================
-- Tabla: tutor
-- Descripción: Dispositivos que miden la humedad
-- =====================================================
CREATE TABLE tutor (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    codigo_serial TEXT UNIQUE NOT NULL,
    area_id BIGINT NOT NULL REFERENCES area(id)
);

-- =====================================================
-- Tabla: lectura
-- Descripción: Lecturas periódicas de humedad tomadas por tutores
-- =====================================================
CREATE TABLE lectura (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tutor_id BIGINT NOT NULL REFERENCES tutor(id),
    arbol_id BIGINT NOT NULL REFERENCES arbol(id),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datos_ambientales JSONB
);

-- =====================================================
-- Tabla: usuario
-- Descripción: Usuarios del sistema con roles
-- =====================================================
CREATE TABLE usuario (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    apellido TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: acceso_usuario
-- Descripción: Registro de accesos de los usuarios
-- =====================================================
CREATE TABLE acceso_usuario (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip TEXT,
    exito BOOLEAN NOT NULL
);

-- =====================================================
-- Tabla: rol (opcional para sistema escalable)
-- Descripción: Define roles y permisos
-- =====================================================
CREATE TABLE rol (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre rol_usuario_enum UNIQUE NOT NULL,
    descripcion TEXT
);

-- =====================================================
-- Relación muchos a muchos usuario-rol
-- =====================================================
CREATE TABLE usuario_rol (
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    rol_id BIGINT NOT NULL REFERENCES rol(id),
    PRIMARY KEY (usuario_id, rol_id)
);

-- =====================================================
-- Ejemplo JSONB para datos_ambientales
-- =====================================================
/*
{
  "temperatura": 11.8,           // en °C
  "velocidad_viento": 2.6,       // en km/h
  "direccion_viento": 214,       // en grados
  "es_dia": true,                // true = día, false = noche
  "latitude": -33.5,             // latitud del lugar
  "longitude": -70.625,          // longitud del lugar
  "elevacion": 538,              // metros sobre el nivel del mar
  "timezone": "GMT",             // zona horaria
  "timezone_abbreviation": "GMT",
  "utc_offset_seconds": 0,
  "weathercode": 3,              // código de clima WMO
  "interval": 900,               // intervalo de actualización en segundos
  "generationtime_ms": 0.046,    // tiempo de generación del dato
  "humedad_suelo": 45.3,         // % de humedad del suelo
  "estado_alerta": false,        // alerta de humedad
  "nota": "No se detectan problemas" // observación manual
}
*/
