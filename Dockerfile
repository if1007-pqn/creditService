FROM openjdk:8u171-jdk-stretch

ARG ENV=prod
ARG STORE_ADDR
ARG LOG_ADDR

ENV STORE_ADDR ${STORE_ADDR}
ENV LOG_ADDR ${LOG_ADDR}
ENV ENV ${ENV}
ENV DOCKER docker
ENV PORT 9090
ENV PORT2 9091

ARG local=/var/app
RUN mkdir -p ${local}
WORKDIR ${local}

COPY . ${local}
# RUN ./gradlew 
EXPOSE 9090
EXPOSE 9091



CMD [ "bash", "./start.sh" ]