# Usa la imagen oficial de Tomcat
FROM tomcat:9.0

# Copia el archivo WAR a la carpeta webapps de Tomcat
COPY CalculadoraVLSM2.war /usr/local/tomcat/webapps/

# Expone el puerto 8080 para el servidor
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]
