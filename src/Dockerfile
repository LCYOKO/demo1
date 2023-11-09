FROM ubuntu
MAINTAINER lee<lee@xxx.com>

ENV MYPATH /usr/local
WORKDIR $MYPATH

RUN apt-get update
RUN apt-get install net-tools

EXPOSE 80

ENTRYPOINT ["ls"]
CMD ["-l"]
