FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/bierapp.jar /bierapp/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/bierapp/app.jar"]
