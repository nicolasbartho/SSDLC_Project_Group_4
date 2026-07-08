# SSDLC_Project_Group_4
Secure Mini Web Application Project

# 🔒 SSDLC Project - Library Management System
**Secure Software Development - Final Assignment**
*Group 01 - Nicolas Barthollet, Aurélien Nougarou, Rémi Gomez, Seydina Aly Tounkara*

---

## 📌 **Project Overview**
A **secure mini web application** for library management, designed with security best practices across the entire **Software Development Lifecycle (SDLC)**.
**Theme**: Library Management System (Web Interface or REST API).
**Duration**: 1 week.

---

## 👥 **Team Members**
   Role          | Name                     | GitHub Username | Tasks                          |
 |---------------|--------------------------|-----------------|--------------------------------|
 | Team Lead     | Nicolas Barthollet       | @nicolasb       | Coordination, Backend          |
 | Developer     | Aurélien Nougarou        | @aurelienN      | Frontend, Threat Modeling      |
 | Developer     | Rémi Gomez               | @remiG          | Security Testing (SAST/DAST)  |
 | Developer     | Seydina Aly Tounkara     | @seydinaA       | CI/CD Pipeline, Documentation  |

---

## 🛠 **Technologies**
 | Category       | Technology              | Purpose                          |
 |----------------|--------------------------|----------------------------------|
 | **Backend**    | Spring Boot (Java 17+)   | Core application logic          |
 | **Frontend**   | Thymeleaf (or REST API)  | Simple UI or API endpoints       |
 | **Security**   | Spring Security          | Authentication & Authorization    |
 | **Database**   | H2 (in-memory)           | Lightweight, no setup required   |
 | **ORM**        | Spring Data JPA          | Database interactions            |
 | **SAST**       | SonarQube / SonarLint    | Static code analysis             |
 | **DAST**       | OWASP ZAP                | Dynamic vulnerability scanning   |
 | **Build**      | Maven                    | Dependency management            |

---

## 🚀 **Getting Started**

### Prerequisites
- Java 17+
- Maven 3.8+
- Git
- (Optional) Docker (for OWASP ZAP)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/nicolasbartho/SSDLC_Project_Group_4/blob/main
   cd SSDLC_Project_Group_4


SSDLC_Project_Group01/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── config/          # Security config (Spring Security)
│   │   │   ├── controller/      # REST/Web controllers
│   │   │   ├── model/           # Entities (Book, User, Loan)
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── LibraryApplication.java
│   │   └── resources/
│   │       ├── static/          # CSS/JS
│   │       ├── templates/       # Thymeleaf HTML
│   │       └── application.properties
│   └── test/                    # Unit/Integration tests
├── docs/
│   ├── DFD.png                  # Data Flow Diagram
│   ├── STRIDE_Analysis.md       # Threat modeling
│   └── CI_CD_Pipeline.md        # DevSecOps pipeline
├── slides/
│   └── SSDLC_Presentation.pptx   # Presentation slides
├── .gitignore
├── pom.xml                      # Maven dependencies
└── README.md
