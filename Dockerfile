# Sử dụng JDK 17 (phù hợp với Spring Boot của bạn)
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file jar đã build thành công vào container
COPY target/shop_giay-0.0.1-SNAPSHOT.jar app.jar

# Cổng mặc định
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]