# Cine Prueba
**Esta práctica consiste en hacerla en parejas (obligatoriamente), usando Git y GitHub como método para colaborar en base a Pull Request y aplicando GitFlow.**

**Tenemos nuestro cine que tiene una estructura de butacas agrupadas en 5 filas y 7 columnas. Las filas están numeradas de la A a la E y las columnas de 1 a la 7. Por lo tanto, una butaca queda identificada como A4 (fila 1, columna 4).**

**Además, las butacas pueden ser normales, cuyo precio es de 5€ la entrada, o VIP, cuyo precio es 8€. Una butaca puede estar activa, en mantenimiento o fuera de servicio. Además, obviamente, puede estar libre, en reserva y ocupada.**

**Por otro lado, tenemos complementos, que pueden ser de las categorías bebida, comida y otros. Como bebida tenemos agua (2€) y refrescos (3€), y como comida tenemos palomitas (3€), frutos secos (2€) y patatas (2,5€).**

**Queremos realizar un programa para gestionar nuestro cine usando bases de datos con SQLite y ficheros.**

**Nuestra aplicación hará uso de un menú para:**

1. Comprar entrada: si hay butacas libres, reservará una butaca con el número de socio. Se podrán añadir un máximo de 3 complementos. El número de socio es LLLNNN (L es letra y N es número). Se obtiene un fichero llamado `entrada_Butaca_NSocio_Fecha.html` (entrada, más el identificador de la butaca, más el identificador del socio, más la fecha de la compra).

2. Devolver entrada: devuelve una entrada liberando los recursos asociados.

3. Estado del cine: muestra el estado del cine.

4. Obtener recaudación: se obtiene la recaudación dada una fecha válida dada en formato AAAA/MM/DD.

5. Importar complementos: importa complementos en base a un fichero CSV dado.

6. Exportar estado del cine: exporta el estado del cine dada una fecha válida AAAA/MM/DD en un fichero JSON dado.

7. Configurar butacas: configura las butacas en base a un fichero CSV dado.

8. Actualizar butaca: cambia la información de una butaca dado su identificador: LN.

**Tablas a tener en cuenta:**

- Butacas
- Complementos
- Ventas (y asociadas)
- Otras que tú puedas considerar.

**No olvides la integridad de claves primarias y referencial donde sea necesario. Debes razonar cuando es necesario delegar la creación de la clave primaria en la bases de datos y cuándo la propia lógica de la aplicación sea la encargada de ello.**

**Otras consideraciones:**

- Al menos un servicio debe usar excepciones adaptadas al dominio.
- Al menos un servicio debe usar Mónadas, ROP y Errores orientados al dominio.
- Al menos un repositorio debe usar un manejador de bases de datos y mapeo manual.
- Al menos un repositorio debe usar SQLDelight.
- Todos los repositorios y servicios deben estar totalmente testeados con los casos correctos e incorrectos.
- Realiza una arquitectura orientada al dominio.
- Usa mecanismos automatizados de inyección de dependencias.

**Se debe entregar:**

- Especificación de requisitos funcionales, no funcionales y de información.
- Diagrama de casos de uso.
- Caso de usos de Comprar entrada y Actualizar Butaca.
- Diagrama de clases.
- Diagramas de secuencias de las operaciones CRUD de productos.
- Diagrama de secuencia de una venta de entrada con complementos.
- Código comentado usando Kdoc y Dokka.
- Estimación del coste de la aplicación.
- Documento en PDF explicando cada elemento.
- Presentación en Youtube en modo oculto (adjuntando el link).
- Presentación en PowerPoint explicando los elementos más importantes para la resolución del mismo.
- GitHub del proyecto.


## Requisitos

### Especificación de Requisitos Funcionales:

1. **Comprar Entrada:**
   - Descripción: Permite al usuario comprar una entrada para una butaca específica, reservándola con un número de socio. Se pueden agregar hasta 3 complementos.
   - Flujo:
     1. El usuario selecciona una butaca disponible.
     2. El usuario ingresa su número de socio.
     3. El usuario selecciona los complementos deseados.
     4. Se genera un archivo HTML que contiene los detalles de la entrada (butaca, socio, complementos y fecha de compra).

2. **Devolver Entrada:**
   - Descripción: Permite al usuario devolver una entrada previamente comprada, liberando los recursos asociados.
   - Flujo:
     1. El usuario ingresa el número de la entrada a devolver.
     2. Se libera la butaca y los complementos asociados.

3. **Estado del Cine:**
   - Descripción: Muestra el estado actual del cine, incluyendo la disponibilidad de las butacas y su estado (libre, reservada u ocupada).
   - Flujo:
     1. Se muestra una representación visual del cine con el estado de cada butaca.

4. **Obtener Recaudación:**
   - Descripción: Calcula la recaudación total del cine en una fecha específica.
   - Flujo:
     1. El usuario ingresa una fecha válida en formato AAAA/MM/DD.
     2. Se calcula la suma total de las ventas realizadas en esa fecha.

5. **Importar Complementos:**
   - Descripción: Importa complementos desde un archivo CSV dado y los agrega a la base de datos.
   - Flujo:
     1. Se lee el archivo CSV.
     2. Se importan los complementos a la base de datos.

