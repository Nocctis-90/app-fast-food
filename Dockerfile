FROM amazoncorretto:17-alpine-jdk

WORKDIR /code
COPY ./app /code/app

CMD ["sh", "-c", "java -jar /code/app/app-fastfood.jar"]

