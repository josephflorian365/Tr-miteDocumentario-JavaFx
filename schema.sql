create database doqinter;
use doqinter;
CREATE TABLE AREA (
    IDARE int NOT NULL AUTO_INCREMENT COMMENT 'Es el Identificador del Area.',
    NOMARE char(50) NOT NULL COMMENT 'Contiene el nombre del área.',
    ESTARE char(1) NOT NULL COMMENT 'Contiene el valor de A o I si el área esta operativa.',
    CONSTRAINT AREA_pk PRIMARY KEY (IDARE)
);

-- Table: DOCUMENTO
CREATE TABLE DOCUMENTO (
    IDDOC int NOT NULL AUTO_INCREMENT COMMENT 'Contiene el identificador del área,',
    ASUDOC char(150) NOT NULL COMMENT 'Contiene el asunto del documento',
    IDTIPDOC int NOT NULL COMMENT 'Contiene el identificador de tipo de documento.',
    IDEXP int NOT NULL COMMENT 'Representa en que expediente se guardara.',
    IDEMPEMI int NOT NULL COMMENT 'Contiene el identificador del empleado que emitió el documento.',
    ESTDOC char(1) NOT NULL COMMENT 'Contiene el estado del documento.',
    ARCDOC blob NULL,
    CONSTRAINT DOCUMENTO_pk PRIMARY KEY (IDDOC)
);

-- Table: EMPLEADO
CREATE TABLE EMPLEADO (
    IDEMP int NOT NULL AUTO_INCREMENT COMMENT 'Contiene el identificador del empleado.',
    NOMEMP char(20) NULL COMMENT 'Contiene el nombre del empleado.',
    APEPATEMP char(15) NULL COMMENT 'Contiene el apellido paterno del empleado.',
    APEMATEMP char(15) NULL COMMENT 'Contiene el apellido materno del empleado,',
    POSEMP char(50) NULL COMMENT 'Contiene la posición de trabajo del empleado',
    DNIEMP char(8) NULL COMMENT 'Contiene el documento nacional de identidad del empleado.',
    IDARE int NOT NULL COMMENT 'Contiene el identificador del área, para saber donde trabaja.',
    USEREMP varchar(15) NULL COMMENT 'Nombre de usuario del empleado',
    PASSEMP varchar(15) NULL COMMENT 'Contraseña de usuario del empleado.',
    ESTEMP char(1) NOT NULL COMMENT 'Contiene el estado del empleado.',
    SEXEMP char(1) NOT NULL COMMENT 'Genero de Empleado.',
    CONSTRAINT EMPLEADO_pk PRIMARY KEY (IDEMP)
);

-- Table: ENVIOS
CREATE TABLE ENVIOS (
    IDENV int NOT NULL AUTO_INCREMENT COMMENT 'Contiene el identificador de envió.',
    IDDOC int NOT NULL COMMENT 'Contiene el código del documento.',
    FECENV date NULL COMMENT 'Contiene la fecha de envió del documento.',
    IDEMPREC int NOT NULL COMMENT 'Contiene el identificador del empleado a quien se le enviará el documento.',
    IDAREREC int NOT NULL COMMENT 'Contiene el identificador del área al que se le enviara el documento.',
    ESTENV char(1) NOT NULL COMMENT 'Contiene el estado del envió.',
    CONSTRAINT ENVIOS_pk PRIMARY KEY (IDENV)
);

-- Table: EXPEDIENTE
CREATE TABLE EXPEDIENTE (
    IDEXP int NOT NULL AUTO_INCREMENT COMMENT 'Contiene le identificador del expediente.',
    NUMEXP int NULL COMMENT 'Contiene el numero del expediente donde se encuentra el documento.',
    FECEXP date NULL COMMENT 'Contiene la fecha cuando ingreso al expediente.',
    ASUEXP char(200) NOT NULL COMMENT 'Contiene el asunto del expediente.',
    ESTEXP char(1) NOT NULL COMMENT 'Contiene el estado del expediente,',
    CONSTRAINT EXPEDIENTE_pk PRIMARY KEY (IDEXP)
);

-- Table: TIPO_DOCUMENTO
CREATE TABLE TIPO_DOCUMENTO (
    IDTIPDOC int NOT NULL AUTO_INCREMENT COMMENT 'Contiene el identificador del tipo de documento.',
    NOMTIPDOC varchar(150) NOT NULL COMMENT 'Contiene el nombre de tipo de documento.',
    ESTTIPDOC char(1) NOT NULL COMMENT 'Contiene el estado del tipo de documento.',
    CONSTRAINT TIPO_DOCUMENTO_pk PRIMARY KEY (IDTIPDOC)
);

-- foreign keys
-- Reference: DOCUMENTO_EMPLEADO (table: DOCUMENTO)
ALTER TABLE DOCUMENTO ADD CONSTRAINT DOCUMENTO_EMPLEADO FOREIGN KEY DOCUMENTO_EMPLEADO (IDEMPEMI)
    REFERENCES EMPLEADO (IDEMP);

-- Reference: DOCUMENTO_EXPEDIENTE (table: DOCUMENTO)
ALTER TABLE DOCUMENTO ADD CONSTRAINT DOCUMENTO_EXPEDIENTE FOREIGN KEY DOCUMENTO_EXPEDIENTE (IDEXP)
    REFERENCES EXPEDIENTE (IDEXP);

-- Reference: DOCUMENTO_TIPO_DOCUMENTO (table: DOCUMENTO)
ALTER TABLE DOCUMENTO ADD CONSTRAINT DOCUMENTO_TIPO_DOCUMENTO FOREIGN KEY DOCUMENTO_TIPO_DOCUMENTO (IDTIPDOC)
    REFERENCES TIPO_DOCUMENTO (IDTIPDOC);

-- Reference: ENVIOS_AREAGEN (table: ENVIOS)
ALTER TABLE ENVIOS ADD CONSTRAINT ENVIOS_AREAGEN FOREIGN KEY ENVIOS_AREAGEN (IDAREREC)
    REFERENCES AREA (IDARE);

-- Reference: ENVIOS_DOCUMENTO (table: ENVIOS)
ALTER TABLE ENVIOS ADD CONSTRAINT ENVIOS_DOCUMENTO FOREIGN KEY ENVIOS_DOCUMENTO (IDDOC)
    REFERENCES DOCUMENTO (IDDOC);

-- Reference: ENVIOS_EMPLEADO (table: ENVIOS)
ALTER TABLE ENVIOS ADD CONSTRAINT ENVIOS_EMPLEADO FOREIGN KEY ENVIOS_EMPLEADO (IDEMPREC)
    REFERENCES EMPLEADO (IDEMP);

-- Reference: TRABAJADOR_AREA (table: EMPLEADO)
ALTER TABLE EMPLEADO ADD CONSTRAINT TRABAJADOR_AREA FOREIGN KEY TRABAJADOR_AREA (IDARE)
    REFERENCES AREA (IDARE);

-- End of file.

