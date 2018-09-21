

/**Creamos la bd**/
docker run  --name mysql_bd --net bridge \
-e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=journal -e MYSQL_USER=spring \
-e ALLOW_EMPTY_PASSWORD=yes -e MYSQL_PASSWORD=123456 -p 3312:3306 -d mariadb:5.5


/***Obtenemos la ip*/
docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql_bd


/** Corremos la aplicacion con gradle*/
gradle bootRun



