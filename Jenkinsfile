pipeline {
    agent any

    environment {
        IMAGE_NAME = "ms-fredymoran-02"
        DOCKERHUB_NAMESPACE = "fredymoran"         
        REGISTRY = "docker.io"
    }

    options {
        timestamps()
        disableConcurrentBuilds()
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

        stage('Build & Push Image') {
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
            echo "Imagen publicada: ${env.REGISTRY}/${DOCKERHUB_NAMESPACE}/${IMAGE_NAME}:${env.BUILD_NUMBER}"
        }
        failure {
            echo "Build fallido. Revisar logs."
        }
    }
}