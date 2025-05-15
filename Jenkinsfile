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

        stage('Levantar servicios') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d --build'
                sh 'sleep 60'
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
            echo "Apagando entorno de servicios"
            sh 'docker-compose -f docker-compose.yml down --volumes --remove-orphans'
        }
    }
}
