pipeline {
    agent any
    stages {
        stage ('Just Test') {
            steps {
                bat 'echo inicio'
            }
        }
        stage ('Meio') {
            steps {
                bat 'echo meio'
                bat 'echo meio de novo'
            }
        }
        stage ('Fim') {
            steps {
                sleep(5)
                bat 'echo fim'
            }
        }
    }
}