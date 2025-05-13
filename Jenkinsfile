pipeline{
    agent any
    tools {
        jdk 'jdk-21.0.7-oracle-x64'
        
    }
    stages{
        stage('Clonar repo'){
            steps{
                git url: 'https://github.com/JoseZapata3/api-user-management-uq.git', branch: 'main'
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
            }
        }
        stage('Calidad de codigo'){
            steps{
                withSonarQubeEnv('Mi Instancia Sonar'){
                    sh "mvn sonar:sonar"
                }
            }
        }
    }
}