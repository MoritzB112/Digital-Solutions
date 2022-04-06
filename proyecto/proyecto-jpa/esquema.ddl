CREATE TABLE AUTORIZACION (TIPO VARCHAR, EM_id BIGINT NOT NULL, PA_ID BIGINT NOT NULL, PRIMARY KEY (EM_id, PA_ID))
CREATE TABLE PERSONA_AUTORIZADA (ID BIGINT NOT NULL, FECHAFIN DATE, FECHAINICIO DATE, IDENTIFICACION VARCHAR NOT NULL UNIQUE, APELLIDOS VARCHAR NOT NULL, DIRECCION VARCHAR NOT NULL, ESTADO VARCHAR, FECHA_NACIMEINTEO DATE, NOMBRE VARCHAR NOT NULL, PASSWORD VARCHAR, SALT VARCHAR, PRIMARY KEY (ID))
CREATE TABLE CLIENTE (id BIGINT NOT NULL, DTYPE VARCHAR(31), ciudad VARCHAR NOT NULL, codigoPostal INTEGER NOT NULL, direccion VARCHAR NOT NULL, estado VARCHAR NOT NULL, fecha_alta DATE NOT NULL, fecha_baja DATE, identificacion VARCHAR NOT NULL UNIQUE, pais VARCHAR NOT NULL, PASSWORD VARCHAR, SALT VARCHAR, tipo_cliente VARCHAR NOT NULL, PRIMARY KEY (id))
CREATE TABLE EMPRESA (id BIGINT NOT NULL, razon_social VARCHAR NOT NULL, PRIMARY KEY (id))
CREATE TABLE CUENTA (IBAN VARCHAR NOT NULL, DTYPE VARCHAR(31), SWIFT VARCHAR, PRIMARY KEY (IBAN))
CREATE TABLE CUENTA_FINTECH (IBAN VARCHAR NOT NULL, CLASIFICACION VARCHAR, ESTADO VARCHAR NOT NULL, FECHA_APERTURA DATE NOT NULL, FECHA_CIERRE DATE, CL_id BIGINT, PRIMARY KEY (IBAN))
CREATE TABLE CUENTA_REFERENCIA (IBAN VARCHAR NOT NULL, ESTADO VARCHAR, FECHA_APERTURA DATE, NOMBREBANCO VARCHAR NOT NULL, PAIS VARCHAR, SALDO DOUBLE NOT NULL, SUCURSAL VARCHAR, DIV_ABREVIATURA VARCHAR, PRIMARY KEY (IBAN))
CREATE TABLE DEPOSITADO_EN (SALDO DOUBLE NOT NULL, CR_IBAN VARCHAR NOT NULL, PA_IBAN VARCHAR NOT NULL, PRIMARY KEY (CR_IBAN, PA_IBAN))
CREATE TABLE DIVISA (ABREVIATURA VARCHAR NOT NULL, CAMBIOEURO DOUBLE NOT NULL, NOMBRE VARCHAR NOT NULL, SIMBOLO VARCHAR, PRIMARY KEY (ABREVIATURA))
CREATE TABLE INDIVIDUAL (id BIGINT NOT NULL, apellido VARCHAR NOT NULL, fecha_nacimiento DATE, nombre VARCHAR NOT NULL, PRIMARY KEY (id))
CREATE TABLE POOLED_ACCOUNT (IBAN VARCHAR NOT NULL, PRIMARY KEY (IBAN))
CREATE TABLE SEGREGADA (IBAN VARCHAR NOT NULL, COMISION DOUBLE, CR_IBAN VARCHAR, PRIMARY KEY (IBAN))
CREATE TABLE TRANSACCION (ID_UNICO BIGINT NOT NULL, TIPO VARCHAR NOT NULL, CANTIDAD DOUBLE NOT NULL, COMISION DOUBLE, FECHAEJECUCION DATE, FECHAINSTRUCCION DATE NOT NULL, INTERNACIONAL BOOLEAN, DESTINO_IBAN VARCHAR, DIVEM_ABREVIATURA VARCHAR, DIVREC_ABREVIATURA VARCHAR, ORIGEN_IBAN VARCHAR, PRIMARY KEY (ID_UNICO))
ALTER TABLE AUTORIZACION ADD CONSTRAINT FK_AUTORIZACION_EM_id FOREIGN KEY (EM_id) REFERENCES CLIENTE (id)
ALTER TABLE AUTORIZACION ADD CONSTRAINT FK_AUTORIZACION_PA_ID FOREIGN KEY (PA_ID) REFERENCES PERSONA_AUTORIZADA (ID)
ALTER TABLE EMPRESA ADD CONSTRAINT FK_EMPRESA_id FOREIGN KEY (id) REFERENCES CLIENTE (id)
ALTER TABLE CUENTA_FINTECH ADD CONSTRAINT FK_CUENTA_FINTECH_CL_id FOREIGN KEY (CL_id) REFERENCES CLIENTE (id)
ALTER TABLE CUENTA_FINTECH ADD CONSTRAINT FK_CUENTA_FINTECH_IBAN FOREIGN KEY (IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE CUENTA_REFERENCIA ADD CONSTRAINT FK_CUENTA_REFERENCIA_IBAN FOREIGN KEY (IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE CUENTA_REFERENCIA ADD CONSTRAINT FK_CUENTA_REFERENCIA_DIV_ABREVIATURA FOREIGN KEY (DIV_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
ALTER TABLE DEPOSITADO_EN ADD CONSTRAINT FK_DEPOSITADO_EN_PA_IBAN FOREIGN KEY (PA_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE DEPOSITADO_EN ADD CONSTRAINT FK_DEPOSITADO_EN_CR_IBAN FOREIGN KEY (CR_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE INDIVIDUAL ADD CONSTRAINT FK_INDIVIDUAL_id FOREIGN KEY (id) REFERENCES CLIENTE (id)
ALTER TABLE POOLED_ACCOUNT ADD CONSTRAINT FK_POOLED_ACCOUNT_IBAN FOREIGN KEY (IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE SEGREGADA ADD CONSTRAINT FK_SEGREGADA_CR_IBAN FOREIGN KEY (CR_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE SEGREGADA ADD CONSTRAINT FK_SEGREGADA_IBAN FOREIGN KEY (IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_DESTINO_IBAN FOREIGN KEY (DESTINO_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_ORIGEN_IBAN FOREIGN KEY (ORIGEN_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_DIVEM_ABREVIATURA FOREIGN KEY (DIVEM_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_DIVREC_ABREVIATURA FOREIGN KEY (DIVREC_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)