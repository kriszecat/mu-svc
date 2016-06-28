Le dump est compatible postgres 9.3

Installation 

D�ployer le war dans un tomcat
Copier le dossier t3config dans %CATALINA_HOME%
Modifier le fichier ewox.properties pour la connexion avec le SGBD


Import du SGBD dans un postgres 9.3
CREATE DATABASE pocetpta WITH ENCODING = 'LATIN5' LC_COLLATE = 'C' LC_CTYPE = 'C' TEMPLATE=template0;
pg_restore.exe -h horoquartz.poc -U horoquartz -n public -d pocetpta D:\home\cchabot\work\conseil\Horoquartz-Audit_eTemptation\3-Realisation\3-PoC\docker\projects\eTemptation-api\src\main\docker\etemptation.dump

-- Ne pas faire attention aux erreurs sur les roles


swagger
http://localhost:8080/hq-web/service/swagger.json

swadl
http://localhost:8080/hq-web/

La liste de tous les employ�s
http://localhost:8080/hq-web/service/user/employee

