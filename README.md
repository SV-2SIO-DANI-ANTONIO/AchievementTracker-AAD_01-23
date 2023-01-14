# Actividad Acceso a Datos Practicas

### Requisitos obligatorios:
- [x] El modelo de datos estará compuesto de, al menos, 3 clases y tendrán que existir relaciones entre ellas. Cada clase tendrá, al menos, 3 atributos (String, int, float, boolean y algún tipo para almacenar fechas).
- [x] Se tendrá que poder realizar, el menos, las operaciones CRUD sobre cada una de las clases. Se controlarán, al menos, los errores 400, 404 y 500
- [x] Añade, a alguna de las operaciones GET, la opción de filtrar por algún campo
- [x] Prepara una colección Postman que permita probar todas las operaciones desarrolladas
- [x] Configura en el proyecto la librería logback para que la aplicación web cuente con un log. Añade trazas en el código de forma que permita seguir el rastro de ejecución en el log (para todas las operaciones que se puedan realizar y también para los casos en los que se recojan errores)

### Requisitos opcionales:
- [ ] Añade una operación PATCH para cada una de las clases del modelo
- [x] Incluye reglas de validación para los atributos del modelo de datos
- [x] Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Escribe el fichero README.md para explicar cómo poner en marcha el proyecto. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo
- [x] Añade 2 nuevos endpoints a la aplicación (sin repetir método) que realicen nuevas operaciones con los datos y que requieran el uso de DTOs y/o utilizar las relaciones entre las clases
- [ ] Securiza algunas de tus operaciones de la API con un token JWT
- [ ] Utiliza consultas JPQL para extraer la información de la base de datos
- [x] Utiliza consultas SQL nativas para extraer la información de la base de datos
- [x] Añade otra clase más al modelo de datos con sus respectivas operaciones CRUD (inclúyelas también en la colección Postman)
- [ ] Parametriza la colección Postman para que pueda ser ejecutada con el Runner de Postman y realizar una prueba completa de la API

### Como ejecutar la aplicacion:
- Tener instalado y configurado correctamente Java 17 y Maven
- (Opcional) Tener instalado Postman y haber importado la coleccion
- Descarga el codigo fuente del proyecto
- Importa el codigo en el IDE de tu preferencia
- Ejecuta en el terminal de tu IDE el comando `mvn spring-boot:run`
- Si todo ha ido correctamente, la aplicacion deberia estar funcionando correctamente y puedes probar los endpoints de la API en Postman o cualquier otra aplicacion similar