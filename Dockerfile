FROM registry.access.redhat.com/rhel7-minimal

USER root

RUN microdnf --enablerepo=rhel-7-server-rpms \
install java-1.8.0-openjdk --nodocs ;\
microdnf clean all


# Set the JAVA_HOME variable to make it clear where Java is located
ENV JAVA_HOME /etc/alternatives/jre

RUN mkdir -p /app

EXPOSE 8080

COPY target/*-runner.jar /app/
COPY target/lib/** /app/lib/

COPY run-java.sh /app/

RUN chmod 755 /app/run-java.sh

CMD [ "/app/run-java.sh" ]
