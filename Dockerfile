FROM openjdk:8u171-jdk-stretch

ARG ENV=prod

ENV ENV ${ENV}
ENV PORT 9090
ENV PORT2 9091

ARG local=/var/app
RUN mkdir -p ${local}
WORKDIR ${local}
RUN if [ $ENV = 'test' ]; then echo './gradlew test' >> script; else echo './gradlew run' >> script; fi

COPY . ${local}
RUN ./gradlew 
EXPOSE 9090
EXPOSE 9091



CMD [ "bash", "./script" ]