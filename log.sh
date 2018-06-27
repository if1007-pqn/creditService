#!/usr/bin/env bash
export ADDRESS_ELASTICSEARCH="$1"

if [ ! "$DOCKER" = 'docker' ]; then
    echo 'Script feito para ser executado via docker'
    exit 0
fi


curl -LO https://artifacts.elastic.co/downloads/beats/packetbeat/packetbeat-6.3.0-amd64.deb
apt update ; apt-get install -y libpcap0.8
apt install ./packetbeat-6.3.0-amd64.deb
rm ./packetbeat-6.3.0-amd64.deb
sed s/localhost:9200/${ADDRESS_ELASTICSEARCH:-'logservice:9200'}/ /etc/packetbeat/packetbeat.yml > /tmp/packetbeat.yml; cp /tmp/packetbeat.yml /etc/packetbeat/packetbeat.yml

curl -LO https://artifacts.elastic.co/downloads/logstash/logstash-6.3.0.zip
unzip logstash-6.3.0.zip 
echo '
input {
  file {
    type => "java"
    path => "/var/app/'${LOGFILE:-elk-credit.log}'"
    codec
     => multiline {
      pattern => "^%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}.*"
      negate => "true"
      what => "previous"
    }
  }
}
 
filter {
  #If log line contains tab character followed by at then we will tag that entry as stacktrace
  if [message] =~ "\tat" {
    grok {
      match => ["message", "^(\tat)"]
      add_tag => ["stacktrace"]
    }
  }
 
 grok {
    match => [ "message",
               "(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME})  %{LOGLEVEL:level} %{NUMBER:pid} --- \[(?<thread>[A-Za-z0-9-]+)\] [A-Za-z0-9.]*\.(?<class>[A-Za-z0-9#_]+)\s*:\s+(?<logmessage>.*)",
               "message",
               "(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME})  %{LOGLEVEL:level} %{NUMBER:pid} --- .+? :\s+(?<logmessage>.*)"
             ]
  }
 
  
  date {
    match => [ "timestamp" , "yyyy-MM-dd HH:mm:ss.SSS" ]
  }
}
 
output {
   
  stdout {
    codec => rubydebug
  }
 
  # Sending properly parsed log events to elasticsearch
  elasticsearch {
    hosts => ["'${ADDRESS_ELASTICSEARCH:-'logservice:9200'}'"]
  }
}' > logstash.conf

packetbeat run -e &
logstash-6.3.0/bin/logstash -f logstash.conf