# E-commerce Backend Makefile
# Automation commands for development and deployment

.PHONY: help compile db-run db-stop app-run app-stop app-local-run clean logs status

# Default target
help:
	@echo "E-commerce Backend - Available Commands:"
	@echo ""
	@echo "Build & Compile:"
	@echo "  compile      - Compile the application using Maven wrapper"
	@echo "  clean        - Clean build artifacts"
	@echo ""
	@echo "Database Commands:"
	@echo "  db-run       - Start PostgreSQL database container"
	@echo "  db-stop      - Stop PostgreSQL database container"
	@echo ""
	@echo "Application Commands:"
	@echo "  app-run      - Build and run application in Docker container"
	@echo "  app-stop     - Stop application container"
	@echo "  app-local-run - Run application locally using Maven wrapper"
	@echo ""
	@echo "Utility Commands:"
	@echo "  logs         - Show application logs"
	@echo "  status       - Show container status"
	@echo "  clean        - Clean build artifacts and stop all containers"

# Build and compile
compile:
	@echo "🔨 Compiling application..."
	./mvnw clean compile

# Database commands
db-run:
	@echo "🐘 Starting PostgreSQL database..."
	docker compose up postgres -d
	@echo "✅ Database started! Connection: localhost:5432"
	@echo "   Database: ecommerce_db"
	@echo "   Username: ecommerce_user"
	@echo "   Password: ecommerce_pass"

db-stop:
	@echo "🛑 Stopping PostgreSQL database..."
	docker compose stop postgres
	@echo "✅ Database stopped!"

# Application commands
app-run: compile
	@echo "🚀 Building and starting application in container..."
	docker compose rm -f app || true
	docker compose up app --build -d
	@echo "✅ Application started! Available at: http://localhost:8080"
	@echo "   Swagger UI: http://localhost:8080/swagger-ui.html"
	@echo "   API Docs: http://localhost:8080/api-docs"

app-stop:
	@echo "🛑 Stopping application container..."
	docker compose stop app
	@echo "✅ Application stopped!"

app-local-run: db-run
	@echo "🏠 Starting application locally (requires database)..."
	@echo "⏳ Waiting for database to be ready..."
	@sleep 10
	@echo "🚀 Starting Spring Boot application..."
	./mvnw spring-boot:run

# Utility commands
logs:
	@echo "📋 Showing application logs..."
	docker compose logs -f app

status:
	@echo "📊 Container Status:"
	@docker compose ps

clean: app-stop db-stop
	@echo "🧹 Cleaning build artifacts and containers..."
	./mvnw clean
	docker compose down --volumes --remove-orphans
	@echo "✅ Cleanup complete!"

# Full stack commands (bonus)
start-all: db-run app-run
	@echo "🌟 Full stack started!"
	@echo "   Database: localhost:5432"
	@echo "   API: http://localhost:8080"
	@echo "   Swagger: http://localhost:8080/swagger-ui.html"

stop-all: app-stop db-stop
	@echo "🛑 Full stack stopped!"

restart: stop-all start-all
	@echo "🔄 Full stack restarted!"