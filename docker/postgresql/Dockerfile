# Creates the minimum environment to package and run this Skyve application.
# Note that this uses an in-memory database and is only useful for demonstration/development.
FROM bizhubaustralia/skyve-wildfly-postgresql:latest

ENV LANG en_US.UTF-8

USER root
RUN mkdir /content
RUN chown jboss:jboss /content

USER jboss
ADD orgManager.war /opt/jboss/wildfly/standalone/deployments/orgManager.war
ADD orgManager.json /opt/jboss/wildfly/standalone/deployments
ADD orgManager-ds.xml /opt/jboss/wildfly/standalone/deployments
RUN touch /opt/jboss/wildfly/standalone/deployments/orgManager.war.dodeploy

ENV SKYVE_CONTENT='/content/' \
    SKYVE_DATASTORE_DIALECT='org.skyve.impl.persistence.hibernate.dialect.PostgreSQL95SpatialDialect' \
    DATABASE_URL='orgManager_db' \
    SKYVE_SECURITY_DOMAIN_NAME='orgManager' \
    SKYVE_JNDI_NAME='orgManagerDB'

ENTRYPOINT [ "entrypoint.sh" ]