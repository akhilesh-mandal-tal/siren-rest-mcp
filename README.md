# E-commerce Backend API

A quick prototype backend for an e-commerce platform built with Spring Boot Java 21. This application provides REST APIs for product browsing and order management.

## Features

- **Product Management**: Browse products with filtering by category, brand, and price range
- **Category Management**: Organize products into categories
- **Order Management**: Create, view, and manage orders
- **Stock Management**: Automatic stock tracking and updates
- **Minimal Architecture**: No repository/service layers - direct EntityManager usage in controllers
- **Docker Support**: Containerized with PostgreSQL database

## Technology Stack

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA (EntityManager only)
- PostgreSQL Database
- SpringDoc OpenAPI 3
- Maven
- Docker & Docker Compose

## Quick Start

### Option 1: Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone <repository-url>
cd ecommerce-backend
```

2. Start with Docker Compose:
```bash
docker-compose up --build
```

3. The application will start on `http://localhost:8080`

### Option 2: Local Development

#### Prerequisites
- Java 21
- Maven 3.6+ (or use included Maven wrapper)
- PostgreSQL running locally

#### Setup Local PostgreSQL
```bash
# Create database and user
createdb ecommerce_db
createuser -s ecommerce_user
# Set password for user (when prompted): ecommerce_pass
```

#### Running the Application
```bash
# Using Maven wrapper (recommended)
./mvnw clean spring-boot:run

# Or using system Maven
mvn clean spring-boot:run
```

### Access Points

- **API Base URL**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8080/api-docs`

## API Endpoints

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products with optional filtering |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |
| GET | `/api/products/category/{categoryId}` | Get products by category |

#### Product Filtering Parameters
- `categoryId`: Filter by category ID
- `brand`: Filter by brand name (case insensitive)
- `minPrice`: Minimum price filter
- `maxPrice`: Maximum price filter
- `search`: Search by product name

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get category by ID |
| POST | `/api/categories` | Create a new category |
| PUT | `/api/categories/{id}` | Update a category |
| DELETE | `/api/categories/{id}` | Delete a category |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get all orders with optional filtering |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders/number/{orderNumber}` | Get order by order number |
| POST | `/api/orders` | Create a new order |
| PUT | `/api/orders/{id}/status` | Update order status |
| DELETE | `/api/orders/{id}` | Cancel an order |

#### Order Filtering Parameters
- `status`: Filter by order status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- `customerEmail`: Filter by customer email

## Sample API Usage

### Get Products with Filtering
```bash
# Get all products
curl http://localhost:8080/api/products

# Filter by category
curl http://localhost:8080/api/products?categoryId=1

# Filter by brand and price range
curl "http://localhost:8080/api/products?brand=Apple&minPrice=500&maxPrice=2000"

# Search by name
curl "http://localhost:8080/api/products?search=MacBook"
```

### Create an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerEmail": "john@example.com",
    "customerName": "John Doe",
    "shippingAddress": "123 Main St, City, State 12345",
    "orderItems": [
      {
        "productId": 1,
        "quantity": 2
      },
      {
        "productId": 3,
        "quantity": 1
      }
    ]
  }'
```

### Update Order Status
```bash
curl -X PUT "http://localhost:8080/api/orders/1/status?status=CONFIRMED"
```

## Architecture

This application uses a **minimal architecture** approach:
- **Controllers** directly use `EntityManager` for database operations
- **No Repository layer** - JPQL queries written directly in controllers
- **No Service layer** - business logic kept minimal in controllers
- **No DTOs** - simple inner classes for request/response when needed
- **Entities** are JPA entities with basic validation

## Database Schema

The application uses the following main entities:

- **Category**: Product categories with name and description
- **Product**: Products with name, description, price, brand, stock quantity, and category
- **Order**: Customer orders with status, customer info, and order items
- **OrderItem**: Individual items within an order with quantity and unit price

## Sample Data

The application automatically initializes with sample data including:
- 5 categories (Electronics, Clothing, Books, Home & Garden, Sports)
- 13 sample products across different categories
- Various brands (Apple, Sony, Samsung, Nike, Levi's, etc.)

## Docker Commands

```bash
# Start services
docker-compose up

# Start in background
docker-compose up -d

# Rebuild and start
docker-compose up --build

# Stop services
docker-compose down

# View logs
docker-compose logs app
docker-compose logs postgres
```

## Notes

- This is a prototype application designed for quick development and testing
- Uses PostgreSQL database with Docker for easy setup
- No authentication or authorization implemented
- Minimal architecture - no service/repository layers
- Basic error handling for prototype purposes

For production use, consider adding:
- Security layer (Spring Security)
- Service and repository layers
- Caching (Redis)
- Message queues for order processing
- Comprehensive logging and monitoring
- Unit and integration tests