
-- Usar la base de datos
USE ferreteria;

-- Crear la tabla de productos
CREATE TABLE productos (
    idProducto INT NOT NULL PRIMARY KEY auto_increment,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10, 2) NOT NULL,
    existencias INT NOT NULL
);

-- Crear la tabla de clientes
CREATE TABLE clientes (
    idCliente INT NOT NULL PRIMARY KEY auto_increment,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

-- Crear la tabla de trabajadores
CREATE TABLE trabajadores (
    idTrabajadores INT NOT NULL PRIMARY KEY auto_increment,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    fecha_contratacion DATE NOT NULL
);

-- Crear la tabla de repartidores
CREATE TABLE repartidores (
    idRepartidores INT NOT NULL PRIMARY KEY auto_increment,
    id_trabajador INT NOT NULL,
    FOREIGN KEY (id_trabajador) REFERENCES trabajadores(idTrabajadores)
);

-- Crear la tabla de proveedores
CREATE TABLE proveedores (
    idProveedores INT NOT NULL PRIMARY KEY auto_increment,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

-- Crear la tabla de pedidos a proveedores
CREATE TABLE pedidos (
    idPedidos INT NOT NULL PRIMARY KEY auto_increment,
    id_proveedor INT NOT NULL,
    fecha_pedido DATE NOT NULL,
    fecha_entrega DATE,
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(idProveedores)
);



-- Crear la tabla de ventas
CREATE TABLE ventas (
    idVentas INT NOT NULL PRIMARY KEY auto_increment,
    fecha DATE NOT NULL,
    id_cliente INT NOT NULL,
    id_repartidor INT,
    FOREIGN KEY (id_cliente) REFERENCES clientes(idCliente),
    FOREIGN KEY (id_repartidor) REFERENCES repartidores(idRepartidores)
);

-- Crear la tabla de detalles de venta
CREATE TABLE detalles_venta (
    idDetallesVenta INT PRIMARY KEY NOT NULL auto_increment,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES ventas(idVentas),
    FOREIGN KEY (id_producto) REFERENCES productos(idProducto)
);

-- Crear la tabla de detalles de pedido a proveedores
CREATE TABLE detalles_pedido (
    idDetallesPedido INT PRIMARY KEY NOT NULL auto_increment,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(idPedidos),
    FOREIGN KEY (id_producto) REFERENCES productos(idProducto)
);