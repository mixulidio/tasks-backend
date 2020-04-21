pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Deploy Beckend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin',path: '', url:'http://192.168.99.1:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test') {
            steps {
                dir('api-test'){
                    git credentialsId: 'github_login', url: 'https://github.com/mixulidio/api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Frontend') {
            steps {
                dir('frontend'){
                    git credentialsId: 'github_login', url: 'https://github.com/mixulidio/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin',path: '', url:'http://192.168.99.1:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage ('Functional Test') {
            steps {
                dir('functional-test'){
                    git credentialsId: 'github_login', url: 'https://github.com/mixulidio/tasks-functional-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Prod'){
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage ('Check Prod') {
            steps {
                sleep(40)
                dir('functional-test'){
                    bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true,
            testResults: '/target/surefire-reports/*.xml, /api-test/target/surefire-reports/*.xml, /functional-test/target/surefire-reports/*.xml, /functional-test/target/failsafe-reports/*.xml',
            archiveArtifacts artifacts: '/target/tasks-backend.war, /frontend/target/tasks.war',
            onlyIfSuccessful: true
        }
    }
}