# ORGANIZADOR DE FACTURAS

Programa organizador de facturas por tipos de gasto y generador de reportes por periodos.

INSTALACIÓN PARA USUARIOS FINALES (PRINCIPIANTES):

1. Descargar el instalador que se encuentra en el repositorio con el nombre "FactSetup.exe".
![captura 2](https://user-images.githubusercontent.com/28027396/27064364-975b65d0-4fbc-11e7-8e27-4b845e1c87c6.PNG)

2. Abrir el instalador y proceder a instalar el programa

  *-------IMPORTANTE--------*
Para el correcto funcionamiento del programa se requiere cambiar la ruta de instalación que se encuentra por defecto "C:\\ProgramFiles" en su lugar elegir un directorio alterno en otro disco duro disponible o alguna partición dando click en el botón "Examinar". También se puede seleccionar un directorio como del usuario como el Escritorio. 

Ruta por defecto:

![captura](https://user-images.githubusercontent.com/28027396/27064291-3dd8ac20-4fbc-11e7-8c96-146ee05c6265.PNG)

Ruta cambiada:

![captura 3](https://user-images.githubusercontent.com/28027396/27064406-c705f35e-4fbc-11e7-8cb6-6a32a3206a5b.PNG)
   
3. Una vez seleccionada la carpeta de instalación, dar click en siguiente y seleccionar la opción de "Crear un icono en el escritorio" si se desea.

![captura 4](https://user-images.githubusercontent.com/28027396/27064464-1a66c186-4fbd-11e7-87d3-377e4ae3e145.PNG)

4. Dar click en siguiente y luego dar click en instalar. 
5. Una vez terminado el proceso de instalación proceder a ejecutar el programa.


INSTALACIÓN PARA USUARIOS COLABORATIVOS (CÓDIGO FUENTE):

Herramientas utilizadas son:

●	SQLite y SQLite Manager como gestor de base de datos.
 ●	Netbeans como IDE de desarrollo de la aplicación.
 ●	Mozilla Firefox, para la ejecución de SQLite Manager

1. Para poder utilizar el programa se debe clonar el repositorio en un directorio determinado, para esto se puede utilizar la herramienta brindada por GitHub.
2. Importar el proyecto o copiar la carpeta creada en el directorio donde se maneje los proyectos en el IDE para la ejecución, de preferencia NetBeans para evitar problemas de compatibilidad. 
3. Ejecutar el proyecto.
4. Ir a la pestaña de Seleccionar para buscar las facturas que vayan a ser ingresadas y continuar con el ingreso de datos, así como el desglose de los gastos realizados por su respectivo tipo. 
5. Para observar los reportes, ir a la pestaña de Reportes y seleccionar el tipo del mismo.
6. En el caso de querer visualizar el archivo de la base de datos, descargar el complemento para Mozila Firefox "SQLite Manager", indicar el directorio donde se encuentra la base de datos (en la misma carpeta donde se guardo el proyecto) e indicar el archivo ".sqlite".
7. Luego se presentará la base de datos con sus respectivas tablas e información almacenada.

NOTA: en el caso de tener problemas con las librerias necesarias para la compilación y ejecución, en la carpeta "lib" se encuentran las librerias necesarias para el funcionamiento correcto.







