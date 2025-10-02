# Portfolio Tracker - Complete Documentation
Project Overview
Portfolio Tracker is a full-stack web application that allows users to manage their investment portfolios. Users can create multiple portfolios, add stocks with real-time price data, and visualize their investments through interactive charts and analytics. Note Api has limitation

Table of Contents
1.Project Overview
2.Technology Stack
3.Backend Setup
4.Frontend Setup
5.API Endpoints
6.Features
7.Authentication
8.Database Schema
9.Deployment

Key Features
User authentication and authorization
Multiple portfolio management
Real-time stock data integration
Interactive portfolio visualization
Responsive design for all devices

Technology Stack
Backend
Java 17 - Programming language
Spring Boot 3.x - Application framework
Spring Security - Authentication & authorization
JWT - Token-based authentication
Spring Data JPA - Database operations
Postgress Database - Development database
Maven - Dependency management
Lombok - Reduced boilerplate code

Frontend
React 18 - UI library
Vite - Build tool and dev server
Tailwind CSS - Utility-first CSS framework
Axios - HTTP client
React Router - Client-side routing
Chart.js - Data visualization

<img width="1865" height="870" alt="image" src="https://github.com/user-attachments/assets/755df16f-94e4-48f8-9460-6f0038e2b3d1" />

Backend Setup
Prerequisites
Java 17 or higher
Maven 3.6+
IDE (IntelliJ IDEA, Eclipse, or VS Code)

Installation Steps
Clone the repository
git clone <repository-url>
cd portfolio/tracker

Build and run the application
mvn clean install
mvn spring-boot:run

Frontend Setup
Prerequisites
Node.js 16 or higher
npm or yarn

Installation Steps
Navigate to frontend directory
cd portfolio/client

Install dependencies
npm install
Environment Configuration (.env)
VITE_BASE_URL=http://localhost:8080/api
Build for production
npm run build


API Endpoints
1.Authentication Endpoints
Method	Endpoint	Description
POST	/auth/login 	User login
POST	/auth/signup	User registration
2.Portfolio Endpoints
Method	Endpoint	Description	
GET	/portfolios	Get user's portfolios
GET	/portfolios/{id}	Get portfolio details
POST /portfolios	Create new portfolio
3.Asset Endpoints
Method	Endpoint	           Description
GET	/portfolio/assets	        Get portfolio assets	
POST	/portfolio/assets	    Add asset to portfolio	
DELETE	/portfolio/assets/{id}	Remove asset from portfolio	
4.Stock Data Endpoints
Method	Endpoint	   Description
GET	/stocks/{ticker}	Get stock information	

Authentication
JWT Implementation
The application uses JSON Web Tokens for authentication:
Login: User provides credentials, backend returns JWT
Token Storage: Frontend stores token in localStorage
API Calls: Token included in Authorization header
Token Validation: Backend validates token for protected routes

Troubleshooting
Common Issues
CORS Errors
Check backend CORS configuration
Verify frontend API URL

Authentication Issues
Verify JWT secret matches
Check token expiration
Database Connection
Verify database credentials
Check database server status
Stock Data Not Loading
Verify Alpha Vantage API key
Check API rate limits
