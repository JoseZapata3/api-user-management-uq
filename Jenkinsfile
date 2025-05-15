pipeline{
    agent any
    tools {
        jdk 'jdk-21.0.7-oracle-x64'
        maven 'maven3'
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
        stage('Levantar servicios') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d --build'
            }
        }
        stage('Esperar servicios') {
            steps {
                sh 'sleep 60'
            }
        }
        stage('Pruebas'){
            steps{
                dir("C:\\Users\\Bryan\\Documents\\btw\\code\\Programacion\\api-test"){
                    sh 'mvn test'
                }
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
    post {
        always {
            echo "Apagando entorno de servicios"
            sh 'docker-compose -f docker-compose.yml down --volumes --remove-orphans'
        }
    }
}
