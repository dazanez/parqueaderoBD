# Mejoras posibles
- La funcion Conexion.ExecuteQuery retorna un boolean para saber si hay registros o no.
Sin embargo, esto no es correcto. Podría retornar una lista con los resultados del 
SetResult o algo así. El SetResult no se debería retornar porque se cierra dentro de la
misma función y afuera de la misma no es accesible.
- Se puede crear una Interfaz para que las clases que tienen tablas tengan atributos en
común, como el nomreTabla.
- Luego de salir del programa después de iniciar sesión o registrar un usuario, el
programa no se termina, por alguna razón sigue corriendo en segundo plano.