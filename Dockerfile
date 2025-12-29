# --- GIAI ĐOẠN 1: BUILD (Dùng Maven để đóng gói file .jar) ---
# Mình khuyên dùng Java 21 (LTS) thay vì 25 (chưa ổn định)
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# 1. COPY thư mục .mvn và file mvnw trước (QUAN TRỌNG ĐỂ SỬA LỖI CŨ)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Cấp quyền thực thi cho mvnw (Sửa lỗi permission denied nếu có)
RUN chmod +x mvnw

# 3. Tải thư viện về (để tận dụng cache)
RUN ./mvnw dependency:go-offline

# 4. Copy source code và build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- GIAI ĐOẠN 2: RUN (Chạy ứng dụng) ---
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy file .jar từ giai đoạn build sang giai đoạn run
# Lưu ý: Kiểm tra tên file jar trong target, thường spring boot đặt tên có version
# Dòng dưới đây sẽ tự tìm file .jar bất kỳ và đổi tên thành app.jar cho gọn
COPY --from=build /app/target/*.jar app.jar

# Cấu hình tối ưu RAM cho container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

