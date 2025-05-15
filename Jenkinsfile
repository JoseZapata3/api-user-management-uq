pipeline {
    agent any

    tools {
        jdk 'jdk-21.0.7-oracle-x64'
    }

    stages {
        stage('Clonar proyecto principal') {
            steps {
                git url: 'https://github.com/JoseZapata3/api-user-management-uq.git', branch: 'main'
            }
        }

        stage('Compilar app') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Levantar entorno') {
            steps {
                sh 'docker-compose up -d --build'
                echo "Esperando que los servicios estén arriba..."
                sh 'sleep 60' // O mejor aún, implementar verificación de healthchecks
            }
        }

        stage('Clonar pruebas Cucumber') {
            steps {
                dir('tests') {
                    git url: "https://github.com/JoseZapata3/api-test", branch: "main"
                }
            }
        }

        stage('Ejecutar pruebas funcionales') {
            steps {
                dir('tests') {
                    sh './mvnw test' // O `mvn test` si usas Maven directamente
                }
            }
        }

        stage('Calidad de código') {
            steps {
                withSonarQubeEnv('Mi Instancia Sonar') {
                    sh "mvn sonar:sonar"
                }
            }
        }
    }

    post {
        always {
            echo 'Limpiando contenedores...'
            sh 'docker-compose down -v'
        }
    }
}
