FROM java:8
MAINTAINER Alex Vanlint
RUN mkdir -p /runtime/status
ADD target/monitor-1.0-SNAPSHOT.jar /runtime/status
CMD ["java","-jar","/runtime/status/monitor-1.0-SNAPSHOT.jar"]