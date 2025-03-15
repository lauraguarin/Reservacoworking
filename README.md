Proyecto ReservaCoworking
 
¿Qué  es lo que hace ?
 
Crear: Llenas un formulario con tu nombre, la fecha, el espacio que quieres y por cuánto tiempo.
 
Listar: Te muestra todas las reservas que ya hiciste, ahí bien ordenaditas en una tabla.
 
Eliminar: Si ya no necesitas la reserva, la puedes borrar sin problema.
 
Modelos: Son las partes que definen cómo se guardan las reservas.
 
Vistas (JSP): Son las páginas que ves en el navegador, como el formulario y la lista.
 
 
Versiones que tengo en mi compu
 
JDK 19: Para desarrollar el proyecto.
 
Apache Tomcat 8.5.96: El servidor donde corre la app.
 
NetBeans 19: Para desarrollar y probar.
 


Despliegue
 
1. Clonar el proyecto: Descárgalo a tu compu con este comando:
 
git clone https://github.com/lauraguarin/Reservacoworking.git
 
 
2. Abrir en NetBeans: Abre NetBeans y carga el proyecto que acabas de clonar.
 
 
3. Configura la base de datos:
 
Asegúrate de que MySQL esté funcionando.
 
Crea una base de datos llamada coworking.
 
Usa PhpMyAdmin para armar una tabla de reservas con estas columnas: id, nombre, fecha, espacio, duración.
 
 
 
4. Corre Tomcat: Asegúrate de que Tomcat esté configurado en NetBeans y lánzalo desde ahí. Fácil.
 
 
5. Acceder a la app: Abre tu navegador y ve a:
 
http://localhost:8080/Reservacoworking/
 
 
6. Listo, pruébalo: Entra, crea tus reservas, ve cómo aparecen en la lista y si ya no las necesitas, eliminarlos
