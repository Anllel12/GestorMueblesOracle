CREATE OR REPLACE TYPE t_mueble AS OBJECT (
    modelo INT(10),
	nombre VARCHAR2(100),
	precio INT(10),
	paquetes INT(5),
	cantidad INT(5)
	);

/

CREATE OR REPLACE TYPE t_tamano AS OBJECT (
	id INT(10),
	ancho INT(10),
	fondo INT(10),
    altura INT(10),
	peso_balda INT(10),
	mueble REF t_mueble
	);

/

CREATE OR REPLACE TYPE t_material AS OBJECT (
	id INT(10),
	principal VARCHAR(100),
	secundario VARCHAR(100),
	mueble REF t_mueble
	);

/

CREATE TABLE mueble OF t_mueble;

/

CREATE TABLE tamano OF t_tamano;

/

CREATE TABLE material OF t_material;

/

INSERT INTO mueble VALUES
	('00278578', 'HYLLIS', '10', '1', '1');
   
/

INSERT INTO mueble VALUES
	('60333850', 'BROR', '99', '1', '1');
   
/

INSERT INTO mueble VALUES
	('30409295', 'KOLBJÖRN', '69', '1', '1');
   
/

INSERT INTO tamano VALUES
	(1, '60', '27', '140', '25', (SELECT REF(m) FROM mueble m WHERE m.modelo = '00278578'));

/

INSERT INTO tamano VALUES
	(2, '85', '55', '88', '50', (SELECT REF(m) FROM mueble m WHERE m.modelo = '60333850'));

/

INSERT INTO tamano VALUES
	(3, '80', '35', '81', '55', (SELECT REF(m) FROM mueble m WHERE m.modelo = '30409295'));
   
/

INSERT INTO material VALUES
	(1, 'Acero galvanizado', 'Plástico amídico', (SELECT REF(m) FROM mueble m WHERE m.modelo = '00278578'));

/

INSERT INTO material VALUES
	(2, 'Acero', 'Contrachapado de pino', (SELECT REF(m) FROM mueble m WHERE m.modelo = '60333850'));

/

INSERT INTO material VALUES
	(3, 'Acero galvanizado', 'Revestimiento en polvo de poliéster', (SELECT REF(m) FROM mueble m WHERE m.modelo = '30409295'));
   
/

SELECT * FROM mueble m;
SELECT t.id, t.ancho, t.fondo, t.altura, t.peso_balda, t.mueble.modelo FROM tamano t;
SELECT m.id, m.principal, m.secundario, m.mueble.modelo FROM material m;
