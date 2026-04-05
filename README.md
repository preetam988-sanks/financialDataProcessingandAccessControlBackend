**Secure Finance Data Processing & Access Control**
A professional Spring Boot backend designed for managing financial records. 
This system implements strict Role-Based Access Control (RBAC), data aggregation for dashboards, and secure API design patterns

**Key Features**
(i) Role-Based Access Control (RBAC):

ADMIN: Full management of records and user statuses.

ANALYST: Can create, update, and view records/insights.

VIEWER: Read-only access to dashboard summaries and record lists.

(ii) Interactive API Documentation: Integrated Swagger UI for real-time endpoint testing.

(iii)Dashboard Analytics: Real-time summary generation using Java Streams (Total Income, Expense, Net Balance, Category-wise totals, and Monthly Trends).

(iv)Security & Data Privacy: * Password hashing via BCrypt.

   DTO Pattern and Jackson Access Levels to prevent sensitive data leakage.

  Ownership Validation: Users can only modify/delete data they own.

(v)Soft Delete: Implemented a deleted flag to maintain financial audit trails.

(vi)Performance: Database indexing on high-traffic columns (user_id, category, entry_date).

**Tech Stack**
i)Framework: Spring Boot 4.0.5 (Spring 7)

ii)Security: Spring Security (Basic Auth + Method Security)

iii)Database: PostgreSQL

iv)Documentation: SpringDoc OpenAPI (Swagger UI)

v)Tools: Lombok, Jakarta Validation, Maven

**Interactive API Documentation**
Once the application is running, you can access the full interactive API documentation here:

 http://localhost:9090/swagger-ui/index.html

**Local Setup**
i)Clone the repository:

git clone https://github.com/preetam988-sanks/financialDataProcessingandAccessControlBackend.git
cd financialDataProcessingandAccessControlBackend

ii)Configure Database:

Create a PostgreSQL database (e.g., financedata).

Copy src/main/resources/application-example.properties to src/main/resources/application.properties.

Update the spring.datasource credentials with your local settings.

iii)Build and Run:
mvn clean install
mvn spring-boot:run

**Assumptions & Tradeoffs**
Authentication: Basic Auth was chosen for the simplicity of this assessment. In a production environment, JWT would be used for stateless token-based sessions.

Concurrency: The system assumes standard ACID compliance provided by PostgreSQL for financial transactions.

Validation: Strict input validation is enforced at the DTO level to ensure data integrity before hitting the service layer.

**Core Endpoints (Summary)**

Method                Endpoint                                  Description                                     Access
POST          /api/v1/users/register                           UserRegistration                                  Public
GET           /api/v1/finance/summary                         DashboardAnalytics                                 All Roles
POST          /api/v1/finance/records                         Add Transaction                                   Admin/Analyst
GET           /api/v1/finance/records/search                  Paginated Keyword Search                           All Roles
