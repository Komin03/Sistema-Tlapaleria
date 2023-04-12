USE ferreteria;
-- Insertar productos
INSERT INTO productos (nombre, descripcion, precio, existencias)
VALUES
('Martillo', 'Martillo de 16 oz con mango de fibra de vidrio', 12.99, 50),
('Destornillador Phillips', 'Destornillador Phillips #2 con mango de goma', 4.50, 100),
('Sierra de mano', 'Sierra de mano con hoja de 18 pulgadas y mango ergonómico', 24.99, 25),
('Llave ajustable', 'Llave ajustable de 10 pulgadas con mandíbulas de acero endurecido', 19.99, 30),
('Pistola de grapas', 'Pistola de grapas de servicio pesado con capacidad para grapas de 1/4" a 9/16"', 34.99, 20),
('Nivel láser', 'Nivel láser de línea cruzada con trípode y bolsa de transporte', 89.99, 10),
('Taladro inalámbrico', 'Taladro inalámbrico de 20 voltios con dos baterías y cargador', 79.99, 15),
('Lijadora orbital', 'Lijadora orbital de 5 pulgadas con bolsa de polvo y adaptador de vacío', 49.99, 20),
('Caja de herramientas', 'Caja de herramientas de plástico resistente con bandeja extraíble', 29.99, 5),
('Juego de llaves de tubo', 'Juego de llaves de tubo de 10 piezas en tamaños de 1/4" a 7/8"', 39.99, 8),
('Sierra de calar', 'Sierra de calar con velocidad variable y hojas de sierra surtidas', 64.99, 12),
('Juego de destornilladores de precisión', 'Juego de destornilladores de precisión de 6 piezas con puntas magnéticas', 12.99, 40),
('Pala', 'Pala de jardín de acero con mango de madera', 14.99, 25),
('Pico', 'Pico de acero con mango de fibra de vidrio', 16.99, 20),
('Alicate', 'Alicate de corte diagonal con mandíbulas de acero de alta resistencia', 8.99, 60),
('Llave inglesa', 'Llave inglesa de 6 pulgadas con mandíbulas ajustables', 6.99, 80),
('Juego de brocas', 'Juego de brocas de 29 piezas con estuche de transporte', 24.99, 15),
('Cinta métrica', 'Cinta métrica de 25 pies con bloqueo automático', 9.99, 50),
('Juego de llaves allen', 'Juego de llaves allen de 10 piezas en tamaños métricos y estándar', 14.99, 30);


-- Insertar clientes
INSERT INTO clientes (nombre, direccion, telefono)
VALUES ('Andres', 'Los amores de don juan', '123456789'),
('Raul', 'Chavarria', '987654321');

-- Insertar trabajadores
INSERT INTO trabajadores (nombre, direccion, telefono, salario, fecha_contratacion)
VALUES ('Martinez Peres Alberto', 'Blanca 148 Los amores de don Juan Tolcayuca', '77911548754', 2000.00, '2021-01-01'),
 ('Perez Rodrigues Saul', 'Esmeralda 175 Los amores de don Juan Tolcayuca', '222222222', 2500.00, '2021-02-01');

-- Insertar repartidores
INSERT INTO repartidores (id_trabajador)
VALUES (1),
(2);

-- Insertar proveedores
INSERT INTO proveedores (nombre, direccion, telefono)
VALUES ('Proveedor 1', 'Dirección del proveedor 1', '333333333'),
('Proveedor 2', 'Dirección del proveedor 2', '444444444');

-- Insertar pedidos a proveedores
INSERT INTO pedidos (id_proveedor, fecha_pedido, fecha_entrega)
VALUES (1, '2022-01-01', '2022-01-15'),
 (2, '2022-02-01', '2022-02-15');

-- Insertar ventas
INSERT INTO ventas (fecha, id_cliente, id_repartidor)
VALUES ('2022-03-01', 1, 1),
('2022-03-02', 2, NULL);

-- Insertar detalles de venta
INSERT INTO detalles_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (1, 1, 5, 10.50),
(1, 2, 3, 15.75),
(2, 3, 10, 20.00);

-- Insertar detalles de pedido a proveedores
INSERT INTO detalles_pedido (id_pedido, id_producto, cantidad, precio_unitario)
VALUES (1, 1, 20, 8.50),
 (1, 2, 10, 13.00);