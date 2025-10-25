@"
pipeline {
  agent any

  environment {
    IMAGE_NAME = "ms-fredymoran-02"
    DOCKERHUB_NAMESPACE = "fredyyessielmf"
    REGISTRY = "docker.io"
    JAVA_HOME = tool name: 'JDK21', type: 'hudson.model.JDK'
    MAVEN_HOME = tool name: 'M3', type: 'hudson.tasks.Maven\$MavenInstallation'
    PATH = "\${JAVA_HOME}/bin:\${MAVEN_HOME}/bin:\${env.PATH}"
  }

  options {
    timestamps()
    ansiColor('xterm')
    disableConcurrentBuilds()
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build JAR') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
      post {
        success {
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          docker.build("\${DOCKERHUB_NAMESPACE}/\${IMAGE_NAME}:\${env.BUILD_NUMBER}")
        }
      }
    }

    stage('Push to Docker Hub') {
      steps {
        script {
          docker.withRegistry("https://\${REGISTRY}", 'dockerhub-creds') {
            docker.image("\${DOCKERHUB_NAMESPACE}/\${IMAGE_NAME}:\${env.BUILD_NUMBER}").push()
            docker.image("\${DOCKERHUB_NAMESPACE}/\${IMAGE_NAME}:\${env.BUILD_NUMBER}").push('latest')
          }
        }
      }
    }
  }

  post {
    success {
      echo """
      ✅ BUILD EXITOSO
      Imagen publicada: \${REGISTRY}/\${DOCKERHUB_NAMESPACE}/\${IMAGE_NAME}:\${env.BUILD_NUMBER}
      URL: https://hub.docker.com/r/fredyyessielmf/ms-fredymoran-02
      """
    }
    failure {
      echo "❌ BUILD FALLADO. Revisar logs."
    }
  }
}
"@ | Out-File -FilePath "Jenkinsfile" -Encoding UTF8