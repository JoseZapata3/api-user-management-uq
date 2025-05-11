pipeline{
    agent any
    tools{
        maven 'maven3'
        jdk 'jdk21'
    }
    stages{
        stage('Clonar repo'){
            steps{
                git 'https://github.com/JoseZapata3/api-user-management-uq.git'
            }
        }
        stage('Compilar'){
            steps{
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Pruebas'){
            steps{
                sh 'mvn test'
                junit 'build/test-results/*.xml'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Calidad de codigo'){
            steps{
                withSonarQubeEnv('Mi Instancia Sonar'){
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}