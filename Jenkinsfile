pipeline {
    agent any

    environment {
        IMAGE_NAME = "ms-fredymoran-02"
        DOCKERHUB_NAMESPACE = "fredyyessielmf"
        REGISTRY = "docker.io"
        
        JAVA_HOME = tool name: 'JDK21', type: 'hudson.model.JDK'
        MAVEN_HOME = tool name: 'M3', type: 'hudson.tasks.Maven$MavenInstallation'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    options {
        timestamps()
        ansiColor('xterm')
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout Git') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    def tag = env.BUILD_NUMBER
                    def image = docker.build("${DOCKERHUB_NAMESPACE}/${IMAGE_NAME}:${tag}")
                    docker.withRegistry("https://${REGISTRY}", 'dockerhub-creds') {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Imagen publicada: ${REGISTRY}/${DOCKERHUB_NAMESPACE}/${IMAGE_NAME}:${env.BUILD_NUMBER}"
        }
        failure {
            echo "Build fallido. Revisar logs."
        }
    }
}