# Sử dụng JDK 17 làm base image
FROM openjdk:17-jdk

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR vào container
COPY target/blog-1.0.1-SNAPSHOT.jar app.jar

# Mở cổng 8083
EXPOSE 8083

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
