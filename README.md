# PGP Encrypted Messaging System

## Overview



https://github.com/user-attachments/assets/ef8c2d02-7580-465b-86da-0a293843171d



This project is an **Encrypted Messaging System** that uses RSA encryption to secure messages. It allows users to generate key pairs, encrypt messages, and decrypt messages securely. The system consists of a backend application built with Spring Boot and a simple frontend with HTML/CSS. 

### Key Features

- **Generate Key Pair**: Create and download a public/private key pair. The public key is stored in the database, and the private key can be downloaded for future use.
- **Encrypt Message**: Encrypt messages using the recipient's public key and send the encrypted message to the recipient's email.
- **Decrypt Message**: Decrypt received messages using your private key and view the original message.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Spring Boot
- A Mail Server (configured in `application.properties`)

### Installation

1. **Clone the Repository**
   ```
   bash
   git clone https://github.com/your-username/your-repository.git
   cd your-repository
   ```
Configure Application Properties

Update the src/main/resources/application.properties file with your mail server details:
```
properties

spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-password
Build and Run the Application
```

bash
```
mvn clean install
mvn spring-boot:run
Access the Application
```

Open your web browser and navigate to http://localhost:8080 to start using the application.

##Usage
Generate Key Pair

#Navigate to the Generate Key Pair page.
-Enter your email and name.
-Click Generate to create your key pair. Download the private key and store it securely.
-Encrypt Message

#Navigate to the Encrypt Message page.
-Select the recipient's unique ID from the list.
-Enter your message and submit.
-The encrypted message will be sent to the recipient's email.

#Decrypt Message

-Navigate to the Decrypt Message page.
-Enter the encrypted message and your private key.
-Click Decrypt Message to view the original message.

#Directory Structure
```
src/main/java/com/your/package/: Contains Java source files.
src/main/resources/: Contains configuration files and static resources.
src/main/resources/static/: Contains static HTML, CSS, and JavaScript files.
```
#Contributing
Contributions are welcome! Please follow these steps to contribute:

#Fork the repository.
```
Create a new branch (git checkout -b feature-branch).
Make your changes and commit (git commit -am 'Add new feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.
License
This project is licensed under the MIT License - see the LICENSE file for details.
```


### How to Use

1. **Customize the repository URL** in the `git clone` command.
2. **Update configuration details** in `application.properties` with your actual mail server settings.
3. **Modify the Directory Structure section** if your project structure differs.