6. **Exportar Estado del Cine:**
   - Descripción: Exporta el estado actual del cine en un archivo JSON, para una fecha específica.
   - Flujo:
     1. El usuario ingresa una fecha válida en formato AAAA/MM/DD.
     2. Se genera un archivo JSON con el estado del cine en esa fecha.

7. **Configurar Butacas:**
   - Descripción: Configura las butacas del cine utilizando un archivo CSV dado.
   - Flujo:
     1. Se lee el archivo CSV.
     2. Se configuran las butacas según la información proporcionada.

8. **Actualizar Butaca:**
   - Descripción: Permite cambiar la información de una butaca dada su identificador.
   - Flujo:
     1. El usuario ingresa el identificador de la butaca a actualizar.
     2. Se actualiza la información de la butaca según los datos proporcionados.

### Especificación de Requisitos No Funcionales:

1. **Base de Datos SQLite:**
   - La aplicación debe utilizar SQLite para gestionar la persistencia de los datos.

2. **Git y GitHub:**
   - El desarrollo debe realizarse utilizando Git y GitHub, con Pull Requests para la colaboración entre parejas.

3. **Mecanismos de Inyección de Dependencias:**
   - Se deben utilizar mecanismos automatizados de inyección de dependencias para mejorar la modularidad y la testabilidad del código.

4. **Manejo de Excepciones Adaptadas al Dominio:**
   - Al menos un servicio debe implementar excepciones adaptadas al dominio para manejar errores de manera eficiente y clara.

### Especificación de Requisitos de Información:

1. **Tablas de la Base de Datos:**
   - Se deben crear las siguientes tablas en la base de datos:
     - Butacas: Para almacenar información sobre las butacas, incluyendo su estado y tipo.
     - Complementos: Para almacenar información sobre los complementos disponibles.
     - Ventas: Para almacenar información sobre las entradas vendidas, incluyendo los detalles de la butaca, el socio y la fecha de compra.

2. **Integridad de Claves Primarias y Referenciales:**
   - Se debe garantizar la integridad de las claves primarias y referencias entre las tablas para mantener la consistencia de los datos.

3. **Formato de Archivos Generados:**
   - Los archivos generados (HTML, JSON) deben seguir un formato específico que contenga la información relevante de manera clara y estructurada.

## Casos de uso

### Comprar Entrada

#### Descripción:
Este caso de uso describe el proceso que sigue un usuario para comprar una entrada para una butaca en el cine, así como la posibilidad de agregar complementos a la compra.

#### Actores:
- Usuario

#### Flujo Principal:
1. El usuario selecciona la opción "Comprar Entrada" en el menú de la aplicación.
2. El sistema muestra las butacas disponibles en el cine, junto con su estado (libre, reservada u ocupada).
3. El usuario elige una butaca disponible.
4. El sistema solicita al usuario que ingrese su número de socio.
5. El usuario introduce su número de socio en el formato LLLNNN (letras y números).
6. El sistema valida el número de socio.
7. El sistema muestra las opciones de complementos disponibles (bebidas, comida u otros).
8. El usuario selecciona hasta un máximo de 3 complementos.
9. El sistema calcula el precio total de la entrada, incluyendo los complementos seleccionados.
10. El usuario confirma la compra.
11. El sistema registra la compra, reservando la butaca, asociándola al número de socio y guardando los detalles de los complementos.
12. El sistema genera un archivo HTML que contiene los detalles de la entrada (butaca, socio, complementos y fecha de compra).
13. El sistema muestra un mensaje de confirmación de la compra.

#### Extensiones:
- 6a. Si el número de socio no es válido:
   1. El sistema muestra un mensaje de error.
   2. Se vuelve al paso 4.

#### Flujo Alternativo:
- 7a. Si el usuario no selecciona ningún complemento:
   1. Se salta el paso 9 y se calcula solo el precio de la entrada.
   2. Se continúa desde el paso 10.

### Actualizar Butaca

#### Descripción:
Este caso de uso describe el proceso que sigue un usuario para actualizar la información de una butaca específica en el cine.

#### Actores:
- Usuario

#### Flujo Principal:
1. El usuario selecciona la opción "Actualizar Butaca" en el menú de la aplicación.
2. El sistema solicita al usuario que ingrese el identificador de la butaca que desea actualizar.
3. El usuario ingresa el identificador de la butaca.
4. El sistema busca la butaca en la base de datos.
5. El sistema muestra la información actual de la butaca (tipo, estado, etc.).
6. El usuario proporciona los nuevos datos para la butaca (tipo, estado, etc.).
7. El sistema valida los nuevos datos proporcionados.
8. El sistema actualiza la información de la butaca en la base de datos.
9. El sistema muestra un mensaje de confirmación de la actualización.

#### Extensiones:
- 3a. Si el identificador de la butaca no es válido o no se encuentra en la base de datos:
   1. El sistema muestra un mensaje de error.
   2. Se vuelve al paso 2.

#### Precondiciones:
- El usuario tiene los permisos necesarios para actualizar la información de la butaca.
- La butaca a actualizar existe en la base de datos.
