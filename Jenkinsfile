pipeline {
    agent any

    tools {
        maven 'Maven3' // Must match the name configured in Manage Jenkins -> Tools
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Execute Automated Tests') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh 'mvn clean test -Denv=jenkins'
                    }
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']]
        }
    }
}