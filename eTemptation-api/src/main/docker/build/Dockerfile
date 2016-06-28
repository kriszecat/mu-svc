FROM tomcat:7-jre7

RUN rm -fr /usr/local/tomcat/webapps/docs
RUN rm -fr /usr/local/tomcat/webapps/examples
RUN rm -fr /usr/local/tomcat/webapps/ROOT

COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY t3config /usr/local/tomcat/t3config/
COPY hq-web.war /usr/local/tomcat/webapps/
