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
    gps_point TEXT,
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
-- Tabla: nacionalidad
-- Descripción: Nacionalidades disponibles para usuarios
-- =====================================================
CREATE TABLE nacionalidad (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
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
    celular VARCHAR(20),
    telefono_fijo VARCHAR(20),
    rut VARCHAR(15),
    direccion TEXT,
    ciudad TEXT,
    nacionalidad_id INT REFERENCES nacionalidad(id), -- referencia a nacionalidad
    fecha_nacimiento DATE,
    genero CHAR(1) CHECK (genero IN ('MASCULINO', 'FEMENINO', 'OTRO')),
    activo BOOLEAN DEFAULT TRUE,
    ultimo_login TIMESTAMP,
    foto_perfil VARCHAR(50),
    metadata JSONB,
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
    aldfhjlasdkjfklñas
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

