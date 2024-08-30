# Inventory Management System

## Architecture Overview

| Layer      | Components                                    | Purpose                                                          |
| ---------- | --------------------------------------------- | ---------------------------------------------------------------- |
| View       | CategoryView, ProductView, SupplierView, etc. | User interface for interacting with the system                   |
| Controller | CategoryController, ProductController, etc.   | Handle user input and coordinate between View and Service layers |
| Service    | CategoryService, ProductService, etc.         | Business logic and data processing                               |
| Repository | CategoryRepository, ProductRepository, etc.   | Data access and persistence                                      |
| Model      | Category, Product, Supplier, etc.             | Data structures representing business entities                   |

## Key Features

-   CRUD operations for Products, Categories, Suppliers, and Inventory Transactions
-   Text-based user interface using Lanterna library
-   File-based data persistence using JSON
-   Modular and extensible design

## Design Patterns Used

| Pattern      | Implementation                              | Purpose                                     |
| ------------ | ------------------------------------------- | ------------------------------------------- |
| Repository   | CategoryRepository, ProductRepository, etc. | Abstraction of data storage                 |
| Unit of Work | UnitOfWork class                            | Coordinating multiple repository operations |
| MVC          | Separation of View, Controller, and Model   | Separation of concerns                      |
| DTO          | CategoryDto, ProductDto, etc.               | Data transfer between layers                |

## Core Components

| Component        | Purpose                                        |
| ---------------- | ---------------------------------------------- |
| FileHandler      | Generic JSON file read/write operations        |
| UnitOfWork       | Coordinating work across multiple repositories |
| APP              | Main application entry point and navigation    |
| DashboardService | Providing summary data for the dashboard       |

## Data Flow

1. User interacts with View
2. View calls Controller methods
3. Controller uses Service layer for business logic
4. Service layer interacts with Repository for data access
5. Repository uses FileHandler for data persistence
6. Results propagate back up the chain to the View

## Technology Stack

-   Java
-   Lanterna (for text-based UI)
-   Gson (for JSON serialization/deserialization)
