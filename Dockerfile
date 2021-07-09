FROM openjdk:18-jdk
LABEL version="1.0"

ARG PROJECT_NAME="todo"
ARG PROJECT_VERSION="1.0.0"
ARG JAR_FILE="${PROJECT_NAME}-${PROJECT_VERSION}.jar"
ARG APP_HOME=/opt/deployment
ARG PROJECT_HOME=${APP_HOME}/${PROJECT_NAME}

COPY target/${JAR_FILE} ${PROJECT_HOME}/${JAR_FILE}

# Change working directory
WORKDIR ${PROJECT_HOME}

# Expose Port
EXPOSE 8080

# Entrypoint
ENTRYPOINT ["java", "-jar", "todo-1.0.0.jar"]

# ENTRYPOINT [ "sh", "-c", "java -jar $JAR_FILE" ]