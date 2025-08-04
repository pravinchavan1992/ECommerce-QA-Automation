pipeline {
  agent any

  tools {
    maven 'Maven_3.9.11'
    jdk 'JDK17'
  }

  environment {
    GIT_CREDENTIALS_ID = '5e577d8f-1277-48c4-ab21-cd1cd8dea5c3'
    REPO_URL = 'https://github.com/pravinchavan1992/ECommerce-QA-Automation.git'
  }

  triggers {
    pollSCM('H/5 * * * *')  // OR configure GitHub webhook instead
  }

  stages {
    stage('Checkout') {
      steps {
        git credentialsId: "${GIT_CREDENTIALS_ID}", url: "${REPO_URL}"
      }
    }

    stage('Build & Test') {
      steps {
        sh 'mvn clean test'
      }
    }

    // Add other stages like Docker, deploy, etc.
  }

  post {
    always {
      echo '📦 Archiving and publishing reports...'

      script {
        if (fileExists('target/allure-results')) {
          allure results: [[path: 'target/allure-results']]
        }

        if (fileExists('target/surefire-reports/emailable-report.html')) {
          publishHTML([
            reportDir: 'target/surefire-reports',
            reportFiles: 'emailable-report.html',
            reportName: 'Test Report'
          ])
        }
      }

      archiveArtifacts artifacts: '**/target/allure-results/**', fingerprint: true
    }

    success {
      echo '✅ Success!'
    }

    failure {
      echo '❌ Build failed!'
    }
  }
}
