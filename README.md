**Project Overview**

CodeSphere is a web-based real-time code editor that allows users to write, compile, and execute code in multiple programming languages directly from the browser. The platform provides an interactive coding environment with syntax highlighting, custom input support, and instant output generation, making it suitable for learning, practice, and coding demonstrations.

**Features**


- Real-time code execution
- Multi-language support (Java, Python, C++, JavaScript, etc.)
- User-friendly and responsive interface
- Custom input support
- Instant output display
- Code synchronization between editor and output panel
- Responsive design for different screen sizes
- Dashboard with easy navigation

**Technologies Used**
- HTML5
- CSS3
- JavaScript
- Java
- Spring Boot
- REST APIs
- MySQL
- IntelliJ IDEA
- Maven
- Git and Github

**Prerequisites**


- Java 17 or above
- Maven
- MySQL
- IntelliJ IDEA


**Steps to Run**
1. Clone the repository:
"git clone <repository-url>"
2. Navigate to the project folder:
"cd CodeSphere"
3. Configure the MySQL database in application.properties.
4. Build the project:
"mvn clean install"
5. Run the application:
"mvn spring-boot:run"
6. Open your browser and visit:
"http://localhost:8080"

**Objectives**


- Provide a simple and efficient online coding environment.
- Enable users to practice coding without local compiler setup.
- Improve coding productivity through instant execution and feedback.

## Testing Instructions

To test the real-time collaborative functionality of CodeSphere on a local machine without deployment:

1. Run the application on your local server.
2. Open the application in a regular browser window using the localhost URL.
3. Open the same URL in an Incognito/Private browsing window.
4. Use the two windows as separate users.
5. Create or join the same coding session from both windows.
6. Any code changes made in one window should be reflected in the other window in real time.

Example:
- http://localhost:8080


This approach allows developers and testers to simulate multiple users interacting with the application simultaneously without deploying the project to a public server.
