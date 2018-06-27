FROM mongo

RUN echo 'deb http://http.debian.net/debian jessie-backports main' >> /etc/apt/sources.list; apt-get update; 
RUN apt-get install -y git
RUN mkdir -p /usr/share/man/man1/
RUN apt-get install -y -t jessie-backports openjdk-8-jdk;
RUN mkdir -p /var/app; cd /var/app; git clone https://github.com/if1007/storeService;
COPY stores.json /var/app

RUN echo 'mongod --bind_ip_all & sleep 12 \
&& mongoimport -d store -c stores < /var/app/stores.json \
&& cd /var/app/storeService/ && ENV=dev ./gradlew run' > start.sh
EXPOSE 27017
EXPOSE 8080

CMD [ "bash", "./start.sh" ]