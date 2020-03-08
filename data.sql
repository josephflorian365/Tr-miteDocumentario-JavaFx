use doqinter;
INSERT INTO area( nomare, estare) 
VALUES 
("Sistemas","A"),
("Logistica","A"),
("Administración","A");


INSERT INTO tipo_documento(NOMTIPDOC, ESTTIPDOC)
 VALUES 
 ("Informe","A"),
  ("Carta","A"),
    ("Memorandum","A"),
    ("Solicitud","A");
    
    
INSERT INTO expediente(NUMEXP, FECEXP, ASUEXP, ESTEXP) 
VALUES
 (01,"2020-01-01", "Vacaciones de Empleado","A"),
  (02,"2020-01-02", "Pago a la telefonica","A"),
  (03,"2020-01-03", "Compra de inmuebles","A");
    
INSERT INTO empleado(NOMEMP, APEPATEMP, APEMATEMP, POSEMP, DNIEMP, IDARE, USEREMP, PASSEMP, ESTEMP, SEXEMP)
 VALUES
 ("Franco","Levano","Silva","Jefe de Sistemas","77982341",1,"ADMIN","QWSX","A","M"),
  ("Lucio","Huaman","Tirado","Jefe de Logistica","77982111",2,"USER","QWSX","A","M");
  
INSERT INTO documento(ASUDOC, IDTIPDOC, IDEXP, IDEMPEMI, ESTDOC, ARCDOC) 
VALUES ("Compra de Impresora",4,3,1,"A",null);

INSERT INTO envios( IDDOC, FECENV, IDEMPREC, IDAREREC, ESTENV) 
VALUES (1,"2020-01-05",2,2,"A")
  
    
  
